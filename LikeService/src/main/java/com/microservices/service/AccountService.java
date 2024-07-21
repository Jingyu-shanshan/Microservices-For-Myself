package com.microservices.service;

import com.microservices.pojo.Account;
import com.microservices.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountService implements IAccountService {
    @Value("${restTemplate.account-service.host}")
    private String host;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Account getAccountByToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        String url = "http://" + host + "/api/account/get";
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        Account account = restTemplate.exchange(url, HttpMethod.GET, entity, Account.class).getBody();

        if (account == null){
            throw new UsernameNotFoundException("Account not found. Check Authentication Token");
        }

        return account;
    }
}
