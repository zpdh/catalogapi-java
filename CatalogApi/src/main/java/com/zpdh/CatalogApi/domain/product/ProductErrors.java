package com.zpdh.CatalogApi.domain.product;

import com.zpdh.CatalogApi.shared.error.DomainError;
import com.zpdh.CatalogApi.shared.error.ErrorCode;

public enum ProductErrors implements DomainError {
    NOT_FOUND("Product not found", ErrorCode.NOT_FOUND),
    OUT_OF_STOCK("Product has insufficient stock", ErrorCode.UNPROCESSABLE);

    private final String message;
    private final ErrorCode errorCode;

    ProductErrors(String message, ErrorCode errorCode) {
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
