package com.example.grpcpractice.server.exception;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
    INVALID_METADATA(1004001),
    NOT_ENOUGH_BALANCE(1004000);

    private final int code;

    CustomErrorCode(int code) {
        this.code = code;
    }

}
