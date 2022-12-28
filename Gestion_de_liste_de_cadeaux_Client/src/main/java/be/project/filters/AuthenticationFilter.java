package be.project.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.User;


public class AuthenticationFilter extends HttpFilter implements Filter {
       
	private static final long serialVersionUID = -6039327106498454462L;

    public AuthenticationFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		String requestUrl = ((HttpServletRequest)request).getRequestURI();
		
		System.out.println(requestUrl);
		if(session!=null) {
			User u = (User)session.getAttribute("connectedUser");
			if(u !=null) {
				chain.doFilter(request, response);
			}
			else {
				((HttpServletResponse)response).sendRedirect("login");
			}
		}else 
			((HttpServletResponse)response).sendRedirect("login");
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
