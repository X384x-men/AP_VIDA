package com.sytecso.security.provider;


import java.nio.charset.StandardCharsets;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import com.sytecso.security.service.CustomUserDetailsService;

public class CustomUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	private static final String USER_NOT_FOUND= "userNotFoundPassword";

	private PasswordEncoder passwordEncoder;
	private String userNotFoundEncodedPassword;
	private CustomUserDetailsService userDetailsService;
	

	public CustomUserDetailsAuthenticationProvider(PasswordEncoder passwordEncoder,
			CustomUserDetailsService userDetailsService) {
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException(
					messages.getMessage("No se proporcionaron credenciales", "Usuario o password incorrectos"));
		}
		String presentedPassword = authentication.getCredentials().toString();
		if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			logger.debug("Authentication failed: password does not match stored value");
			throw new BadCredentialsException(
					messages.getMessage("La contraseña no coincide con el valor almacenado", "Usuario o password incorrectos"));
		}	
		if(!userDetailsService.loadUserByUsername(userDetails.getUsername()).isEnabled()) {
			logger.debug("Authentication failed: user is inactive");
			String message = "El usuario se encuentra inactivo, para proporcionarle informaci&oacute;n por favor comuniquese con nosotros a trav&eacute;s del correo electr&oacute;nico modulo.urawa@spsegurospatrimonial.mx";
			byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
			String messageUTF = new String(bytes, StandardCharsets.UTF_8);
			throw new BadCredentialsException(
				messages.getMessage(messageUTF, messageUTF));
		}
	}

	@Override
	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
		this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND);
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		CustomAuthenticationToken auth = (CustomAuthenticationToken) authentication;
		UserDetails loadedUser;

		try {
			loadedUser = this.userDetailsService.loadUserByUsernameAndPassword(auth.getPrincipal().toString(),
					auth.getCredentials().toString());
		} catch (UsernameNotFoundException notFound) {
			if (authentication.getCredentials() != null) {
				String presentedPassword = authentication.getCredentials().toString();
				passwordEncoder.matches(presentedPassword, userNotFoundEncodedPassword);
			}
			throw notFound;
		} catch (Exception repositoryProblem) {
			throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new InternalAuthenticationServiceException(
					"UserDetailsService returned null, " + "which is an interface contract violation");
		}
		return loadedUser;
	}
}
