package com.microservices.controller;

import com.microservices.dto.AccountDto;
import com.microservices.entity.Account;
import com.microservices.service.interfaces.IAccountService;
import jakarta.validation.Valid;
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

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> UpdateAccount(@PathVariable Long id, @Valid @RequestBody AccountDto accountDto){
        Account account = modelMapper.map(accountDto, Account.class);
        Account updatedAccount = accountService.updateAccount(id, account);
        AccountDto updatedAccountDto = modelMapper.map(updatedAccount, AccountDto.class);
        return new ResponseEntity<>(updatedAccountDto, HttpStatus.OK);
    }
}
