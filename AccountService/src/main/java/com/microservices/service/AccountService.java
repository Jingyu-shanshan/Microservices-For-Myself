package com.microservices.service;

import com.microservices.entity.Account;
import com.microservices.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccount(long id) {
        return accountRepository.findById(id).get();
    }
}
