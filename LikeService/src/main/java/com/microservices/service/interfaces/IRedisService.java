package com.microservices.service.interfaces;

import com.microservices.entity.AccountLike;

import java.util.List;

public interface IRedisService {
    void likePost(String accountId, String likedId);

    void unlikePost(String accountId, String likedId);

    void deleteLike(String accountId, String likedId);

    Boolean hasLikePost(String accountId, String likedId);

    Boolean isLikedPost(String accountId, String likedId);

    void incrementLikedCount(String likedId);

    void decrementLikedCount(String likedId);

    List<AccountLike> getLikedDataFromRedis();

}
