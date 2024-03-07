package com.microservices.service;

import com.microservices.dto.AccountLoginDto;
import com.microservices.entity.Account;
import com.microservices.entity.Role;
import com.microservices.repository.AccountRepository;
import com.microservices.repository.RoleRepository;
import com.microservices.security.JwtTokenProvider;
import com.microservices.service.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public String login(AccountLoginDto accountLoginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                accountLoginDto.getAccountNumberOrEmail(), accountLoginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(Account account) {
        if(accountRepository.existsByAccountNumber(account.getAccountNumber())){
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        if(accountRepository.existsByEmail(account.getEmail())){
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER").get();
        roles.add(userRole);
        account.setRoles(roles);

        accountRepository.save(account);

        return account.getName();
    }
}
