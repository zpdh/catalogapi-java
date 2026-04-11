package com.zpdh.CatalogApi.shared.result;

/**
 * Representa o resultado de uma operação, pode ser successo ou falha.
 * Inspirado no tipo Result do Rust, para fluxo de controle como uma alternativa à exceptions.
 */
public sealed interface Result<T> permits Result.Success, Result.Failure {
    record Success<T>(T value) implements Result<T> {
    }

    record Failure<T>(String errorMessage, ErrorCode errorCode) implements Result<T> {
    }

    static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    static <T> Result<T> failure(String errorMessage, ErrorCode errorCode) {
        return new Failure<>(errorMessage, errorCode);
    }

    default T getOrThrow() {
        return switch (this) {
            case Success<T> success -> success.value();
            case Failure<T> failure -> throw new ApiException(failure.errorCode().getHttpStatus(), failure.errorMessage());
        };
    }

    default boolean isSuccess() {
        return this instanceof Result.Success<?>;
    }
}
