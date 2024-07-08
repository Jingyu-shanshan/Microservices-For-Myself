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
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

import static com.microservices.constant.DeleteFlag.DELETED;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account GetAccountDetail(long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id));
        if (account.getDeleteFlag() == DELETED.getValue()) {
            throw new ResourceNotFoundException("Account", "id", id);
        }
        return account;
    }

    @Override
    public Account GetAccountDetail(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new ResourceNotFoundException("Account", "account number", accountNumber));
        if (account.getDeleteFlag() == DELETED.getValue()) {
            throw new ResourceNotFoundException("Account", "account number", accountNumber);
        }
        return account;
    }

    @Override
    @Transactional
    public Account updateAccount(Long id, Account account) {
        Account existedAccount = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id));
        if (existedAccount.getDeleteFlag() == DELETED.getValue()) {
            throw new ResourceNotFoundException("Account", "id", id);
        }

        updateEntity(account, existedAccount);
        accountRepository.save(existedAccount);
        return existedAccount;
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "id", id));
        if (account.getDeleteFlag() == DELETED.getValue()) {
            throw new ResourceNotFoundException("Account", "id", id);
        }

        account.setDeleteFlag(DELETED.getValue());
        accountRepository.save(account);
    }

    @Override
    public Account getAccount(String nameOrEmail) {
        Account account = accountRepository.findByAccountNumberOrEmail(nameOrEmail, nameOrEmail).orElseThrow(
                () -> new ResourceNotFoundException("Account", "account name or Email", nameOrEmail));
        if (account.getDeleteFlag() == DELETED.getValue()) {
            throw new ResourceNotFoundException("Account", "account name or Email", nameOrEmail);
        }
        return account;
    }

    private static void updateEntity(Account updatedAccount, Account existedAccount) {
        for (Field field : existedAccount.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object newValue = field.get(updatedAccount);
                if (newValue != field.get(existedAccount) && !field.getName().equals("id")){
                    field.set(existedAccount, newValue);
                }
            } catch (IllegalAccessException e) {
                throw new AccountApiException(HttpStatus.BAD_REQUEST, existedAccount.getClass() + ": Class Field:" + field.getName() + " Not Found");
            }
        }
    }
}
