package com.sytecso.security.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sytecso.dto.usuarioacceso.UserAccess;
import com.sytecso.security.repository.UserRepository;

@Service("userDetailsService")
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService{

	private UserRepository userRepository;

	public CustomUserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsernameAndPassword(String username, String password) throws UsernameNotFoundException {
		if (StringUtils.isAnyBlank(username, password)) {
			throw new UsernameNotFoundException("Username and domain must be provided");
		}
		UserAccess user = userRepository.findUser(username, password);
		if (user == null) {
			throw new UsernameNotFoundException(
					String.format("Username not found for domain, username=%s, password=%s", username, password));
		}
		return user;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringUtils.isAnyBlank(username)) {
			throw new UsernameNotFoundException("Username and domain must be provided");
		}
		UserAccess user = userRepository.findUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException(
					String.format("Username not found for domain, username=%s ", username));
		}
		return user;
	}
}
