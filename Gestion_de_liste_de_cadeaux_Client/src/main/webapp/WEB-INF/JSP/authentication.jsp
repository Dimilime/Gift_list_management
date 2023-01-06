<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="handleException.jsp"%>
    
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Page de connexion" name="title"/>
</jsp:include>
<body>
	<div class="authentication-container">
        <div class="image-container"></div>
      
        <div class="login-content">
            <div class="login-message">
                <h1>Connexion</h1>
                <div class="center">
                    <a href="./signup">Pas de compte ? Incrivez-vous ici.</a>
                </div>
  
            </div>
            <div class="line"></div>
            
            <div class="login-form">
			<% if(request.getAttribute("error")!=null){%>
				<div class="alert alert-danger" role="alert">
	  				<%= request.getAttribute("error") %>
				</div>
			<% } %>
			<% if(request.getAttribute("message")!=null){%>
				<div class="alert alert-success alert-dismissible fade show" role="alert">
	  				<%= request.getAttribute("message") %>
	  				<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
			<% } %>
				<form action="authentication" method="POST">
				  <div class="mb-3">
				    <label for="inputEmail" class="form-label">Email</label>
				    <input type="text" class="form-control" id="inputEmail" name="email" placeholder="Entrez votre email" required>
					<div id="inputEmail" class="form-text" class="invalid-feedback">Exemple : example@hotmail.com</div>		 
				  </div>
				  <div class="mb-3">
				    <label for="inputPassword" class="form-label">Mot de passe</label>
				    <input type="password" class="form-control" id="inputPassword" name="password" placeholder="**********" required>
				  </div>
				  <div class="center">
		          	<button type="submit" class="button" >Se connecter</button>
		          </div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>