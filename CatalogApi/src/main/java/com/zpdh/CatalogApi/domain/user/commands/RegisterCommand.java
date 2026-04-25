package com.zpdh.CatalogApi.domain.user.commands;

import com.zpdh.CatalogApi.domain.user.dto.AuthResponse;
import com.zpdh.CatalogApi.domain.user.dto.RegisterRequest;
import com.zpdh.CatalogApi.shared.mediator.command.Command;
import com.zpdh.CatalogApi.shared.result.Result;

public record RegisterCommand(RegisterRequest request) implements Command<Result<AuthResponse>> {
}
