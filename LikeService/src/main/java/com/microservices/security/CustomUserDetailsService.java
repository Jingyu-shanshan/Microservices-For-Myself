package com.microservices.security;

import com.microservices.pojo.Account;
import com.microservices.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService {
    @Autowired
    private IAccountService accountService;

    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        Account account = accountService.getAccountByToken(token);

        Set<GrantedAuthority> authorities = account
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(account.getAccountNumber(),
                account.getPassword(),
                authorities);
    }
}
