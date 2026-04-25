package com.zpdh.CatalogApi.domain.user.dto;

import com.zpdh.CatalogApi.domain.user.Role;

public record AuthResponse(String token, String email, Role role) {
}
