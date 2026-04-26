package com.zpdh.CatalogApi.domain.user;

import java.util.Arrays;

public enum Role {
    ADMIN,
    CLIENT;

    public String asAuthority() {
        return "ROLE_" + this.name();
    }

    public static String[] getAllAsString() {
        return Arrays.stream(Role.values())
            .map(Enum::name)
            .toArray(String[]::new);
    }
}
