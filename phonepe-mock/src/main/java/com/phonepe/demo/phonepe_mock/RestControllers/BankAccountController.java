package com.phonepe.demo.phonepe_mock.RestControllers;

import com.phonepe.demo.phonepe_mock.dto.ApiResponseDto;
import com.phonepe.demo.phonepe_mock.dto.BankAccountDto;
import com.phonepe.demo.phonepe_mock.dto.CreateBankAccountRequestDto;
import com.phonepe.demo.phonepe_mock.service.BankAccountService;
import com.phonepe.demo.phonepe_mock.util.SecurityUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private static final Logger log = LoggerFactory.getLogger(BankAccountController.class);

    private final BankAccountService bankAccountService;
    private final SecurityUtil securityUtil;

    public BankAccountController(BankAccountService bankAccountService, SecurityUtil securityUtil) {
        this.bankAccountService = bankAccountService;
        this.securityUtil = securityUtil;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<BankAccountDto>>> getUserBankAccounts() {
        try {
            Long userId = securityUtil.getCurrentUserId();
            List<BankAccountDto> accounts = bankAccountService.getUserBankAccounts(userId);
            return ResponseEntity.ok(ApiResponseDto.success("Bank accounts retrieved", accounts));
        } catch (Exception e) {
            log.error("Failed to get user bank accounts", e);
            throw e;
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto<List<BankAccountDto>>> getAllUserBankAccounts() {
        try {
            Long userId = securityUtil.getCurrentUserId();
            List<BankAccountDto> accounts = bankAccountService.getAllUserBankAccounts(userId);
            return ResponseEntity.ok(ApiResponseDto.success("All bank accounts retrieved", accounts));
        } catch (Exception e) {
            log.error("Failed to get all user bank accounts", e);
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<BankAccountDto>> createBankAccount(
            @Valid @RequestBody CreateBankAccountRequestDto request) {
        try {
            Long userId = securityUtil.getCurrentUserId();
            BankAccountDto account = bankAccountService.createBankAccount(request, userId);
            log.info("Bank account created for user: {}, account: {}", userId, account.getAccountNumber());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDto.success("Bank account created successfully", account));
        } catch (Exception e) {
            log.error("Failed to create bank account", e);
            throw e;
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponseDto<BankAccountDto>> getBankAccountById(
            @PathVariable Long accountId) {
        try {
            Long userId = securityUtil.getCurrentUserId();
            BankAccountDto account = bankAccountService.getBankAccountById(accountId, userId);
            return ResponseEntity.ok(ApiResponseDto.success("Bank account retrieved", account));
        } catch (Exception e) {
            log.error("Failed to get bank account by ID: {}", accountId, e);
            throw e;
        }
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<ApiResponseDto<BankAccountDto>> updateBankAccount(
            @PathVariable Long accountId,
            @Valid @RequestBody CreateBankAccountRequestDto request) {
        try {
            Long userId = securityUtil.getCurrentUserId();
            BankAccountDto account = bankAccountService.updateBankAccount(accountId, request, userId);
            log.info("Bank account updated: {}", accountId);
            return ResponseEntity.ok(ApiResponseDto.success("Bank account updated successfully", account));
        } catch (Exception e) {
            log.error("Failed to update bank account: {}", accountId, e);
            throw e;
        }
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<ApiResponseDto<String>> deactivateBankAccount(
            @PathVariable Long accountId) {
        try {
            Long userId = securityUtil.getCurrentUserId();
            bankAccountService.deactivateBankAccount(accountId, userId);
            log.info("Bank account deactivated: {}", accountId);
            return ResponseEntity.ok(ApiResponseDto.success("Bank account deactivated successfully", null));
        } catch (Exception e) {
            log.error("Failed to deactivate bank account: {}", accountId, e);
            throw e;
        }
    }

    @GetMapping("/balance/total")
    public ResponseEntity<ApiResponseDto<BigDecimal>> getTotalBalance() {
        try {
            Long userId = securityUtil.getCurrentUserId();
            BigDecimal totalBalance = bankAccountService.getTotalBalance(userId);
            return ResponseEntity.ok(ApiResponseDto.success("Total balance retrieved", totalBalance));
        } catch (Exception e) {
            log.error("Failed to get total balance", e);
            throw e;
        }
    }
}
