package com.example.ctca.model.mapper;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.entity.Account;

import java.util.List;

public interface AccountMapper {

    AccountDTO toDTO(Account account);

    List<AccountDTO> toListDTO(List<Account> accountList);

    Account toEntity(AccountDTO accountDTO);
}
