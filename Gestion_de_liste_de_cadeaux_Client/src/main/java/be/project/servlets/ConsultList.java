package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.Gift;
import be.project.javabeans.GiftList;
import be.project.javabeans.User;


public class ConsultList extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = null;
		GiftList currentGiftList=null;
		int listId = 0;
		boolean rightAccess= false;
		try {
			if(session != null ) {
				user = (User)session.getAttribute("connectedUser");
				if(session.getAttribute("listId") != null) 
					session.removeAttribute("listId");
				if(session.getAttribute("giftId") != null) 
					session.removeAttribute("giftId");
			}
			ArrayList<GiftList> giftList = null;
			//si vient après modif on refresh la liste
			if(request.getAttribute("refreshList")!=null) {
				giftList = user.findAllGiftList();
			}else {
				giftList = user.getGiftLists();
			}
			
			//check si bien accès à la liste récup dans la query string
			if(request.getParameter("id") != null) {
				listId = Integer.valueOf(request.getParameter("id"));
				for(GiftList list : giftList) {
					if(list.getListId() == listId) {
						rightAccess=true;
						currentGiftList= list;
					}	
				}
				if(rightAccess) {
					ArrayList<Gift> gifts = currentGiftList.getGifts();
					//trier par ordre de priorité
					Collections.sort(gifts, Comparator.comparing(Gift::getPriorityLevel));
					Collections.reverse(gifts);
					currentGiftList.setGifts(gifts);
					session.setAttribute("giftList", currentGiftList);
					request.setAttribute("giftList", currentGiftList);
					request.getRequestDispatcher("/WEB-INF/JSP/consultList.jsp").forward(request, response);
					return;
				}
			}
			
		}catch(Exception ex) {
			System.out.println("Exception dans doGet de consultList Servlet : " + ex.getMessage());
			response.sendRedirect("connexion");
			return;
		}
		
		response.sendRedirect("home");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
