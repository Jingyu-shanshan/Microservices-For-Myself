package com.microservices.service;

import com.microservices.entity.AccountLike;
import com.microservices.repository.IAccountLikeRepository;
import com.microservices.service.interfaces.ILikeService;
import com.microservices.service.interfaces.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeService implements ILikeService {
    @Autowired
    IAccountLikeRepository accountLikeRepository;

    @Autowired
    IRedisService redisService;

    @Override
    public void save(AccountLike accountLike) {
        accountLikeRepository.save(accountLike);
    }

    @Override
    public void saveAll(List<AccountLike> accountLikes) {
        accountLikeRepository.saveAll(accountLikes);
    }

    @Override
    public AccountLike getByAccountIdAndLikedId(String accountId, String likedId) {
        return accountLikeRepository.findByAccountIdAndLikedId(accountId, likedId).orElse(null);
    }

    @Override
    @Transactional
    public void persistLikedFromRedis() {
        List<AccountLike> likedDataFromRedis = redisService.getLikedDataFromRedis();
        for (AccountLike like : likedDataFromRedis) {
            AccountLike accountLikeFromDB = accountLikeRepository.findByAccountIdAndLikedId(like.getAccountId(), like.getLikedId()).orElse(null);
            if (accountLikeFromDB == null) {
                accountLikeRepository.save(like);
            } else{
                accountLikeFromDB.setStatus(like.getStatus());
            }
        }
    }

    @Override
    public void likePost(String accountId, String likedId) {
        if (!redisService.hasLikePost(accountId, likedId)){
            redisService.saveLikePost(accountId, likedId);
            redisService.incrementLikedCount(likedId);
        }
    }
}
