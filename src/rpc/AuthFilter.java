package rpc;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthFilter implements javax.servlet.Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
    	System.out.println("AuthFilter");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		try {
			if (JwtToken.verifyToken(httpRequest)) {
				filterChain.doFilter(request, response);
			} else {
				httpResponse.setStatus(401);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpResponse.setStatus(401);
		}
	}
}
