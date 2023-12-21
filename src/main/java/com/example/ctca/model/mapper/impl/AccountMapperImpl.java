package com.example.ctca.model.mapper.impl;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Role;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.RoleMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    AccountService accountService;

    @Override
    public AccountDTO toDTO(Account account) {
        if (account == null){
            return null;
        }
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setFullName(account.getFullName());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setPhone(account.getPhone());
        accountDTO.setStatus(account.isStatus());

        Role role = account.getRole();
        if (!ObjectUtils.isEmpty(role)) {
            accountDTO.setRoleId(role.getId());
            accountDTO.setRoleDTO(roleMapper.toDTO(account.getRole()));
            accountDTO.setRoleName(role.getName());
        }

        return accountDTO;
    }

    @Override
    public List<AccountDTO> toListDTO(List<Account> accountList) {
        if (accountList == null) {
            return null;
        }
        List<AccountDTO> list = new ArrayList<>(accountList.size());
        for (Account account : accountList) {
            AccountDTO accountDTO = toDTO(account);
            if (accountDTO != null) {
                list.add(accountDTO);
            }
        }
        return list;
    }

    @Override
    public Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null){
            return null;
        }
        Account account = accountService.findById(accountDTO.getId());
        if (account == null)
            account = new Account();
        account.setFullName(accountDTO.getFullName());
        account.setEmail(accountDTO.getEmail());
        account.setPhone(accountDTO.getPhone());
        if (accountDTO.getRoleId() != 0) {
            account.setRole(roleService.findById(accountDTO.getRoleId()));
        }

        return account;
    }
}
