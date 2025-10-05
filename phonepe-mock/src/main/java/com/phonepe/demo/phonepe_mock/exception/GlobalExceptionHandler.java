package com.phonepe.demo.phonepe_mock.exception;

import com.phonepe.demo.phonepe_mock.dto.ApiResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponseDto<String>> handleInvalidCredentials(
            InvalidCredentialsException ex, HttpServletRequest request) {
        log.warn("Invalid credentials attempt from IP: {} - {}",
                getClientIpAddress(request), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto<String>> handleResourceNotFound(
            ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponseDto<String>> handleInsufficientBalance(
            InsufficientBalanceException ex) {
        log.warn("Insufficient balance: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDto<String>> handleUserAlreadyExists(
            UserAlreadyExistsException ex) {
        log.warn("User already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    @ExceptionHandler(TransactionFailedException.class)
    public ResponseEntity<ApiResponseDto<String>> handleTransactionFailed(
            TransactionFailedException ex) {
        log.error("Transaction failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponseDto<String>> handleUnauthorizedAccess(
            UnauthorizedAccessException ex) {
        log.warn("Unauthorized access: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseDto<String>> handleAccessDenied(
            AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponseDto.error("Access denied"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseDto<String>> handleBadCredentials(
            BadCredentialsException ex) {
        log.warn("Bad credentials: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseDto.error("Invalid email or password"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponseDto<String>> handleAuthentication(
            AuthenticationException ex) {
        log.warn("Authentication failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseDto.error("Authentication failed"));
    }

    // 🔄 HANDLE CONCURRENT TRANSACTION CONFLICTS
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ApiResponseDto<String>> handleOptimisticLocking(
            ObjectOptimisticLockingFailureException ex) {
        log.warn("⚡ Concurrent transaction detected - will retry: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponseDto.error("Transaction conflict detected. Please try again in a moment."));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDto<String>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {
        log.error("Data integrity violation: {}", ex.getMessage());
        String message = "Data integrity violation. Please check your input.";
        if (ex.getMessage().contains("email")) {
            message = "Email already exists";
        } else if (ex.getMessage().contains("phone")) {
            message = "Phone number already exists";
        } else if (ex.getMessage().contains("account_number")) {
            message = "Account number already exists";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponseDto.error(message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.warn("Validation failed: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDto<>(false, "Validation failed", errors));
    }

    @ExceptionHandler(EmailServiceException.class)
    public ResponseEntity<ApiResponseDto<String>> handleEmailService(
            EmailServiceException ex) {
        log.error("Email service error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.error("Email service temporarily unavailable"));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiResponseDto<String>> handleSecurity(
            SecurityException ex) {
        log.error("Security exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponseDto.error("Security violation detected"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<String>> handleIllegalArgument(
            IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDto<String>> handleRuntime(
            RuntimeException ex) {
        log.error("Runtime exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.error("A runtime error occurred"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<ErrorDetails>> handleGeneral(
            Exception ex, WebRequest webRequest) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "An unexpected error occurred",
                webRequest.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.error("An unexpected error occurred", errorDetails));
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return xForwardedForHeader.split(",")[0];
        }
    }
}
