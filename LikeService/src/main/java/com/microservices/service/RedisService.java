package com.microservices.service;

import com.microservices.constant.LikedStatusEnum;
import com.microservices.entity.AccountLike;
import com.microservices.service.interfaces.IRedisService;
import com.microservices.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RedisService implements IRedisService {
    @Autowired
    RedisTemplate<String, AccountLike> redisTemplate;

    @Override
    public void saveLike(String likedAccountId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedAccountId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_ACCOUNT_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikePost(String likedAccountId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedAccountId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_ACCOUNT_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLike(String likedAccountId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedAccountId, likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_ACCOUNT_LIKED, key);
    }

    @Override
    public void incrementLikedCount(String likedAccountId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_ACCOUNT_LIKED_COUNT, likedAccountId, 1);
    }

    @Override
    public void decrementLikedCount(String likedAccountId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_ACCOUNT_LIKED_COUNT, likedAccountId, -1);
    }

    @Override
    public List<AccountLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_ACCOUNT_LIKED, ScanOptions.NONE);
        List<AccountLike> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            String[] split = key.split("::");
            String likedAccountId = split[0];
            String likedPostId = split[1];
            int value = (int) entry.getValue();

            AccountLike userLike = new AccountLike(likedAccountId, likedPostId, value);
            list.add(userLike);

            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_ACCOUNT_LIKED, key);
        }

        return list;
    }
}
