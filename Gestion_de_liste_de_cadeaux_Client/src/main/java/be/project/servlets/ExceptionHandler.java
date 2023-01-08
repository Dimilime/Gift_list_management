package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }
    private void processError(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        //customize error message
        Throwable throwable = (Throwable) request
                .getAttribute("javax.servlet.error.exception");
        String servletName = (String) request
                .getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Inconnu";
        }
        String requestUri = (String) request
                .getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Inconnu";
        }    
        request.setAttribute("exception", "La Servlet " + servletName + 
          " a rencontré une exception " + throwable.getClass().getName() +
          " : " + throwable.getMessage());    

		request.getRequestDispatcher("/WEB-INF/JSP/handleException.jsp").forward(request, response);

    }

}
