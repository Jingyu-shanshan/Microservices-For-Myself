package com.microservices.repository;

import com.microservices.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumberOrEmail(String accountNumber, String email);

    Boolean existsByAccountNumber(String accountNumber);

    Boolean existsByEmail(String email);
}
