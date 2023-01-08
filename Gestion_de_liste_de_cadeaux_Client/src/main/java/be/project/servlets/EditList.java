package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.GiftList;
import be.project.javabeans.User;
import be.project.utils.Utils;


public class EditList extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = null;
		GiftList currentGiftList=null;
		int listId = 0;
		boolean rightAccess= false;
		try {
			if(session != null )
				user = (User)session.getAttribute("connectedUser");
			//check si bien accès à la liste récup dans la query string
			ArrayList<GiftList> giftList = user.getGiftLists();
			if(request.getParameter("id") != null) {
				listId = Integer.valueOf(request.getParameter("id"));
				for(GiftList list : giftList) {
					if(list.getListId() == listId) {
						rightAccess=true;
						currentGiftList= list;
						//ajout listid dans session -> suppression au niveau consultList
						session.setAttribute("listId", listId);
					}	
				}
				if(rightAccess) {
					request.setAttribute("giftList", currentGiftList);
					request.getRequestDispatcher("/WEB-INF/JSP/editList.jsp").forward(request, response);
					return;
				}
			}
			
		}catch(Exception ex) {
			System.out.println("Exception dans doGet de editList Servlet : " + ex.getMessage());
			response.sendRedirect("connexion");
			return;
		}
		
		response.sendRedirect("home");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//modification d'une liste form envoyé
		int listId=0;
		boolean enabled = false;
		User connectedUser = null;
		HttpSession session = request.getSession(false);
		try {
			if(session != null) {
				listId= (int)session.getAttribute("listId");
				connectedUser = (User)session.getAttribute("connectedUser");
			}
				
			if(listId!=0) {
				if(request.getParameter("occasion")!=null) {
					String occasion = request.getParameter("occasion");
					String expirationDate = request.getParameter("expirationDate");
				
					//recup checkbox liste active ou non
					if(request.getParameter("enabled")!=null && request.getParameter("enabled").equals("on")) {
						enabled = true;
					}
					GiftList giftList = new GiftList(listId,occasion,expirationDate,null, null, connectedUser, null, Utils.convertBoolToString(enabled));
	
					if(giftList.update()) {
						request.setAttribute("message", "La liste a bien été modifiée");
						request.setAttribute("refreshList", "yes");
						request.getRequestDispatcher("consultList?id="+listId).forward(request, response);
						return;
					}
				}
			}
		}catch(Exception ex) {
			System.out.println("Exception dans EditList doPost : " + ex.getMessage());
			response.sendRedirect("connexion");
			return;
		}
		response.sendRedirect("home");
	}

}
