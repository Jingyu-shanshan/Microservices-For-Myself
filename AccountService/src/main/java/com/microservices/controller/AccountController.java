package com.microservices.controller;

import com.microservices.dto.AccountDto;
import com.microservices.dto.AccountRoleDto;
import com.microservices.dto.RoleDto;
import com.microservices.entity.Account;
import com.microservices.service.interfaces.IAccountService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDto> GetAccount(@PathVariable Long id) {
        Account account = accountService.GetAccountDetail(id);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDto> UpdateAccount(@PathVariable Long id, @Valid @RequestBody AccountDto accountDto) {
        Account account = modelMapper.map(accountDto, Account.class);
        Account updatedAccount = accountService.updateAccount(id, account);
        AccountDto updatedAccountDto = modelMapper.map(updatedAccount, AccountDto.class);
        return new ResponseEntity<>(updatedAccountDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> DeleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get")
    public ResponseEntity<AccountDto> getAccountDetail() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Account currentAccount = accountService.GetAccountDetail(currentUsername);
        AccountDto accountDto = modelMapper.map(currentAccount, AccountDto.class);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/get/{nameOrEmail}")
    public ResponseEntity<AccountRoleDto> getAccountByNameOrEmail(@PathVariable String nameOrEmail) {
        Account account = accountService.getAccount(nameOrEmail);
        AccountRoleDto accountDto = modelMapper.map(account, AccountRoleDto.class);
        Set<RoleDto> roleDtos = account.getRoles().stream().map(item -> modelMapper.map(item, RoleDto.class)).collect(Collectors.toSet());
        accountDto.setRoles(roleDtos);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/update")
    public ResponseEntity<AccountDto> UpdateAccount(@Valid @RequestBody AccountDto accountDto) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Account currentAccount = accountService.GetAccountDetail(currentUsername);

        Account account = modelMapper.map(accountDto, Account.class);
        Account updatedAccount = accountService.updateAccount(currentAccount.getId(), account);

        AccountDto updatedAccountDto = modelMapper.map(updatedAccount, AccountDto.class);
        return new ResponseEntity<>(updatedAccountDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> DeleteAccount() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Account currentAccount = accountService.GetAccountDetail(currentUsername);

        accountService.deleteAccount(currentAccount.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
