package com.microservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
}
