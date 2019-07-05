package com.hzq.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserToken extends AbstractAuthenticationToken {

    private UserDetails principal;
    private DecodedJWT token;

    public UserToken(DecodedJWT token) {
        super(Collections.<GrantedAuthority>emptyList());
        this.token=token;
    }

    public UserToken(UserDetails principal, DecodedJWT token,
                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
    }
    @Override
    public void setDetails(Object details) {
        super.setDetails(details);
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public DecodedJWT getToken() {
        return token;
    }
}
