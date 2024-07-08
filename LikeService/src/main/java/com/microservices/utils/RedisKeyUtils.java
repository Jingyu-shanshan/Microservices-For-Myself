package com.microservices.utils;

public class RedisKeyUtils {
    public static final String MAP_KEY_ACCOUNT_LIKED = "MAP_ACCOUNT_LIKED";

    public static final String MAP_KEY_ACCOUNT_LIKED_COUNT = "MAP_ACCOUNT_LIKED_COUNT";

    public static String getLikedKey(String likedAccountId, String likedPostId){
        return likedAccountId +
                "::" +
                likedPostId;
    }
}
