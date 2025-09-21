package com.phonepe.demo.phonepe_mock.controller;

import com.phonepe.demo.phonepe_mock.dto.ApiResponseDto;
import com.phonepe.demo.phonepe_mock.dto.LoginRequestDto;
import com.phonepe.demo.phonepe_mock.dto.LoginResponseDto;
import com.phonepe.demo.phonepe_mock.dto.UserProfileResponse;
import com.phonepe.demo.phonepe_mock.dto.UserRegisterRequest;
import com.phonepe.demo.phonepe_mock.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<UserProfileResponse>> register(@Valid @RequestBody UserRegisterRequest request) {
        UserProfileResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Registration successful", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto response = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponseDto.success("Login successful", response));
    }

    // THIS WAS MISSING!
    @GetMapping("/profile")
    public ResponseEntity<ApiResponseDto<UserProfileResponse>> getProfile(HttpServletRequest request) {
        String token = getJwtFromRequest(request);
        if (!StringUtils.hasText(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDto.error("Authorization token required"));
        }

        UserProfileResponse response = authService.getProfile(token);
        return ResponseEntity.ok(ApiResponseDto.success("Profile retrieved successfully", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto<Void>> logout(HttpServletRequest request) {
        String token = getJwtFromRequest(request);
        if (StringUtils.hasText(token)) {
            authService.logout(token);
        }
        return ResponseEntity.ok(ApiResponseDto.success("Logout successful", null));
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
