package com.phonepe.demo.phonepe_mock.service;

import com.phonepe.demo.phonepe_mock.dto.LoginRequestDto;
import com.phonepe.demo.phonepe_mock.dto.LoginResponseDto;
import com.phonepe.demo.phonepe_mock.dto.UserDto;
import com.phonepe.demo.phonepe_mock.dto.UserProfileResponse;
import com.phonepe.demo.phonepe_mock.dto.UserRegisterRequest;
import com.phonepe.demo.phonepe_mock.entity.TokenBlacklist;
import com.phonepe.demo.phonepe_mock.entity.User;
import com.phonepe.demo.phonepe_mock.exception.InvalidCredentialsException;
import com.phonepe.demo.phonepe_mock.exception.ResourceNotFoundException;
import com.phonepe.demo.phonepe_mock.repository.TokenBlacklistRepository;
import com.phonepe.demo.phonepe_mock.repository.UserRepository;
import com.phonepe.demo.phonepe_mock.security.JwtTokenProvider;
import com.phonepe.demo.phonepe_mock.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private EmailService emailService;

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        if (!user.getActive()) {
            throw new InvalidCredentialsException("Account is deactivated");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());
        UserDto userDto = ModelMapper.toUserDto(user);

        // Send login notification email
        try {
            emailService.sendLoginNotification(user.getEmail(), user.getFirstName());
        } catch (Exception e) {
            System.err.println("Failed to send login notification email to " + user.getEmail() + ": " + e.getMessage());
        }

        return new LoginResponseDto(token, userDto);
    }

    @Transactional
    public UserProfileResponse register(UserRegisterRequest registerRequest) {
        // Check if user already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new InvalidCredentialsException("User already exists with email: " + registerRequest.getEmail());
        }

        if (userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            throw new InvalidCredentialsException("User already exists with phone number: " + registerRequest.getPhoneNumber());
        }

        // Create new user
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setActive(true);
        user.setKycStatus(User.KycStatus.PENDING);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        // Send welcome email
        try {
            emailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getFirstName());
        } catch (Exception e) {
            System.err.println("Failed to send welcome email to " + savedUser.getEmail() + ": " + e.getMessage());
        }

        return new UserProfileResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getPhoneNumber(),
                savedUser.getActive(),
                savedUser.getKycStatus().toString(),
                savedUser.getCreatedAt()
        );
    }

    // NEW METHOD: Get user profile using JWT token
    public UserProfileResponse getProfile(String token) {
        String email = jwtTokenProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getActive(),
                user.getKycStatus().toString(),
                user.getCreatedAt()
        );
    }

    @Transactional
    public void logout(String token) {
        TokenBlacklist tokenBlacklist = new TokenBlacklist();
        tokenBlacklist.setToken(token);
        tokenBlacklist.setBlacklistedAt(LocalDateTime.now());
        tokenBlacklistRepository.save(tokenBlacklist);
        System.out.println("Token blacklisted successfully");
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.existsByToken(token);
    }

    public UserDto getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ModelMapper.toUserDto(user);
    }
}
