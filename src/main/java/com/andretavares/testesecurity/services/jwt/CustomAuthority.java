package com.andretavares.testesecurity.services.jwt;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class CustomAuthority implements GrantedAuthority {
    private String authority;

    public CustomAuthority(String authority) {
        this.authority = authority;
    }
}
