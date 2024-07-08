package com.microservices.constant;

import lombok.Getter;

@Getter
public enum LikedStatusEnum {
    LIKE(1),
    UNLIKE(0),
    ;

    private final int code;

    LikedStatusEnum(int code) {
        this.code = code;
    }

    public static LikedStatusEnum fromCode(int code) {
        for (LikedStatusEnum status : LikedStatusEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
