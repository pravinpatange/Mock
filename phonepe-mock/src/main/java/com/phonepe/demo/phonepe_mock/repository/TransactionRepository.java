package com.phonepe.demo.phonepe_mock.repository;

import com.phonepe.demo.phonepe_mock.entity.Transaction;
import com.phonepe.demo.phonepe_mock.entity.TransactionStatus;
import com.phonepe.demo.phonepe_mock.entity.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.sender.id = :userId OR t.receiver.id = :userId ORDER BY t.createdAt DESC")
    Page<Transaction> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId, Pageable pageable);

    // Additional useful methods for PhonePe functionality
    Optional<Transaction> findByTransactionId(String transactionId);

    List<Transaction> findBySenderIdOrderByCreatedAtDesc(Long senderId);

    List<Transaction> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);

    @Query("SELECT t FROM Transaction t WHERE t.sender.id = :userId AND t.status = :status ORDER BY t.createdAt DESC")
    List<Transaction> findBySenderIdAndStatusOrderByCreatedAtDesc(@Param("userId") Long userId, @Param("status") TransactionStatus status);

    @Query("SELECT t FROM Transaction t WHERE t.receiver.id = :userId AND t.status = :status ORDER BY t.createdAt DESC")
    List<Transaction> findByReceiverIdAndStatusOrderByCreatedAtDesc(@Param("userId") Long userId, @Param("status") TransactionStatus status);

    @Query("SELECT t FROM Transaction t WHERE (t.sender.id = :userId OR t.receiver.id = :userId) AND t.type = :type ORDER BY t.createdAt DESC")
    List<Transaction> findByUserIdAndTypeOrderByCreatedAtDesc(@Param("userId") Long userId, @Param("type") TransactionType type);

    @Query("SELECT t FROM Transaction t WHERE (t.sender.id = :userId OR t.receiver.id = :userId) AND t.createdAt BETWEEN :startDate AND :endDate ORDER BY t.createdAt DESC")
    List<Transaction> findByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    boolean existsByTransactionId(String transactionId);
}
