package com.sytecso.filters;


//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter  {
//	implements Filter
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//			throws IOException, ServletException {
//		final HttpServletResponse response = (HttpServletResponse) res;
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//
//		// without this header jquery.ajax calls returns 401 even after successful login
//		// and SSESSIONID being succesfully stored.
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//
//		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//		response.setHeader("Access-Control-Max-Age", "3600");
//		response.setHeader("Access-Control-Allow-Headers",
//				"X-Requested-With, Authorization, Origin, Content-Type, Version, X-XSRF-TOKEN");
//		response.setHeader("Access-Control-Expose-Headers", "X-Requested-With, Authorization, Origin, Content-Type, X-XSRF-TOKEN");
//
//		final HttpServletRequest request = (HttpServletRequest) req;
//		System.err.println(request.getHeader("origin"));
//		System.err.println(request.getRequestURI());
//		System.err.println(request.getHeader("X-XSRF-TOKEN"));
//		if (!request.getMethod().equals("OPTIONS")) {
//			 response.getWriter().print("OK");
//	            response.getWriter().flush();
////			chain.doFilter(req, res);
//		} else {
//			// do not continue with filter chain for options requests
//		}
//	}

}
