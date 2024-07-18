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
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void likePost(String accountId, String likedId) {
        String key = RedisKeyUtils.getLikedKey(accountId, likedId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikePost(String accountId, String likedId) {
        String key = RedisKeyUtils.getLikedKey(accountId, likedId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLike(String accountId, String likedId) {
        String key = RedisKeyUtils.getLikedKey(accountId, likedId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_LIKED, key);
    }

    @Override
    public Boolean hasLikePost(String accountId, String likedId) {
        String key = RedisKeyUtils.getLikedKey(accountId, likedId);
        return redisTemplate.opsForHash().hasKey(RedisKeyUtils.MAP_KEY_LIKED, key);
    }

    @Override
    public Boolean isLikedPost(String accountId, String likedId) {
        String key = RedisKeyUtils.getLikedKey(accountId, likedId);
        Object likedStatusEnumCode = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_LIKED, key);

        if (likedStatusEnumCode != null){
            int code = Integer.parseInt(likedStatusEnumCode.toString());
            return LikedStatusEnum.LIKE.getCode() == code;
        }

        return false;
    }

    @Override
    public void incrementLikedCount(String likedId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_LIKED_COUNT, likedId, 1);
    }

    @Override
    public void decrementLikedCount(String likedId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_LIKED_COUNT, likedId, -1);
    }

    @Override
    public List<AccountLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_LIKED, ScanOptions.NONE);
        List<AccountLike> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            String[] split = key.split("::");
            int likedAccountId = Integer.parseInt(split[0]);
            int likedPostId = Integer.parseInt(split[1]);
            int value = (int) entry.getValue();

            AccountLike accountLike = new AccountLike(likedAccountId, likedPostId, value);
            list.add(accountLike);

            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_LIKED, key);
        }

        return list;
    }
}
