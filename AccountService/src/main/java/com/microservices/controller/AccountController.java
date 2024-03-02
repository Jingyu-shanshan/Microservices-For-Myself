package com.microservices.controller;

import com.microservices.DTO.AccountDto;
import com.microservices.entity.Account;
import com.microservices.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    public ResponseEntity<AccountDto> RegisterAccount(@RequestBody AccountDto accountDto) {
        Account accountToCreate = modelMapper.map(accountDto, Account.class);
        Account accountCreated = accountService.createAccount(accountToCreate);
        return new ResponseEntity<>(modelMapper.map(accountCreated, AccountDto.class), HttpStatus.CREATED);
    }
}
