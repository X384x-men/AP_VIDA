package com.sytecso.security.response;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sytecso.component.utility.UtileriaFechas;
import com.sytecso.dto.errors.ErrorDetails;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		ObjectMapper mapper = new ObjectMapper();
		response.getOutputStream()
				.println(mapper.writeValueAsString(new ErrorDetails(UtileriaFechas.generateDate(DATE_FORMAT),
						"Bad credentials", exception.getMessage())));

	}
}
