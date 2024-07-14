package com.microservices.service.interfaces;

import com.microservices.entity.AccountLike;

import java.util.List;

public interface ILikeService {
    void save(AccountLike accountLike);

    void saveAll(List<AccountLike> accountLikes);

    AccountLike getByAccountIdAndLikedId(String accountId, String likedId);

    void persistLikedFromRedis();

    void likePost(String accountId, String likedId);
}
