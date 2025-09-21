package com.phonepe.demo.phonepe_mock.RestControllers;

import com.phonepe.demo.phonepe_mock.dto.ApiResponseDto;
import com.phonepe.demo.phonepe_mock.dto.MerchantDto;
import com.phonepe.demo.phonepe_mock.service.MerchantService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private static final Logger log = LoggerFactory.getLogger(MerchantController.class);

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<MerchantDto>>> getAllMerchants() {
        try {
            List<MerchantDto> merchants = merchantService.getAllMerchants();
            return ResponseEntity.ok(ApiResponseDto.success("Merchants retrieved successfully", merchants));
        } catch (Exception e) {
            log.error("Failed to get all merchants", e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<MerchantDto>> getMerchantById(@PathVariable Long id) {
        try {
            MerchantDto merchant = merchantService.getMerchantById(id);
            return ResponseEntity.ok(ApiResponseDto.success("Merchant retrieved successfully", merchant));
        } catch (Exception e) {
            log.error("Failed to get merchant by ID: {}", id, e);
            throw e;
        }
    }

    @GetMapping("/upi/{upiId}")
    public ResponseEntity<ApiResponseDto<MerchantDto>> getMerchantByUpiId(@PathVariable String upiId) {
        try {
            Optional<MerchantDto> merchant = merchantService.findByUpiId(upiId);
            if (merchant.isPresent()) {
                return ResponseEntity.ok(ApiResponseDto.success("Merchant found", merchant.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("Merchant not found with UPI ID: " + upiId));
            }
        } catch (Exception e) {
            log.error("Failed to get merchant by UPI ID: {}", upiId, e);
            throw e;
        }
    }

    @GetMapping("/account/{accountNo}")
    public ResponseEntity<ApiResponseDto<MerchantDto>> getMerchantByAccountNo(@PathVariable String accountNo) {
        try {
            Optional<MerchantDto> merchant = merchantService.findByAccountNo(accountNo);
            if (merchant.isPresent()) {
                return ResponseEntity.ok(ApiResponseDto.success("Merchant found", merchant.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("Merchant not found with Account No: " + accountNo));
            }
        } catch (Exception e) {
            log.error("Failed to get merchant by account number: {}", accountNo, e);
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<MerchantDto>> createMerchant(
            @Valid @RequestBody MerchantDto merchantDto) {
        try {
            MerchantDto createdMerchant = merchantService.createMerchant(merchantDto);
            log.info("Merchant created: {}", createdMerchant.getName());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDto.success("Merchant created successfully", createdMerchant));
        } catch (Exception e) {
            log.error("Failed to create merchant", e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<MerchantDto>> updateMerchant(
            @PathVariable Long id,
            @Valid @RequestBody MerchantDto merchantDto) {
        try {
            MerchantDto updatedMerchant = merchantService.updateMerchant(id, merchantDto);
            log.info("Merchant updated: {}", id);
            return ResponseEntity.ok(ApiResponseDto.success("Merchant updated successfully", updatedMerchant));
        } catch (Exception e) {
            log.error("Failed to update merchant: {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> deleteMerchant(@PathVariable Long id) {
        try {
            merchantService.deleteMerchant(id);
            log.info("Merchant deleted: {}", id);
            return ResponseEntity.ok(ApiResponseDto.success("Merchant deleted successfully", null));
        } catch (Exception e) {
            log.error("Failed to delete merchant: {}", id, e);
            throw e;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<List<MerchantDto>>> searchMerchantsByName(
            @RequestParam String name) {
        try {
            List<MerchantDto> merchants = merchantService.searchMerchantsByName(name);
            return ResponseEntity.ok(ApiResponseDto.success("Merchants found", merchants));
        } catch (Exception e) {
            log.error("Failed to search merchants by name: {}", name, e);
            throw e;
        }
    }
}
