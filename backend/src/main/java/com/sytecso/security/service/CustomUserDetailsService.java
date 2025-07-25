package com.sytecso.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService {
    UserDetails loadUserByUsernameAndPassword(String username, String password) throws UsernameNotFoundException;

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
