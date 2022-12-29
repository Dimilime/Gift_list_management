<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="handleException.jsp"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Ajouter un cadeau" name="title" />
</jsp:include>
<body>
	<%@ include file="base.html"%>
	<div class="d-flex justify-content-center"><h1>Ajouter un cadeau </h1></div>
	<% if( request.getAttribute("message") != null){%>
		<div class="alert alert-success alert-dismissible fade show" role="alert">
			<%=request.getAttribute("message") %>
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	<%}if(request.getAttribute("errorGift") != null){ %>
		<div class="alert alert-warning alert-dismissible fade show" role="alert">
			<%=request.getAttribute("errorGift") %>
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	<%} %>
	<div id="addGift" class="d-flex justify-content-center" >
		
		<form action="addGift" method="post" enctype="multipart/form-data">
			<div class="mb-3">
				<label for="giftName" class="form-label">Nom</label> 
				<input id="giftName" type="text" class="form-control" name="giftName" required>
			</div>
			<div class="mb-3">
				<label for="description" class="form-label">Description</label> 
				<input id="description" type="text" class="form-control" name="description">
			</div>
			<div class="mb-3">
				<label for="averagePrice" class="form-label">Prix moyen</label> 
				<input id="averagePrice" type="number" class="form-control" name="averagePrice" required min=1>
			</div>
			<div class="mb-3">
				<label for="priorityLevel" class="form-label">Priorité</label> 
				<select id="priorityLevel" class="form-select" name="priorityLevel" aria-described="selectHelp">
					<option selected value="1">1</option>
					<%
					for (int i = 2; i <= 10; i++) {
					%>
					<option value="<%=i%>"><%=i%></option>
					<%
					}
					%>

				</select>
				<div id="selectHelp" class="form-text">Priorité des envies, 10 est une envie très élevée</div>
			</div>
			<div class="mb-3">
				<label for="image" class="form-label">Inserer une image du cadeau</label> 
				<input type="file" id="image" name="giftImg" accept="image/png, image/jpeg, image/jpg" aria-described="imageHelp">
				<div id="imageHelp" class="form-text">Facultatif! Si vous avez une image vous pouvez l'inclure</div>
			</div>
			<div class="mb-3">
				<label for="link" class="form-label">Lien du cadeau</label> 
				<input id="link" type="text" class="form-control" name="link" aria-described="linkHelp">
				<div id="linkHelp" class="form-text">Facultatif! Si vous avez un lien marchand pour le cadeau vous pouvez l'inclure (max 500 caractères)</div>
			</div>
			<div class="mb-3">
				<label for="addAnother" class="form-label">Souhaitez-vous ajouter un autre cadeau ensuite ?</label> 
				<input id="addAnother" type="checkbox" class="form-check-input" name="addAnother" aria-described="addAnotherHelp">
				<div id="addAnotherHelp" class="form-text">Cochez la case pour ajouter un autre cadeau après celui ci</div>
			</div>
			<div class="d-grid gap-2 col-6 mx-auto">
				<button type="submit" class="btn btn-primary btn-lg">Ajouter</button>
			</div>
			
		</form>
	</div>
	
</body>
</html>