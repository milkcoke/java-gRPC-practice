package com.example.grpcpractice.server.exception;

import lombok.Getter;

/** This class is immutable.
 * It doesn't require stack trace since it's just return error message predefined.
* */

@Getter
public class CustomException extends RuntimeException {
    private final int code;

    public CustomException(CustomErrorCode customErrorCode) {
        super(customErrorCode.name());
        this.code = customErrorCode.getCode();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        // don't need stack trace by super.
        return this;
    }
}
