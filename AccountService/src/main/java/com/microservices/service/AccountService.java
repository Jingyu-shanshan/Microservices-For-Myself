package com.microservices.service;

import com.microservices.constant.AuthenticationConst;
import com.microservices.entity.Account;
import com.microservices.repository.AccountRepository;
import com.microservices.utility.Authentication;
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
        try {
            String encryptedPassword = Authentication.encrypt(account.getPassword(), AuthenticationConst.SecretKey, AuthenticationConst.Salt);
            account.setPassword(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        accountRepository.save(account);
        return account;
    }
}
