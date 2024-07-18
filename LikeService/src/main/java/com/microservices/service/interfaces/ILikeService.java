package com.microservices.service.interfaces;

import com.microservices.entity.AccountLike;

import java.util.List;

public interface ILikeService {
    void save(AccountLike accountLike);

    void saveAll(List<AccountLike> accountLikes);

    AccountLike getByAccountIdAndLikedId(int accountId, int likedId);

    void persistLikedFromRedis();

    void likePost(int accountId, int likedId);

    void unlikePost(int accountId, int likedId);
}
