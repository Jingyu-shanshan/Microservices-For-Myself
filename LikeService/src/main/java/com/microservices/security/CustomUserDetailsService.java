package com.microservices.security;

import com.microservices.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService {
    @Value("${restTemplate.host}")
    private String host;

    @Autowired
    private RestTemplate restTemplate;

    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        String url = "http://" + host + "/api/account/get";
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        Account account = restTemplate.exchange(url, HttpMethod.GET, entity, Account.class).getBody();

        if (account == null){
            throw new UsernameNotFoundException("Token error.");
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
