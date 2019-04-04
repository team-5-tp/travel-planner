package rpc;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthorizationFilter implements javax.servlet.Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		try {
			if (JwtToken.verifyToken(httpRequest)) {
				filterChain.doFilter(request, response);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		httpResponse.setStatus(401);
	}

}
