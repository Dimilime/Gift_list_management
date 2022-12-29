package be.project.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.project.javabeans.User;
import be.project.utils.Utils;

public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignUpServlet() {
        super();
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check si arrive ici après un lien --> String servletPath = request.getServletPath().substring(1); on recup la clé
		//on met la clé dans les attributs de la requete + fait passer dans le form en input hidden
		request.getRequestDispatcher("/WEB-INF/JSP/signup.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String message, email, firstname, lastname, password,key;
		message=email=firstname=lastname=password=key=null;
		request.setAttribute("error", null);
		String errors="";
		if(request.getParameter("email")!=null && request.getParameter("firstname")!=null && request.getParameter("lastname")!=null &&
				request.getParameter("password")!=null ) {
			email=request.getParameter("email");
			firstname=request.getParameter("firstname");
			lastname=request.getParameter("lastname");
			password=request.getParameter("password");
		}
		if(request.getParameter("key")!=null) {
			key = request.getParameter("key");
		}
		
		if(!Utils.allFieldsAreFilled(email,firstname, lastname, password)){
			errors="Tous les champs doivent être remplis!";
		}
		else if(!Utils.emailIsValid(email)){
			errors="Entrez une email valide";
		}
		else if(!Utils.pwdValid(password)){
			errors="Le mot de passe doit faire une taille mini de 5 et max 50";
		}
		else {
			try {
				User user = new User(firstname, lastname,email, password);
				boolean success= false;
				if(key!=null) {
					success= user.createUser(true);
				}else {
					success= user.createUser();
				}
				if(success) {
					message="Compte créé avec succès!";
					request.setAttribute("message", message);
					//check si s'inscrit après un lien direct le co et go liste details 
					if(key!=null) {
						request.setAttribute("key", key);
						request.getRequestDispatcher("sharedlistdetails").forward(request, response);
						return;
					}
					//sinon go ecran connexion
					else {
						request.getRequestDispatcher("connexion").forward(request, response);
						return;
					}
				}
				else {
					errors="Cette email est déjà utilisée";
				}	
				
			}catch(Exception e) {
				request.setAttribute("error", e.getMessage());
				request.getRequestDispatcher("/WEB-INF/JSP/error.jsp").forward(request,response);
				return;
			}
		}
		if(!errors.equals("")) {
			request.setAttribute("error", errors);
		}
		doGet(request,response);
	}
}
