package com.phonepe.demo.phonepe_mock.repository;

import com.phonepe.demo.phonepe_mock.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {

    boolean existsByToken(String token);

    // Additional useful methods for token management
    @Modifying
    @Query("DELETE FROM TokenBlacklist tb WHERE tb.blacklistedAt < :cutoffDate")
    void deleteExpiredTokens(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Query("SELECT COUNT(tb) FROM TokenBlacklist tb WHERE tb.blacklistedAt >= :date")
    long countTokensBlacklistedSince(@Param("date") LocalDateTime date);
}
