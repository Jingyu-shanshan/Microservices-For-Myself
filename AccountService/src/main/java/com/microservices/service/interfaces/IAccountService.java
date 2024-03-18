package com.microservices.service.interfaces;

import com.microservices.entity.Account;

public interface IAccountService {
    Account GetAccountDetail(long id);

    Account GetAccountDetail(String accountNumber);

    Account updateAccount(Long id, Account account);

    void deleteAccount(Long id);
}
