package com.microservices.service.interfaces;

import com.microservices.entity.AccountLike;

import java.util.List;

public interface IRedisService {
    void saveLike(String likedAccountId, String likedPostId);

    void unlikePost(String likedAccountId, String likedPostId);

    void deleteLike(String likedAccountId, String likedPostId);

    void incrementLikedCount(String likedAccountId);

    void decrementLikedCount(String likedAccountId);

    List<AccountLike> getLikedDataFromRedis();

}
