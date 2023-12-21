package com.example.ctca.service;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findById(long id);

    Account findByEmail(String email);

    Account findByPhone(String phone);

    Account save(Account account);

    Account save(AccountDTO accountDTO);

    Account register(AccountDTO accountDTO);

    Account verifyAccount(long id);

    List<Account> findByStatusIsTrue();

}
