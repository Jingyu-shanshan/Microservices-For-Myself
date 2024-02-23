package com.microservices.service;

import com.microservices.entity.Account;

public interface IAccountService {
    Account getAccount(long id);
}
