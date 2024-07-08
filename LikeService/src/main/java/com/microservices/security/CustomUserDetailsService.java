package com.microservices.security;

import com.microservices.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String accountNumberOrEmail) throws UsernameNotFoundException {
        String url = "localhost:8080/api/account/get/" + accountNumberOrEmail;
        Account account = restTemplate.getForObject(url, Account.class);

        if (account == null){
            throw new UsernameNotFoundException("User not found with account number or email: "+ accountNumberOrEmail);
        }

        Set<GrantedAuthority> authorities = account
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(account.getAccountNumber(),
                account.getPassword(),
                authorities);
    }
}
