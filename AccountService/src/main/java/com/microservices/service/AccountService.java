package com.microservices.service;

import com.microservices.entity.Account;
import com.microservices.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccount(long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public Account createAccount(Account account) {
        account.setDateOfCreate(Date.valueOf(LocalDate.now()));
        accountRepository.save(account);
        return account;
    }
}
