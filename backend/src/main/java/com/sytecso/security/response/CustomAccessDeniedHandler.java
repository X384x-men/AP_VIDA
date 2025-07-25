package com.sytecso.security.response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sytecso.component.utility.UtileriaFechas;
import com.sytecso.dto.errors.ErrorDetails;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	public static String APP;

    @Value("${context.app_name}")
    public void setAppName(String app_name) {
    	APP = app_name;
    }
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String LOGOUT_URL = "/"+APP+"/login?logout=true";

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			if (accessDeniedException != null) {
				sendErrorResponse(response, accessDeniedException, "Acceso no autorizado",
						HttpServletResponse.SC_MOVED_PERMANENTLY);

			} else {
				response.setStatus(HttpServletResponse.SC_OK);
			}
		} else {
			sendErrorResponse(response, accessDeniedException, "El recurso no esta disponible",
					HttpServletResponse.SC_MOVED_PERMANENTLY);

		}
	}

	private void sendErrorResponse(HttpServletResponse response, AccessDeniedException accessDeniedException,
			String errorMessage, int servletResponse) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json");
		response.setContentType(StandardCharsets.UTF_8.toString());
		response.setHeader("Location", LOGOUT_URL);
		response.setStatus(servletResponse);
		response.getOutputStream()
				.println(mapper.writeValueAsString(new ErrorDetails(UtileriaFechas.generateDate(DATE_FORMAT),
						accessDeniedException.getMessage(), errorMessage)));
	}

}
