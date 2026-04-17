package com.zpdh.CatalogApi.domain.category;

import com.zpdh.CatalogApi.shared.error.DomainError;
import com.zpdh.CatalogApi.shared.error.ErrorCode;

public enum CategoryErrors implements DomainError {
    NOT_FOUND("Category not found", ErrorCode.NOT_FOUND),
    ALREADY_EXISTS("Category with this name already exists", ErrorCode.CONFLICT);

    private final String message;
    private final ErrorCode errorCode;

    CategoryErrors(String message, ErrorCode errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
