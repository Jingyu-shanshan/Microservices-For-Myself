package com.microservices.entity;

import com.microservices.constant.LikedStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_like")
public class AccountLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String accountId;

    private String likedId;

    private int status = LikedStatusEnum.UNLIKE.getCode();

    public AccountLike(String accountId, String likedId, int status) {
        this.accountId = accountId;
        this.likedId = likedId;
        this.status = status;
    }
}
