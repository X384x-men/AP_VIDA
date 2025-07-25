package com.sytecso.security.provider;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = -6155624919928344842L;

    public CustomAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
        super.setAuthenticated(false);
    }

    public CustomAuthenticationToken(Object principal, Object credentials, 
        Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        super.setAuthenticated(true);
    }

        
}