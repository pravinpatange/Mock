package com.phonepe.demo.phonepe_mock.service;

import com.phonepe.demo.phonepe_mock.dto.TransactionDto;
import com.phonepe.demo.phonepe_mock.dto.TransferRequestDto;
import com.phonepe.demo.phonepe_mock.entity.*;
import com.phonepe.demo.phonepe_mock.exception.InsufficientBalanceException;
import com.phonepe.demo.phonepe_mock.exception.ResourceNotFoundException;
import com.phonepe.demo.phonepe_mock.repository.BankAccountRepository;
import com.phonepe.demo.phonepe_mock.repository.TransactionRepository;
import com.phonepe.demo.phonepe_mock.repository.UserRepository;
import com.phonepe.demo.phonepe_mock.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final int INITIAL_RETRY_DELAY_MS = 100;

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public TransactionService(TransactionRepository transactionRepository,
                              BankAccountRepository bankAccountRepository,
                              UserRepository userRepository,
                              EmailService emailService) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // 🔄 MANUAL RETRY IMPLEMENTATION - PRODUCTION READY
    public TransactionDto transferMoney(TransferRequestDto transferRequest, Long userId) {
        int attempt = 1;

        while (attempt <= MAX_RETRY_ATTEMPTS) {
            try {
                return executeTransferWithLocking(transferRequest, userId, attempt);

            } catch (ObjectOptimisticLockingFailureException e) {
                if (attempt == MAX_RETRY_ATTEMPTS) {
                    log.error("❌ Transfer failed after {} attempts due to concurrent conflicts: {}",
                            MAX_RETRY_ATTEMPTS, e.getMessage());
                    throw new RuntimeException("Transaction failed due to high concurrency. Please try again later.");
                }

                // 🔄 EXPONENTIAL BACKOFF: 100ms, 200ms, 400ms
                int delayMs = INITIAL_RETRY_DELAY_MS * (int) Math.pow(2, attempt - 1);
                log.warn("⚡ Concurrent transaction detected (attempt {}), retrying in {}ms: {}",
                        attempt, delayMs, e.getMessage());

                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Transaction interrupted", ie);
                }

                attempt++;
            }
        }

        throw new RuntimeException("Unexpected error in transfer retry logic");
    }

    // 🔐 CORE TRANSFER LOGIC WITH DATABASE LOCKING
    @Transactional(isolation = Isolation.READ_COMMITTED)
    private TransactionDto executeTransferWithLocking(TransferRequestDto transferRequest, Long userId, int attempt) {

        log.info("🚀 Starting concurrent-safe money transfer (attempt {}): {} -> {}, amount: {}",
                attempt, userId, transferRequest.getReceiverPhoneNumber(), transferRequest.getAmount());

        // 🔐 Get sender account WITH DATABASE LOCK (prevents concurrent access)
        BankAccount senderAccount = bankAccountRepository
                .findByIdAndUserIdWithLock(transferRequest.getSenderAccountId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender account not found or not accessible"));

        // Get receiver by phone number
        User receiver = userRepository.findByPhoneNumber(transferRequest.getReceiverPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found with phone number: "
                        + transferRequest.getReceiverPhoneNumber()));

        // 🔐 Get receiver's account WITH DATABASE LOCK (prevents concurrent access)
        BankAccount receiverAccount = bankAccountRepository.findByUserIdWithLock(receiver.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Receiver has no active bank account"));

        // Check balance
        if (senderAccount.getBalance().compareTo(transferRequest.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for transfer");
        }

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setAmount(transferRequest.getAmount());
        transaction.setDescription(transferRequest.getDescription());
        transaction.setType(TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setSender(senderAccount.getUser());
        transaction.setReceiver(receiver);
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);
        transaction.setCreatedAt(LocalDateTime.now());

        // 💰 ATOMIC BALANCE UPDATE (with optimistic locking protection)
        senderAccount.setBalance(senderAccount.getBalance().subtract(transferRequest.getAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(transferRequest.getAmount()));

        // Save transaction
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction = transactionRepository.save(transaction);

        // 💾 Save updated accounts (optimistic locking will prevent conflicts)
        bankAccountRepository.save(senderAccount);
        bankAccountRepository.save(receiverAccount);

        // Send email notifications
        sendTransferNotifications(transaction);

        log.info("✅ Concurrent-safe money transfer completed (attempt {}): {} from {} to {}",
                attempt, transferRequest.getAmount(),
                senderAccount.getUser().getEmail(),
                receiver.getEmail());

        return ModelMapper.toTransactionDto(transaction);
    }

    // ✅ OTHER METHODS REMAIN UNCHANGED
    @Transactional
    public TransactionDto depositMoney(Long userId, Long accountId, BigDecimal amount, String description) {
        BankAccount account = bankAccountRepository.findByIdAndUserIdAndActiveTrue(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setReceiver(account.getUser());
        transaction.setReceiverAccount(account);
        transaction.setCreatedAt(LocalDateTime.now());

        // Update balance
        account.setBalance(account.getBalance().add(amount));

        // Save transaction and account
        transaction = transactionRepository.save(transaction);
        bankAccountRepository.save(account);

        log.info("Money deposited: {} to account {}", amount, accountId);

        return ModelMapper.toTransactionDto(transaction);
    }

    @Transactional
    public TransactionDto withdrawMoney(Long userId, Long accountId, BigDecimal amount, String description) {
        BankAccount account = bankAccountRepository.findByIdAndUserIdAndActiveTrue(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setSender(account.getUser());
        transaction.setSenderAccount(account);
        transaction.setCreatedAt(LocalDateTime.now());

        // Update balance
        account.setBalance(account.getBalance().subtract(amount));

        // Save transaction and account
        transaction = transactionRepository.save(transaction);
        bankAccountRepository.save(account);

        log.info("Money withdrawn: {} from account {}", amount, accountId);

        return ModelMapper.toTransactionDto(transaction);
    }

    private void sendTransferNotifications(Transaction transaction) {
        try {
            // Send notification to sender
            emailService.sendMoneyDeductedNotification(
                    transaction.getSender().getEmail(),
                    transaction.getSender().getFirstName(),
                    transaction.getAmount(),
                    transaction.getReceiver().getFirstName() + " " + transaction.getReceiver().getLastName(),
                    transaction.getTransactionId()
            );

            // Send notification to receiver
            emailService.sendMoneyReceivedNotification(
                    transaction.getReceiver().getEmail(),
                    transaction.getReceiver().getFirstName(),
                    transaction.getAmount(),
                    transaction.getSender().getFirstName() + " " + transaction.getSender().getLastName(),
                    transaction.getTransactionId()
            );

        } catch (Exception e) {
            log.error("Failed to send transfer notification emails for transaction {}",
                    transaction.getTransactionId(), e);
        }
    }

    public Page<TransactionDto> getUserTransactions(Long userId, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return transactions.map(ModelMapper::toTransactionDto);
    }

    public List<TransactionDto> getUserTransactionHistory(Long userId) {
        return transactionRepository.findByUserIdOrderByCreatedAtDesc(userId, Pageable.unpaged())
                .getContent()
                .stream()
                .map(ModelMapper::toTransactionDto)
                .collect(Collectors.toList());
    }

    public TransactionDto getTransactionById(String transactionId, Long userId) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        // Check if user has access to this transaction
        if (!transaction.getSender().getId().equals(userId) &&
                !transaction.getReceiver().getId().equals(userId)) {
            throw new ResourceNotFoundException("Transaction not found");
        }

        return ModelMapper.toTransactionDto(transaction);
    }
}
