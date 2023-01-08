<%@page import="be.project.javabeans.GiftList"%>
<%@page import="be.project.javabeans.Gift"%>
<%@page import="be.project.javabeans.Participation"%>
<%@page import="be.project.javabeans.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="handleException.jsp"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Modification d'une liste" name="title" />
</jsp:include> 
<body>
	<% 	Gift gift = (Gift)request.getAttribute("gift");
	%>
	<%@ include file="base.jsp" %> 
	<div class="d-flex justify-content-center"><h1>Modification d'un cadeau</h1></div>

	<div class="modify-form">
			<% if(request.getAttribute("error")!=null){%>
				<div class="alert alert-danger" role="alert">
	  				<%= request.getAttribute("error") %>
				</div>
			<% } %>
				<form action="editGift" method="POST">
				<div class="mb-3">
					<label for="giftNameInput" class="form-label">Nom</label> 
					<input id="giftNameInput" type="text" class="form-control" name="giftName" value="<%=gift.getName()%>" >
				</div>
				
				<div class="mb-3">
					<label for="descriptionInput" class="form-label">Description</label> 
					<input id="descriptionInput" type="text" class="form-control" name="description" value="<%=gift.getDescription()%>">
				</div>
				
				<div class="mb-3">
					<label for="averagePriceInput" class="form-label">Prix moyen</label> 
					<input id="averagePriceInput" type="number" class="form-control" name="averagePrice" min=1 value="<%=gift.getAveragePrice()%>">
				</div>
				
				<div class="mb-3">
					<label for="priorityLevelSelect" class="form-label">Priorité</label> 
					<select id="priorityLevelSelect" class="form-select" name="priorityLevel" >
						<%
						for (int i = 1; i <= 10; i++) {
							if(i == gift.getPriorityLevel()){%>
								<option selected value="<%=i%>"><%=i%></option>
							<%}else{%>
							<option value="<%=i%>"><%=i%></option>
							<%}
							}%>
					</select>
				</div>
				
				<div class="mb-3">
					<label for="linkInput" class="form-label">Lien du cadeau</label>
					<%if(gift.getLink() == null){ %> 
					<input id="linkInput" type="text" class="form-control" name="link">
					<%}else{ %>
					<input id="linkInput" type="text" class="form-control" name="link" value="<%=gift.getLink() %>">
					<%} %>
				</div>
				
				<div class="d-grid gap-2 col-6 mx-auto">
					<button type="submit" class="btn-red btn-confirm">Modifier</button>
					<a class="btn-red" href="./home">Annuler</a>
				</div>
				
				</form>
				
			</div>
			
			
		
		

			
			
			
			
			
</body>
</html>