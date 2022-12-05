package com.timkin.models.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Table;

@Table(name = "roles")
public enum Role implements GrantedAuthority {
    Administrator, User;

    @Override
    public String getAuthority() {
        return name();
    }
}