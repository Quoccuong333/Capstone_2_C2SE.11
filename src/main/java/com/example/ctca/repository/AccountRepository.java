package com.example.ctca.repository;

import com.example.ctca.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmailAndStatusIsTrue(String email);

    Account findByPhone(String phone);

    List<Account> findByStatusIsTrue();
}
