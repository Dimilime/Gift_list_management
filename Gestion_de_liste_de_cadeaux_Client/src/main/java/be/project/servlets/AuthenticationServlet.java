package be.project.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import be.project.javabeans.User;
import be.project.utils.Utils;

public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;     
   
    public AuthenticationServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException{
    	super.init();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		//si session existante renvoi vers page d'accueil
		if(session!=null && !session.isNew()) {
			User user = (User)session.getAttribute("connectedUser");
			if(user !=null) {
				response.sendRedirect("home");
				return;
			}
		}
		request.getRequestDispatcher("/WEB-INF/JSP/authentication.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Si vient de la création de compte
		if(request.getAttribute("message")!=null) {
			doGet(request,response);
			return;
		}
		request.setAttribute("error", null);
		String errors,email,password;
		errors=email=password="";
		
		//check paramètres existants venant du form
		if(request.getParameter("email")!=null && request.getParameter("password")!=null ) {
			email=request.getParameter("email");
			password=request.getParameter("password");
		}
		//check contiennent tous une valeur
		if(!Utils.allFieldsAreFilled(email, password)){
			errors="Tous les champs doivent être remplis!";
		}
		//check email valide
		else if(!Utils.emailIsValid(email)){
			errors="Entrez une email valide";
		}else {
			//si ok on check si identifiant et mdp correspondante à un compte
			try {
				boolean loginSuccess=User.login(email, password);
				if(loginSuccess) {
					User connectedUser=User.getUser(email);
					if(connectedUser!=null) {
						HttpSession session=request.getSession(true);
						if(!session.isNew()) {
							session.invalidate();
							session=request.getSession();
						}
						//On met le user dans la session puis on renvoie vers la page d'accueil
						session.setAttribute("connectedUser", connectedUser);
						response.sendRedirect("home");
						return;
					}
				}else {
					//Pas de compte correspondant
					errors+="Identifiant ou mot de passe incorrect";
				}
				
			}catch(Exception e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
				return;
			}
		}
		request.setAttribute("error", errors);
		doGet(request,response);
	}
}
