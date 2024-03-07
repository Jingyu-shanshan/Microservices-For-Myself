package com.microservices.security;

import com.microservices.entity.Account;
import com.microservices.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String accountNumberOrEmail) throws UsernameNotFoundException {
          Account account = accountRepository.findByAccountNumberOrEmail(accountNumberOrEmail, accountNumberOrEmail)
                 .orElseThrow(() ->
                         new UsernameNotFoundException("User not found with account number or email: "+ accountNumberOrEmail));

        Set<GrantedAuthority> authorities = account
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(account.getEmail(),
                account.getPassword(),
                authorities);
    }
}
