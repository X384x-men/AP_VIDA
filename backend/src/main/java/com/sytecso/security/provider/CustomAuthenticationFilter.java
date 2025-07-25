package com.sytecso.security.provider;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sytecso.dto.usuarioacceso.UserAccess;
import com.sytecso.component.exceptions.SytecsoController;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public CustomAuthenticationFilter() {
		super();
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		CustomAuthenticationToken authRequest = getAuthRequest(request);
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		if ("application/json".equals(request.getHeader("Content-Type"))) {
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authResult);
			SecurityContextHolder.setContext(context);
			ObjectMapper mapper = new ObjectMapper();
			UserAccess user = new UserAccess(authResult.getName(), "", true, true, true, true,
					authResult.getAuthorities());
			user.setToken("123123");
			response.setContentType("application/json");
			response.setContentType(StandardCharsets.UTF_8.toString());
			response.getWriter().print(mapper.writeValueAsString(user));
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().flush();
		}
	}

	private CustomAuthenticationToken getAuthRequest(HttpServletRequest request) {
		InputStream body;
		try {
			body = request.getInputStream();
			UserAccess user = new ObjectMapper().readValue(body, UserAccess.class);
			return new CustomAuthenticationToken(user.getUsername(), user.getPassword());
		} catch (IOException e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	@Override
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

}
