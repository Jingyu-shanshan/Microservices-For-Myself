package com.microservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

import static com.microservices.constant.DeleteFlag.NOT_DELETED;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
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
    private Date createDate;
    @Column(nullable = false)
    private int followingNumber = 0;
    @Column(nullable = false)
    private int followerNumber = 0;
    @Column(nullable = false)
    private int deleteFlag = NOT_DELETED.getValue();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;
}
