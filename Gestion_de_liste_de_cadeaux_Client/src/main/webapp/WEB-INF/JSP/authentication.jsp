<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="handleException.jsp"%>
    
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Page de connexion" name="title"/>
</jsp:include>
<body>
   	<div class="container" >
		<div class="row justify-content-center">
		    <div class="text-center">
		      <h2>Connexion</h2>
		    </div>
		 </div><hr>
		<% if(request.getAttribute("error")!=null){%>
			<div class="alert alert-danger" role="alert">
  				<%= request.getAttribute("error") %>
			</div>
		<% } %>
		<% if(request.getAttribute("message")!=null){%>
			<div class="alert alert-success" role="alert">
  				<%= request.getAttribute("message") %>
			</div>
		<% } %>
		<form action="authentication" method="POST">
		  <div class="mb-3">
		    <label for="inputEmail" class="form-label">Email</label>
		    <input type="text" class="form-control" id="inputEmail" name="email" placeholder="Entrez votre email" required>
			<div id="serialNumber" class="form-text" class="invalid-feedback">Exemple : example@hotmail.com</div>		 
		  </div>
		  <div class="mb-3">
		    <label for="inputPassword" class="form-label">Mot de passe</label>
		    <input type="password" class="form-control" id="inputPassword" name="password" placeholder="**********" required>
		  </div>
		  <div class="text-center">
		  	<button type="submit" class="btn btn-primary" >Se connecter</button>
		  </div>
		    <div class="text-center">
		  	<a href="./signup" class="btn btn-secondary">S'inscrire</a>
		  </div>
		</form>
	</div>
</body>
</html>