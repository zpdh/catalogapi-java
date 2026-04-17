package com.zpdh.CatalogApi.shared.error;

public interface DomainError {
    String getMessage();

    ErrorCode getErrorCode();
}

