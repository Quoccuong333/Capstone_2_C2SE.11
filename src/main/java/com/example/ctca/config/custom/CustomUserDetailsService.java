package com.example.ctca.config.custom;

import com.example.ctca.model.entity.Account;
import com.example.ctca.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmailAndStatusIsTrue(email).orElse(null);
        if (account != null) {
            CustomUserDetails customUserDetails = new CustomUserDetails(account);
            return customUserDetails;
        }

        return null;
    }

}
