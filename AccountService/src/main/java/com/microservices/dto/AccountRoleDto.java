package com.microservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

import static com.microservices.constant.DeleteFlag.NOT_DELETED;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountRoleDto {
    private long id;
    private String name;
    private String accountNumber;
    private String email;
    private String password;
    private String introduction;
    private String location;
    private String websites;
    private String dateOfBirth;
    private String userImage;
    private String userBackgroundImage;
    private Date createDate;
    private int followingNumber = 0;
    private int followerNumber = 0;
    private int deleteFlag = NOT_DELETED.getValue();
    private Set<RoleDto> roles;
}
