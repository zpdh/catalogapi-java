package com.zpdh.CatalogApi.domain.user;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
