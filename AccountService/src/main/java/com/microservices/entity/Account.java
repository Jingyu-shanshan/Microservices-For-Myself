package com.microservices.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String accountNumber;
    private String email;
    private String password;
    private String introduction;
    private String location;
    private String websites;
    private String dateOfBirth;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String userImage;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String userBackgroundImage;
    private Date dateOfCreate;
    @Column(nullable = false)
    private int followingNumber = 0;
    @Column(nullable = false)
    private int followerNumber = 0;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;
}
