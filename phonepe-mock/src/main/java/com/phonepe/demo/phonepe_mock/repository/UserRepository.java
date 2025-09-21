package com.phonepe.demo.phonepe_mock.repository;

import com.phonepe.demo.phonepe_mock.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    // Additional useful methods for your PhonePe app
    Optional<User> findByEmailAndActiveTrue(String email);

    Optional<User> findByPhoneNumberAndActiveTrue(String phoneNumber);
}
