package com.andretavares.testesecurity.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDetailsImpl implements UserDetails {
    
    private String username;
    private String email;
    private String name;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private UserRole role;

    public UserDetailsImpl(String username,
            String email,
            String name,
            String password,
            UserRole role) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(user.getId().toString(),
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // if (StringUtils.hasText(roles)) {
        //     String[] splits = roles.replaceAll(" ", "").split(",");
        //     for (String string : splits) {
        //         authorities.add(new SimpleGrantedAuthority(string));
        //     }
        // }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
