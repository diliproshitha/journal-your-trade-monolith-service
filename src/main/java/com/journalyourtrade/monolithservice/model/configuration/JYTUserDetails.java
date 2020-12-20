package com.journalyourtrade.monolithservice.model.configuration;

import com.journalyourtrade.monolithservice.model.entity.JYTUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JYTUserDetails implements UserDetails {

    private String email;
    private String password;
    private boolean isActive;
    private List<GrantedAuthority> grantedAuthorities;

    public JYTUserDetails(JYTUser user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isActive = true;
        this.grantedAuthorities = user.getRoles().stream().map(role -> role.getRoleName()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return isActive;
    }
}
