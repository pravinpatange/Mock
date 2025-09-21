
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
```
src/main/java/com/phonepe/demo/phonepe_mock/
├── 📁 controller/          # REST API endpoints
│   ├── AuthController.java         # Authentication endpoints
│   ├── BankAccountController.java  # Account management
│   ├── TransactionController.java  # Transaction operations
│   └── MerchantController.java     # Merchant operations
│
├── 📁 service/             # Business logic layer
│   ├── AuthService.java            # Authentication logic
│   ├── TransactionService.java     # Transaction processing
│   ├── BankAccountService.java     # Account operations
│   ├── MerchantService.java        # Merchant services
│   └── EmailService.java           # Email notifications
│
├── 📁 entity/              # Database entities
│   ├── User.java                   # User entity with relationships
│   ├── BankAccount.java            # Bank account details
│   ├── Transaction.java            # Transaction records
│   ├── Merchant.java               # Merchant information
│   └── TokenBlacklist.java         # JWT token management
│
├── 📁 repository/          # Data access layer
│   ├── UserRepository.java
│   ├── BankAccountRepository.java
│   ├── TransactionRepository.java
│   ├── MerchantRepository.java
│   └── TokenBlacklistRepository.java
│
├── 📁 dto/                 # Data Transfer Objects
│   ├── 📁 request/         # API request DTOs
│   └── 📁 response/        # API response DTOs
│
├── 📁 security/            # Security configuration
│   ├── JwtAuthenticationFilter.java
│   ├── JwtTokenProvider.java
│   ├── CustomUserDetailsService.java
│   └── SecurityUtil.java
│
├── 📁 exception/           # Exception handling
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── InsufficientBalanceException.java
│   └── InvalidCredentialsException.java
│
└── 📁 util/                # Utility classes
    └── ModelMapper.java            # Object mapping utilities
```

## 🔌 **API Endpoints**

### **🔐 Authentication APIs**
```http
POST   /api/auth/register     # User registration
POST   /api/auth/login        # User login
POST   /api/auth/logout       # Secure logout
GET    /api/auth/profile      # User profile
```

### **🏦 Bank Account APIs**
```http
POST   /api/accounts          # Create bank account
GET    /api/accounts          # Get user accounts
GET    /api/accounts/{id}     # Get specific account
PUT    /api/accounts/{id}     # Update account details
```

### **💸 Transaction APIs**
```http
POST   /api/transactions/deposit     # Deposit money
POST   /api/transactions/withdraw    # Withdraw money
POST   /api/transactions/transfer    # Transfer to other users
GET    /api/transactions/history     # Transaction history
GET    /api/transactions/{id}        # Transaction details
```

### **🏪 Merchant APIs**
```http
POST   /api/merchants         # Register merchant
GET    /api/merchants         # Get merchant details
POST   /api/merchants/payment # Process merchant payments
```

## 🎯 **Core Features Deep Dive**

### **💰 Money Transfer Flow**
1. **User Authentication** via JWT token
2. **Sender Account Validation** and balance check
3. **Receiver Identification** by phone number
4. **Real-time Balance Updates** for both accounts
5. **Transaction Record Creation** with complete audit trail
6. **Email Notifications** (configurable)

### **🔒 Security Implementation**
- **JWT Token Management** with secure generation and validation
- **Password Encryption** using BCrypt hashing
- **API Rate Limiting** protection against abuse
- **SQL Injection Prevention** via JPA parameterized queries
- **CORS Configuration** for frontend integration

## 🚀 **Getting Started**

### **Prerequisites**
- Java 17 or higher
- MySQL 8.0+
- Maven 3.6+
- IDE (IntelliJ IDEA/Eclipse)

### **Installation**

1. **Clone the repository**
   ```bash
   git clone https://github.com/pravinpatange/Mock.git
   cd Mock
   ```

2. **Configure MySQL Database**
   ```sql
   CREATE DATABASE phonepe_mock;
   CREATE USER 'phonepe_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON phonepe_mock.* TO 'phonepe_user'@'localhost';
   ```

3. **Update Application Properties**
   ```properties
   # Database Configuration
   spring.datasource.url=jdbc:mysql://localhost:3306/phonepe_mock
   spring.datasource.username=phonepe_user
   spring.datasource.password=your_password

   # JWT Configuration
   jwt.secret=your-secret-key-here
   jwt.expiration=86400000

   # Email Configuration (optional)
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-app-password
   ```

4. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access the Application**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html` (if configured)

## 📝 **API Usage Examples**

### **User Registration**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@phonepe.com",
    "phoneNumber": "9876543210",
    "password": "securePassword123"
  }'
```

### **User Login**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@phonepe.com",
    "password": "securePassword123"
  }'
```

### **Create Bank Account**
```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "bankName": "State Bank of India",
    "accountNumber": "1234567890123456",
    "ifscCode": "SBIN0001234",
    "balance": 10000.00
  }'
```

### **Transfer Money**
```bash
curl -X POST http://localhost:8080/api/transactions/transfer \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "senderAccountId": 1,
    "receiverPhoneNumber": "8765432109",
    "amount": 2500.00,
    "description": "Rent payment"
  }'
```

## 🔮 **Future Enhancements**

### **Phase 2 Features**
- [ ] **QR Code Payments** with dynamic QR generation
- [ ] **Bill Payment Integration** (electricity, gas, mobile)
- [ ] **Investment Features** (mutual funds, gold)
- [ ] **Merchant Dashboard** with analytics
- [ ] **Mobile App Integration** (React Native/Flutter)

### **Phase 3 Features**
- [ ] **Microservices Architecture** migration
- [ ] **Real-time Notifications** via WebSocket
- [ ] **Advanced Security** with 2FA
- [ ] **AI-powered Fraud Detection**
- [ ] **Multi-language Support**

## 🧪 **Testing**

### **Manual Testing Guide**
1. **Register Users:** Create multiple test users
2. **Create Accounts:** Add bank accounts for each user
3. **Test Deposits:** Verify balance updates
4. **Test Transfers:** Transfer money between users
5. **Check History:** Verify transaction records

### **Test Data**
```json
{
  "testUsers": [
    {
      "name": "John Doe",
      "email": "john@phonepe.com",
      "phone": "9876543210"
    },
    {
      "name": "Jane Smith", 
      "email": "jane@phonepe.com",
      "phone": "8765432109"
    }
  ]
}
```

## 📊 **Database Schema**

### **Key Tables**
- **users** - User information and authentication
- **bank_accounts** - User bank account details
- **transactions** - All transaction records
- **merchants** - Merchant information
- **token_blacklist** - JWT token management

## 🤝 **Contributing**

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Create Pull Request

## 📄 **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 **Developer**

**Pravin Patange**
- GitHub: [@pravinpatange](https://github.com/pravinpatange)
- LinkedIn: [Pravin Patange](https://linkedin.com/in/pravinpatange)
- Email: pravin.patange@example.com

## 🙏 **Acknowledgments**

- Inspired by PhonePe's innovative payment solutions
- Built with Spring Boot and modern Java technologies
- Special thanks to the open-source community

---

⭐ **Star this repository if you found it helpful!**

📱 *Building the future of digital payments, one transaction at a time.*
