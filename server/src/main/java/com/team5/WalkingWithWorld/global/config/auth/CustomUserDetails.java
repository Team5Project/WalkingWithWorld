package com.team5.WalkingWithWorld.global.config.auth;

import com.team5.WalkingWithWorld.users.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private Users users;
    private CustomAuthorityUtils customAuthorityUtils;

    public CustomUserDetails(Users users){
        this.users = users;
    }
    public CustomUserDetails(Users users, CustomAuthorityUtils customAuthorityUtils) {
        this.users = users;
        this.customAuthorityUtils = customAuthorityUtils;
    }

    public Users getUsers() {
        return users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return customAuthorityUtils.createAuthorities(users.getEmail());
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
