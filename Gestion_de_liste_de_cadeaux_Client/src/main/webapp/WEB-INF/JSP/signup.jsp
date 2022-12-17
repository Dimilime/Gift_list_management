<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Page d'inscription</title>
</head>
<body>
	<div class="container" >
		<div class="row justify-content-center">
		    <div class="text-center">
		      <h2>Inscription</h2>
		    </div>
		 </div><hr>
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
		    <label for="inputfirstname" class="form-label">Pr�nom</label>
		    <input type="text" class="form-control" id="inputfirstname" name="firstname" placeholder="Entrez pr�nom"  required>	 
		  </div>
		   <div class="mb-3">
		    <label for="inputLastname" class="form-label">Nom</label>
		    <input type="text" class="form-control" id="inputLastname" name="lastname" placeholder="Entrez votre nom" required>
		  </div>
		  <div class="mb-3">
		    <label for="inputPassword" class="form-label">Mot de passe</label>
		    <input type="password" class="form-control" id="inputPassword" name="password" placeholder="**********" required>
		  </div>
		<% if(request.getAttribute("key")!=null){%>
			<input type="hidden" class="form-control" id="key" name="key" value="<%=request.getAttribute("key") %>">
		<% } %>
		  <div class="text-center">
		  	<button type="submit" class="btn btn-primary" >S'inscrire</button>
		  </div>
		    <div class="text-center">
		  	<a href="<%=str%>/connexion" class="btn btn-secondary">Se connecter</a>
		  </div>
		</form>
	</div>
</body>
</html>