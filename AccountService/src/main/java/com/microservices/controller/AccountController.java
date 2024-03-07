package com.microservices.controller;

import com.microservices.dto.AccountDto;
import com.microservices.entity.Account;
import com.microservices.service.interfaces.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> GetAccount(@PathVariable Long id) {
        Account account = accountService.getAccount(id);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        return ResponseEntity.ok(accountDto);
    }
}
