package com.sytecso.security.provider;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;

import com.sytecso.component.utility.UtileriaAcceso;
import com.sytecso.service.rolAcceso.ServiceRolAcceso;

@Configuration
public class CustomAccessDecisionVoter extends UtileriaAcceso implements AccessDecisionVoter<Object> {
	private static final String ANONYMUS_USER = "ANONYMOUS";
	private static final String LOGIN_PAGE = "/login";
	private static final String LOGOUT_URL = "/logout";
	private static final String WS_PAGE = "/ws";

	@Autowired
	private ServiceRolAcceso serviceRolAcceso;

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		FilterInvocation filter = (FilterInvocation) object;
		if (filter.getRequestUrl().contains(LOGIN_PAGE) || filter.getRequestUrl().contains(LOGOUT_URL)
				|| filter.getRequestUrl().equals("/")) {
			SecurityContextHolder.clearContext();
			return ACCESS_GRANTED;
		}
		if (authentication == null)
			return ACCESS_DENIED;
		if (authentication.getAuthorities() == null)
			return ACCESS_DENIED;
		if (authentication.getAuthorities().isEmpty())
			return ACCESS_DENIED;
		if (this.isAuthoritiePresent(authentication.getAuthorities(), ANONYMUS_USER)
				&& (filter.getRequestUrl().contains(LOGIN_PAGE) || filter.getRequestUrl().contains(WS_PAGE)))
			return ACCESS_GRANTED;
		boolean access = this.serviceRolAcceso.userHasAcceso(authentication.getAuthorities(), filter);
		if (access)
			return ACCESS_GRANTED;
		return ACCESS_DENIED;
	}

}
