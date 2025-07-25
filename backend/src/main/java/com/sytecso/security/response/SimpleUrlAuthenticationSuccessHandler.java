package com.sytecso.security.response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;



public class SimpleUrlAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler  {
	protected final Log logger = LogFactory.getLog(this.getClass());

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public SimpleUrlAuthenticationSuccessHandler() {
		super();
	}

	// @Override
	// public void onAuthenticationSuccess(final HttpServletRequest request, final
	// HttpServletResponse response, final Authentication authentication) throws
	// IOException {
	// handle(request, response, authentication);
	// clearAuthenticationAttributes(request);
	// }
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		try {
			if ("application/json".equals(request.getHeader("Content-Type"))) {
//				UserAccess user = new UserAccess(authentication.getName(), "USER", new ArrayList<>());
				// /*
				// * USED if you want to AVOID redirect to LoginSuccessful.htm in JSON
				// * authentication
				// */
//				ObjectMapper mapper = new ObjectMapper();
//				SimpleModule module = new SimpleModule();
				// module.addSerializer(JsonView.class, new JsonViewSerializer());
				response.setContentType("application/json");
				response.setContentType(StandardCharsets.UTF_8.toString());
//				response.getWriter().print(mapper.writeValueAsString(user));
				response.setStatus(HttpServletResponse.SC_OK);
//				response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//				response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
//				response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
//				response.setHeader("Access-Control-Allow-Headers", "*");
//				response.setHeader("Access-Control-Allow-Credentials", "true");
//				response.setHeader("Access-Control-Max-Age", "180");
				response.getWriter().flush();
//				clearAuthenticationAttributes(request);
			} else {
				 super.onAuthenticationSuccess(request, response, authentication);

			}

		} catch (Exception e) {
			clearAuthenticationAttributes(request);
		}
	}

	protected void handle(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException {
		final String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(final Authentication authentication) {
		boolean isUser = false;
		boolean isAdmin = false;
		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (final GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
				isUser = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}

		if (isUser) {
			return "/homepage.html";
		} else if (isAdmin) {
			return "/console.html";
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Removes temporary authentication-related data which may have been stored in
	 * the session during the authentication process.
	 */
	
//	protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
//		final HttpSession session = request.getSession(false);
//
//		if (session == null) {
//			return;
//		}
//		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//	}

	public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

}
