package com.microservices.utils;

public class RedisKeyUtils {
    public static final String MAP_KEY_LIKED = "MAP_LIKED";

    public static final String MAP_KEY_LIKED_COUNT = "MAP_LIKED_COUNT";

    public static String getLikedKey(String accountId, String likedId){
        return accountId +
                "::" +
                likedId;
    }
}
