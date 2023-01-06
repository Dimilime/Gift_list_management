<%@page import="be.project.javabeans.GiftList"%>
<%@page import="be.project.javabeans.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="handleException.jsp"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Mes listes de cadeaux" name="title" />
</jsp:include> 
<body>
	<%@ include file="base.html" %> 
	<div class="d-flex justify-content-center"><h1>Mes listes de cadeaux</h1></div>
	<% if( request.getAttribute("message") != null){%>
		<div class="alert alert-success alert-dismissible fade show" role="alert">
			${ message }
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	<%}
		if(request.getAttribute("errorAddGiftList") != null){
	%>
		<div class="alert alert-warning alert-dismissible fade show" role="alert">
			${ errorAddGiftList }	
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	<%} %>
	<div class="container d-flex justify-content-center">
		
		<table class="table table-bordered">
			<tr>
				<th>Occasion</th>
				<th>Status</th>
				<th>Date d'expiration</th>
				<th>Actions</th>
			</tr>
			<%
				User u = (User)session.getAttribute("connectedUser");
				ArrayList<GiftList> giftLists = u.getGiftLists();
				//session.setAttribute("user", u);
				for ( GiftList giftList : giftLists) {
			%>
			<tr>
				<td><%= giftList.getOccasion()%></td>
				<td><%= giftList.isEnabled() ? "Activé":" Désactivé"%></td>
				<td><%= giftList.getExpirationDate() == null ? "Pas de date d'expiration": giftList.getExpirationDate() %></td>
				<td>
					<a class="btn btn-secondary" href="./consultList?id=<%=giftList.getListId()%>">Consulter</a>
					<a class="btn btn-secondary" href="./shareList?id=<%=giftList.getListId()%>">Partager</a>
				</td>
			</tr>
			<%
			}
			%>
		</table>
		<div class="ms-5">
			<a class="btn btn-secondary " href="./addList">Ajouter une nouvelle liste</a>
		</div>
	</div>

</body>
</html>