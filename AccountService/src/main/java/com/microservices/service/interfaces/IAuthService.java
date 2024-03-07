package com.microservices.service.interfaces;

import com.microservices.dto.AccountLoginDto;
import com.microservices.entity.Account;

public interface IAuthService {
    String login(AccountLoginDto accountLoginDto);

    String register(Account account);
}
