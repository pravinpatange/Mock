# 💸 PhonePe Mock API - Complete Payment System

> A comprehensive **PhonePe-inspired payment application** built with **Spring Boot** featuring JWT authentication, real-time transactions, and secure banking operations.

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)
![JWT](https://img.shields.io/badge/JWT-Authentication-red.svg)
![Maven](https://img.shields.io/badge/Maven-Build-yellow.svg)

## 🚀 **Project Overview**

This project replicates the core functionality of PhonePe, India's leading digital payment platform. Built using modern Spring Boot architecture, it provides secure user authentication, multi-bank account management, and seamless money transfer capabilities.

## ✨ **Features Implemented**

### 🔐 **Authentication & Security**
- **JWT Token-based Authentication** with refresh token support
- **Secure user registration and login** with password encryption
- **Role-based access control** for different user types
- **Token blacklist management** for secure logout
- **Custom security filters** for API protection

### 🏦 **Banking System**
- **Multi-bank account support** (SBI, HDFC, Axis, ICICI, etc.)
- **Real-time balance tracking** and updates
- **Account creation with IFSC validation**
- **Bank-specific account number generation**
- **Account linking and management**

### 💰 **Transaction Management**
- **Money Deposits** with instant balance updates
- **Money Transfers** between users using phone numbers
- **Money Withdrawals** with balance validation
- **Real-time transaction processing** with status tracking
- **Transaction history** with detailed audit trail
- **Insufficient balance protection**

### 📱 **User Management**
- **Complete user profile management**
- **Phone number-based user identification**
- **Email verification system** (ready for implementation)
- **User preferences and settings**

## 🛠️ **Technical Architecture**

### **Backend Technology Stack**
- **Framework:** Spring Boot 3.1.5
- **Security:** Spring Security 6.1.5 with JWT
- **Database:** MySQL 8.0 with JPA/Hibernate
- **Build Tool:** Maven 3.9+
- **Java Version:** Java 17+
- **Validation:** Jakarta Bean Validation

### **Project Structure**
