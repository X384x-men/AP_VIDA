package com.sytecso.filters;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomCsrfFilter extends OncePerRequestFilter {

	public static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//		response.setHeader("Access-Control-Max-Age", "3600");
//		response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, X-Requested-Wit, X-XSRF-TOKEN");
//		response.addHeader("Access-Control-Expose-Headers", "X-XSRF-TOKEN");
		CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

		if (csrf != null) {

			Cookie cookie = WebUtils.getCookie(request, CSRF_COOKIE_NAME);
			String token = csrf.getToken();

			if (cookie == null || token != null && !token.equals(cookie.getValue())) {
				cookie = new Cookie(CSRF_COOKIE_NAME, token);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			filterChain.doFilter(request, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}
}