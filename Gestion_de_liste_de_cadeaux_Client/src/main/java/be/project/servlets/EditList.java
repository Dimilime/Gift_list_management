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
					//recup la liste des users pour le partage
					ArrayList<User> allUsers = User.getAll();
					request.setAttribute("giftList", currentGiftList);
					request.setAttribute("allUsers", allUsers);
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
		//gère les modifs
		int listId=0;
		boolean enabled = false;
		User connectedUser = null;
		HttpSession session = request.getSession(false);
		try {
			if(session != null ) {
				listId= (int)session.getAttribute("listId");
				connectedUser = (User)session.getAttribute("connectedUser");
			}
				
			String servletPath = request.getServletPath().substring(1);
			if(servletPath.equals("editList") && listId!=0 ) {
				//recup params obligatoire
				if(request.getParameter("occasion")!=null && request.getParameter("expirationDate") !=null) {
					String occasion = request.getParameter("occasion");
					String expirationDate = request.getParameter("expirationDate");
				
					//recup les id des participants
					ArrayList<User> sharedUsers = new ArrayList<User>();
					if(request.getParameterValues("participants") != null) {
						String[] values = request.getParameterValues("participants");
						int[] usersId = new int[values.length];
						for(int i=0;i<values.length;i++) {
							usersId[i] = Integer.valueOf(values[i]) ;
							User user = new User();
							user.setUserId(usersId[i]);
							sharedUsers.add(user);
						}
					}
					//recup checkbox liste active ou non
					if(request.getParameter("enabled")!=null && request.getParameter("enabled").equals("on")) {
						enabled = true;
					}
					GiftList giftList = new GiftList(listId,occasion,expirationDate,null, sharedUsers, connectedUser, null, Utils.convertBoolToString(enabled));
					if(giftList.update()) {
						System.out.println("update ok");
						request.setAttribute("message", "La liste a bien été modifiée");
						//rajouter l'id dans queryString?
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
