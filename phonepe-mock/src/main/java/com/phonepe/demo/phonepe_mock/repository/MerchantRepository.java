package com.phonepe.demo.phonepe_mock.repository;

import com.phonepe.demo.phonepe_mock.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Optional<Merchant> findByUpiId(String upiId);

    Optional<Merchant> findByAccountNo(String accountNo);

    // Additional useful methods
    List<Merchant> findByNameContainingIgnoreCase(String name);

    boolean existsByUpiId(String upiId);

    boolean existsByAccountNo(String accountNo);

    Optional<Merchant> findByContact(String contact);
}
