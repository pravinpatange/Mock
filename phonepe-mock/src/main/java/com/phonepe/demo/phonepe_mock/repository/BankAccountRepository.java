package com.phonepe.demo.phonepe_mock.repository;

import com.phonepe.demo.phonepe_mock.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    // Find active accounts by user ID
    List<BankAccount> findByUserIdAndActiveTrue(Long userId);

    // Find account by ID and user ID (with active status)
    @Query("SELECT ba FROM BankAccount ba WHERE ba.id = :accountId AND ba.user.id = :userId AND ba.active = true")
    Optional<BankAccount> findByIdAndUserIdAndActiveTrue(@Param("accountId") Long accountId, @Param("userId") Long userId);

    // *** MISSING METHOD - This is what's causing your error ***
    boolean existsByAccountNumber(String accountNumber);

    // Find all accounts by user ID (including inactive)
    List<BankAccount> findByUserId(Long userId);

    // Find active account by account number
    Optional<BankAccount> findByAccountNumberAndActiveTrue(String accountNumber);

    // Find account by account number (including inactive)
    Optional<BankAccount> findByAccountNumber(String accountNumber);

    // Count active accounts by user ID
    @Query("SELECT COUNT(ba) FROM BankAccount ba WHERE ba.user.id = :userId AND ba.active = true")
    long countActiveAccountsByUserId(@Param("userId") Long userId);

    // Find accounts by bank name
    List<BankAccount> findByBankNameAndActiveTrue(String bankName);

    // Find accounts by IFSC code
    List<BankAccount> findByIfscCodeAndActiveTrue(String ifscCode);

    // Check if account exists with account number and is active
    boolean existsByAccountNumberAndActiveTrue(String accountNumber);

    // Custom query to find accounts with balance greater than specified amount
    @Query("SELECT ba FROM BankAccount ba WHERE ba.user.id = :userId AND ba.balance > :minBalance AND ba.active = true")
    List<BankAccount> findByUserIdAndBalanceGreaterThanAndActiveTrue(@Param("userId") Long userId, @Param("minBalance") java.math.BigDecimal minBalance);

    // Find accounts by user and bank name
    @Query("SELECT ba FROM BankAccount ba WHERE ba.user.id = :userId AND ba.bankName = :bankName AND ba.active = true")
    List<BankAccount> findByUserIdAndBankNameAndActiveTrue(@Param("userId") Long userId, @Param("bankName") String bankName);
}
