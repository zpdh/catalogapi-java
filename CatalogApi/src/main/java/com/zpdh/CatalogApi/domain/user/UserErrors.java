package com.zpdh.CatalogApi.domain.user;

import com.zpdh.CatalogApi.shared.error.DomainError;
import com.zpdh.CatalogApi.shared.error.ErrorCode;

public enum UserErrors implements DomainError {
    ALREADY_EXISTS("User with this email already exists", ErrorCode.CONFLICT),
    INVALID_CREDENTIALS("Invalid email or password", ErrorCode.UNAUTHORIZED);

    private final String message;
    private final ErrorCode code;

    UserErrors(String message, ErrorCode code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return code;
    }
}
