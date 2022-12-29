package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.GiftList;
import be.project.javabeans.User;


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
		HttpSession session = request.getSession(false);
		User user = null;
		if(session != null )
			user = (User)session.getAttribute("connectedUser");
		
		if(occasion != null && occasion.trim().length()>0) {
			ArrayList<GiftList> giftLists = new ArrayList<>();
			GiftList giftList = new GiftList(0,occasion,user,expirationDate, null, "Y");
			user.setGiftList(giftLists);
			int idCreated = user.addGiftList(giftList);
			if(idCreated !=0) {
				giftList.setListId(idCreated);
				session.setAttribute("giftList", giftList);
				request.setAttribute("message", "Liste ajouté!");
				request.getRequestDispatcher("/addGift").forward(request, response);
				return;
			}
			request.setAttribute("errorAddGiftList", "Liste non ajouté!");
			doGet(request, response);
			return;
		}
		request.setAttribute("errorAddGiftList", "L'occasion ne peut pas être vide!");
		doGet(request, response);
		return;
	}

}
