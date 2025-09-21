package com.phonepe.demo.phonepe_mock.controller;

import com.phonepe.demo.phonepe_mock.dto.ApiResponseDto;
import com.phonepe.demo.phonepe_mock.dto.DepositRequestDto;
import com.phonepe.demo.phonepe_mock.dto.TransactionDto;
import com.phonepe.demo.phonepe_mock.dto.TransferRequestDto;
import com.phonepe.demo.phonepe_mock.service.TransactionService;
import com.phonepe.demo.phonepe_mock.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponseDto<TransactionDto>> transferMoney(@Valid @RequestBody TransferRequestDto transferRequest) {
        Long userId = SecurityUtil.getCurrentUserId();
        TransactionDto transaction = transactionService.transferMoney(transferRequest, userId);
        return ResponseEntity.ok(ApiResponseDto.success("Money transfer successful", transaction));
    }

    // ✅ JSON Body version with DepositRequestDto
    @PostMapping("/deposit")
    public ResponseEntity<ApiResponseDto<TransactionDto>> depositMoney(@Valid @RequestBody DepositRequestDto depositRequest) {
        Long userId = SecurityUtil.getCurrentUserId();
        TransactionDto transaction = transactionService.depositMoney(userId, depositRequest.getAccountId(),
                depositRequest.getAmount(), depositRequest.getDescription());
        return ResponseEntity.ok(ApiResponseDto.success("Money deposited successfully", transaction));
    }

    // ✅ JSON Body version with DepositRequestDto (can use same DTO for withdraw)
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponseDto<TransactionDto>> withdrawMoney(@Valid @RequestBody DepositRequestDto withdrawRequest) {
        Long userId = SecurityUtil.getCurrentUserId();
        TransactionDto transaction = transactionService.withdrawMoney(userId, withdrawRequest.getAccountId(),
                withdrawRequest.getAmount(), withdrawRequest.getDescription());
        return ResponseEntity.ok(ApiResponseDto.success("Money withdrawn successfully", transaction));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponseDto<Page<TransactionDto>>> getTransactionHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = SecurityUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionDto> transactions = transactionService.getUserTransactions(userId, pageable);
        return ResponseEntity.ok(ApiResponseDto.success("Transaction history retrieved", transactions));
    }
}
