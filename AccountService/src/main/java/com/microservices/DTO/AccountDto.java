package com.microservices.DTO;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto {
    private long id;
    private String name;
    private String accountNumber;
    private String introduction;
    private String location;
    private String websites;
    private String dateOfBirth;
    private String userImage;
    private String userBackgroundImage;
    private Date dateOfCreate;
    private int followingNumber = 0;
    private int followerNumber = 0;
}
