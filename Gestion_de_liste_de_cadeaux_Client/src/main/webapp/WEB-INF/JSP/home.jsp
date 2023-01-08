<%@page import="be.project.javabeans.GiftList"%>
<%@page import="be.project.javabeans.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="handleException.jsp"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Mes listes de cadeaux" name="title" />
</jsp:include> 
<body>
	<%!
		public static String convertDateEUFormat(LocalDate date){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    return date.format(formatter);	
		}
	%>
	<%@ include file="base.jsp" %> 
	<div class="d-flex justify-content-center"><h1>Mes listes de cadeaux</h1></div>
	<% if( request.getAttribute("message") != null){%>
		<div class="alert alert-success alert-dismissible fade show" role="alert">
			${ message }
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	<%}
		if( request.getAttribute("errorList") != null){%>
		<div class="alert alert-warning alert-dismissible fade show" role="alert">
			${ errorList }
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
				int i=0;
				if(u.getGiftLists() !=null){
					for ( GiftList giftList : u.getGiftLists()) {
			%>
			<tr>
				<td><%= giftList.getOccasion()%></td>
				<td><%= giftList.isEnabled() ? "Activé":" Désactivé"%></td>
				<td><%= giftList.getExpirationDate() == null ? "Pas de date d'expiration": convertDateEUFormat(giftList.getExpirationDate()) %></td>
				<td>
					<a class="btn btn-secondary" href="./consultList?id=<%=giftList.getListId()%>">Consulter</a>
					<a class="btn btn-secondary" href="./shareList?id=<%=giftList.getListId()%>&index=<%=i++%>">Partager</a>
				</td>
			</tr>
			<%
			}}
			%>
		</table>
		<div class="ms-5">
			<a class="btn btn-secondary " href="./addList">Ajouter une nouvelle liste</a>
		</div>
	</div>

</body>
</html>