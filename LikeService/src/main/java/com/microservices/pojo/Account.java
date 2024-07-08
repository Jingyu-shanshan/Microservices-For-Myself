package com.microservices.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    private long id;
    private String name;
    private String accountNumber;
    private String email;
    private String password;
    private Set<Role> roles;
}
