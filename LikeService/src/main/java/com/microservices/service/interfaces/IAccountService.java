package com.microservices.service.interfaces;

import com.microservices.pojo.Account;

public interface IAccountService {
    Account getAccountByToken(String token);
}
