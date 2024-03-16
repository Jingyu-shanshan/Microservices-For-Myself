package com.microservices.constant;

import lombok.Getter;

@Getter
public enum DeleteFlag {
    NOT_DELETED(0),
    DELETED(1);

    private final int value;

    DeleteFlag(final int newValue) {
        value = newValue;
    }
}
