package com.example.ctca.service.impl;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Role;
import com.example.ctca.repository.AccountRepository;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmailAndStatusIsTrue(email).orElse(null);
    }

    @Override
    public Account findByPhone(String phone) {
        return accountRepository.findByPhone(phone);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account save(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }

        Account account = findById(accountDTO.getId());
        if (account == null) {
            account = new Account();
            String password = "123456";
            String encodedPassword = passwordEncoder.encode(password);
            account.setPassword(encodedPassword);
        }

        Role role = roleService.findByName(accountDTO.getRoleName());
        account.setRole(role);
        // account
        account.setId(accountDTO.getId());
        account.setFullName(accountDTO.getFullName().trim());
        account.setPhone(accountDTO.getPhone().trim());
        account.setEmail(accountDTO.getEmail().trim());
        account.setStatus(accountDTO.isStatus());

        return accountRepository.save(account);
    }

    @Override
    public Account register(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }

        Account account = new Account();
        Role role;

        // role
        role = roleService.findByName("USER");
        account.setRole(role);

        // account
        account.setId(accountDTO.getId());
        account.setFullName(accountDTO.getFullName().trim());
        String encodedPassword = passwordEncoder.encode(accountDTO.getPassword());
        account.setPassword(encodedPassword);
        account.setPhone(accountDTO.getPhone());
        account.setEmail(accountDTO.getEmail().trim());
        account.setStatus(false);

        return accountRepository.save(account);
    }

    @Override
    public Account verifyAccount(long id) {
        Account account = accountRepository.findById(id).orElse(null);
        account.setStatus(true);
        accountRepository.save(account);
        return account;
    }

    @Override
    public List<Account> findByStatusIsTrue() {
        return accountRepository.findByStatusIsTrue();
    }
}
