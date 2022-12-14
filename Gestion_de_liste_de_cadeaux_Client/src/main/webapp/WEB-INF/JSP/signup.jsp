<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="handleException.jsp"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Page d'inscription" name="title"/>
</jsp:include>
<body>
	<div class="authentication-container">
        <div class="image-container"></div>
      
        <div class="signup-content">
            <div class="signup-message">
                <h1>Inscription</h1>
                <div class="center">
                    <a href="./connexion">Déjà enregistré? Connectez-vous ici.</a>
                </div>  
            </div>

            <div class="line"></div>
            
            <div class="signup-form">
			<% if(request.getAttribute("error")!=null){%>
				<div class="alert alert-danger" role="alert">
	  				<%= request.getAttribute("error") %>
				</div>
			<% } %>
				<form action="signup" method="POST">
				  <div class="mb-3">
				    <label for="inputEmail" class="form-label">Email</label>
				    <input type="text" class="form-control" id="inputEmail" name="email" placeholder="Entrez votre email" required>
					<div id="serialNumber" class="form-text" class="invalid-feedback">Exemple : example@hotmail.com</div>		 
				  </div>
				     <div class="mb-3">
				    <label for="inputfirstname" class="form-label">Prénom</label>
				    <input type="text" class="form-control" id="inputfirstname" name="firstname" placeholder="Entrez prénom"  required>	 
				  </div>
				   <div class="mb-3">
				    <label for="inputLastname" class="form-label">Nom</label>
				    <input type="text" class="form-control" id="inputLastname" name="lastname" placeholder="Entrez votre nom" required>
				  </div>
				  <div class="mb-3">
				    <label for="inputPassword" class="form-label">Mot de passe</label>
				    <input type="password" class="form-control" id="inputPassword" name="password" placeholder="**********" required>
				  </div>
				<% if(request.getParameter("key")!=null){%>
					<input type="hidden" class="form-control" id="key" name="key" value="<%=request.getParameter("key") %>">
				<% } %>
				  <div class="center">
		          	<button type="submit" class="button" >S'inscrire</button>
		          </div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>