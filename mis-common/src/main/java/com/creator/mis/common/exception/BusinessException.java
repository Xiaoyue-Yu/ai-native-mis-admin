package com.creator.mis.common.exception;

import lombok.Getter;

/**
 * Business rule violations (coding-standards.md).
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(String message) {
        this(400, message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}
