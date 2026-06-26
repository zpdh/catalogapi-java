package com.zpdh.CatalogApi.domain.user.dto;

import com.zpdh.CatalogApi.domain.user.Role;
import com.zpdh.CatalogApi.shared.messaging.payload.UserEventPayload;

public record UserAuthPayload(String email, Role role) implements UserEventPayload {
}
