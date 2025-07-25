package com.sytecso.security.response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sytecso.component.utility.UtileriaFechas;
import com.sytecso.dto.errors.ErrorDetails;
import com.sytecso.component.exceptions.SytecsoController;

public class CustomHttp403ForbiddenEntryPoint implements AuthenticationEntryPoint {
	public static String APP;

    @Value("${context.app_name}")
    public void setAppName(String app_name) {
    	APP = app_name;
    }
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String LOGOUT_URL = "/"+APP+"/login?logout=true";

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			SytecsoController.logClassAndMethodWithException(authException);
			response.setHeader("Location", LOGOUT_URL);
			ObjectMapper mapper = new ObjectMapper();
			response.setContentType("application/json");
			response.setContentType(StandardCharsets.UTF_8.toString());
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.getOutputStream()
					.println(mapper.writeValueAsString(new ErrorDetails(UtileriaFechas.generateDate(DATE_FORMAT),
							authException.getMessage(), "No se ha iniciado sesion")));

		}

	}

}