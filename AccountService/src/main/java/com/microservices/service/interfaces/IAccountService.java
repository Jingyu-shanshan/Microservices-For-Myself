package com.microservices.service.interfaces;

import com.microservices.entity.Account;
import org.springframework.security.core.userdetails.User;

public interface IAccountService {
    Account getAccount(long id);

    Account updateAccount(Long id, Account account);
}
