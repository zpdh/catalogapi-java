package com.zpdh.CatalogApi.domain.user.queries;

import com.zpdh.CatalogApi.domain.user.dto.AuthRequest;
import com.zpdh.CatalogApi.domain.user.dto.AuthResponse;
import com.zpdh.CatalogApi.shared.mediator.query.Query;
import com.zpdh.CatalogApi.shared.result.Result;

public record LoginQuery(AuthRequest request) implements Query<Result<AuthResponse>> {
}
