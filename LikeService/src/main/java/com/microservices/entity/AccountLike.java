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

    private String likedAccountId;

    private String likedPostId;

    private int status = LikedStatusEnum.UNLIKE.getCode();

    public AccountLike(String likedAccountId, String likedPostId, int status) {
        this.likedAccountId = likedAccountId;
        this.likedPostId = likedPostId;
        this.status = status;
    }
}
