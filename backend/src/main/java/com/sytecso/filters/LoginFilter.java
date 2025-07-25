package com.sytecso.filters;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;

import com.sytecso.config.logger.SytecsoLogger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Component

public class LoginFilter implements Filter {

	private static final String LOGIN_PAGE = "/login";
	private static final String MAIN_PAGE = "/";

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (verifyLoginUrl(request)) {
			HttpSession session = request.getSession(false);
			if (session == null) {
				clearCache(response);
				filterChain.doFilter(request, response);
				return;
			}
			SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
			if (securityContext != null) {
				response.sendRedirect(request.getContextPath() + MAIN_PAGE);
			} else {
				clearCache(response);
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	private void clearCache(HttpServletResponse response) {
		response.setDateHeader("Expires", 0);
		response.setHeader("Last-Modified", new Date().toString());
		response.setHeader("Cache-Control",
				"public,no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("X-Content-Type-Options", "nosniff");
		response.setHeader("X-Frame-Options", "DENY");
	}

	private boolean verifyLoginUrl(ServletRequest servletRequest) {
		String url = ((HttpServletRequest) servletRequest).getRequestURL().toString();
		if (url.contains(LOGIN_PAGE) || url.contains("/createEmpleadoAP")||url.contains("/getDependencias")) {
			SytecsoLogger.info("Interceptando acceso a pagina de login");
			return true;
		}
		return false;
	}

}
