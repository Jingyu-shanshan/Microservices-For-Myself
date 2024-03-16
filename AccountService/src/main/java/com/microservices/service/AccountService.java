package com.microservices.service;

import com.microservices.entity.Account;
import com.microservices.exception.AccountApiException;
import com.microservices.exception.ResourceNotFoundException;
import com.microservices.repository.AccountRepository;
import com.microservices.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account getAccount(long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id));
    }

    @Override
    public Account updateAccount(Long id, Account account) {
        Account existedAccount = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id));
        updateEntity(account, existedAccount);
        existedAccount.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(existedAccount);
        return existedAccount;
    }

    private static void updateEntity(Account updatedAccount, Account existedAccount) {
        for (Field field : existedAccount.getClass().getDeclaredFields()){
            try {
                field.setAccessible(true);
                Object newValue = field.get(updatedAccount);
                field.set(existedAccount, newValue);
            } catch (IllegalAccessException e) {
                throw new AccountApiException(HttpStatus.BAD_REQUEST, existedAccount.getClass() + ": Class Field:" + field.getName() + " Not Found");
            }
        }
    }
}
