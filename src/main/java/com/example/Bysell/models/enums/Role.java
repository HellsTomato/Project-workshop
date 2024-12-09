package com.example.Bysell.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority { // Перечисление для ролей пользователей.
    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name(); // Возвращает имя роли в виде строки.
    }
}
