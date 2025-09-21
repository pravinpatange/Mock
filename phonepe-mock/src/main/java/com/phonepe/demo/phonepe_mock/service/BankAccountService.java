package com.phonepe.demo.phonepe_mock.service;

import com.phonepe.demo.phonepe_mock.dto.BankAccountDto;
import com.phonepe.demo.phonepe_mock.dto.CreateBankAccountRequestDto;
import com.phonepe.demo.phonepe_mock.entity.BankAccount;
import com.phonepe.demo.phonepe_mock.entity.User;
import com.phonepe.demo.phonepe_mock.exception.ResourceNotFoundException;
import com.phonepe.demo.phonepe_mock.exception.UserAlreadyExistsException;
import com.phonepe.demo.phonepe_mock.repository.BankAccountRepository;
import com.phonepe.demo.phonepe_mock.repository.UserRepository;
import com.phonepe.demo.phonepe_mock.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountService {

    private static final Logger log = LoggerFactory.getLogger(BankAccountService.class);

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository,
                              UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    public List<BankAccountDto> getUserBankAccounts(Long userId) {
        return bankAccountRepository.findByUserIdAndActiveTrue(userId)
                .stream()
                .map(ModelMapper::toBankAccountDto)
                .collect(Collectors.toList());
    }

    public List<BankAccountDto> getAllUserBankAccounts(Long userId) {
        return bankAccountRepository.findByUserId(userId)
                .stream()
                .map(ModelMapper::toBankAccountDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BankAccountDto createBankAccount(CreateBankAccountRequestDto request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if account number already exists
        if (bankAccountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new UserAlreadyExistsException("Bank account already exists with this account number");
        }

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(request.getAccountNumber());
        bankAccount.setBankName(request.getBankName());
        bankAccount.setIfscCode(request.getIfscCode());
        bankAccount.setBalance(request.getInitialBalance() != null ? request.getInitialBalance() : BigDecimal.ZERO);
        bankAccount.setUser(user);
        bankAccount.setActive(true);

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);

        log.info("Bank account created for user {}: {}", userId, savedAccount.getAccountNumber());

        return ModelMapper.toBankAccountDto(savedAccount);
    }

    @Transactional
    public BankAccountDto updateBankAccount(Long accountId, CreateBankAccountRequestDto request, Long userId) {
        BankAccount account = bankAccountRepository.findByIdAndUserIdAndActiveTrue(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        account.setBankName(request.getBankName());
        account.setIfscCode(request.getIfscCode());

        BankAccount updatedAccount = bankAccountRepository.save(account);

        log.info("Bank account updated: {}", accountId);

        return ModelMapper.toBankAccountDto(updatedAccount);
    }

    @Transactional
    public void deactivateBankAccount(Long accountId, Long userId) {
        BankAccount account = bankAccountRepository.findByIdAndUserIdAndActiveTrue(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        account.setActive(false);
        bankAccountRepository.save(account);

        log.info("Bank account deactivated: {}", accountId);
    }

    public BankAccountDto getBankAccountById(Long accountId, Long userId) {
        BankAccount account = bankAccountRepository.findByIdAndUserIdAndActiveTrue(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        return ModelMapper.toBankAccountDto(account);
    }

    public BigDecimal getTotalBalance(Long userId) {
        return bankAccountRepository.findByUserIdAndActiveTrue(userId)
                .stream()
                .map(BankAccount::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
