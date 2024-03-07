package com.microservices.controller;

import com.microservices.dto.AccountDto;
import com.microservices.dto.AccountLoginDto;
import com.microservices.entity.Account;
import com.microservices.payload.JwtAuthResponse;
import com.microservices.service.interfaces.IAuthService;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;

    @Autowired
    private ModelMapper modelMapper;
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody AccountLoginDto accountLoginDto){
        String token = authService.login(accountLoginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AccountDto accountDto){
        Account account = modelMapper.map(accountDto, Account.class);

        val accountName = authService.register(account);

        return new ResponseEntity<>(accountName, HttpStatus.CREATED);
    }
}
