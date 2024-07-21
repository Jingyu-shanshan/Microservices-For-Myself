package com.microservices.service;

import com.microservices.entity.AccountLike;
import com.microservices.exception.ResourceNotFoundException;
import com.microservices.pojo.Post;
import com.microservices.repository.IAccountLikeRepository;
import com.microservices.service.interfaces.ILikeService;
import com.microservices.service.interfaces.IPostService;
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

    @Autowired
    IPostService postService;

    @Override
    public void save(AccountLike accountLike) {
        accountLikeRepository.save(accountLike);
    }

    @Override
    public void saveAll(List<AccountLike> accountLikes) {
        accountLikeRepository.saveAll(accountLikes);
    }

    @Override
    public AccountLike getByAccountIdAndLikedId(int accountId, int likedId) {
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
    public void persistLikedCountFromRedis() {
        List<Post> postListFromRedis = redisService.getLikedCountFromRedis();
        for(Post postFromRedis : postListFromRedis){
            Post post = postService.getPostById(postFromRedis.getId());
            if (post != null){
                int likeCount = post.getLikeCount() + postFromRedis.getLikeCount();
                post.setLikeCount(likeCount);
                postService.update(post);
            }
        }
    }

    @Override
    public void likePost(int accountId, int likedId) {
        Post post = postService.getPostById(likedId);
        String stringAccountId = String.valueOf(accountId);
        String stringLikedId = String.valueOf(likedId);

        if(post == null){
            throw new ResourceNotFoundException("Post", "id", likedId);
        }

        if (!redisService.isLikedPost(stringAccountId, stringLikedId)){
            redisService.likePost(stringAccountId, stringLikedId);
            redisService.incrementLikedCount(stringLikedId);
        }
    }

    @Override
    public void unlikePost(int accountId, int likedId) {
        Post post = postService.getPostById(likedId);
        String stringAccountId = String.valueOf(accountId);
        String stringLikedId = String.valueOf(likedId);

        if(post == null){
            throw new ResourceNotFoundException("Post", "id", likedId);
        }

        if (redisService.hasLikePost(stringAccountId, stringLikedId)){
            redisService.unlikePost(stringAccountId, stringLikedId);
            redisService.decrementLikedCount(stringLikedId);
        }
    }
}
