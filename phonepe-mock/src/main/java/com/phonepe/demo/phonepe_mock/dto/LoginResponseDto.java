package com.phonepe.demo.phonepe_mock.dto;

public class LoginResponseDto {

    private String token;
    private String tokenType = "Bearer";
    private UserDto user;

    // Constructors
    public LoginResponseDto() {}

    public LoginResponseDto(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    public LoginResponseDto(String token, String tokenType, UserDto user) {
        this.token = token;
        this.tokenType = tokenType;
        this.user = user;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
