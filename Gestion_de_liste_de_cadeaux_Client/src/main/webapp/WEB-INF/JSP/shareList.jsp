<%@page import="java.util.ArrayList"%>
<%@page import="be.project.javabeans.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="handleException.jsp"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Partager ma liste" name="title" />
</jsp:include> 
<body>
	<%@ include file="base.html" %> 
	<div class="d-flex flex-column justify-content-center align-items-center">
		<h1>Partager ma liste</h1>
		<p>Vous pouvez copier le lien pour la partager à d'autre personne non inscrit.</p>
		<div class="d-flex align-items-center">
			
			<input  id="copyLinkInput" class="flex-grow-1" type="text" readonly value="http://localhost:8080/Gestion_de_liste_de_cadeaux_Client/shareList?id=50">
			<button id="copyLinkBtn" class=" d-flex flex-shrink-0  align-items-center" type="button">
			<span class="material-icons ">content_copy</span>
			</button>
		</div>
	</div>
	<% if( request.getAttribute("message") != null){%>
		<div class="alert alert-success alert-dismissible fade show" role="alert">
			<%=request.getAttribute("message") %>
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	<%}if(request.getAttribute("errorShareList") != null){ %>
		<div class="alert alert-warning alert-dismissible fade show" role="alert">
			<%=request.getAttribute("errorShareList") %>
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	<%} %>
	<div class="container d-flex flex-wrap justify-content-center">
		<div class="d-inline-flex p-2 mx-auto bg-secondary bg-opacity-25" >
			<label for="search">
				<img src="./resources/imgs/searchMember32.png">
			</label>
			<input type="text" id="search" placeholder="Rechercher un membre...">
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>Nom</th>
					<th>Prenom</th>
					<th>Adresse email</th>
					<th>Partager</th>
				</tr>
				</thead>
			<tbody id="result">
			
			</tbody>
			
		</table>
		
	</div>
	
</body>
</html>