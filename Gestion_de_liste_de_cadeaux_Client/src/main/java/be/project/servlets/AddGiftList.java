package be.project.servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddGiftList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AddGiftList() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/JSP/addGiftList.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String occasion = request.getParameter("occasion");
		String expirationDate = request.getParameter("expirationDate");
		
		if(occasion != null && expirationDate != null) {
			
		}
		doGet(request, response);
	}

}