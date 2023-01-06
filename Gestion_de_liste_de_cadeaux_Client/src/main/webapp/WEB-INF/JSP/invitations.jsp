<%@page import="be.project.javabeans.GiftList"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Mes invitations" name="title" />
</jsp:include> 
<body>
	<%@ include file="base.jsp" %> 
	<div class="d-flex justify-content-center"><h1>Mes invitations</h1></div>
	<div class="container d-flex flex-wrap justify-content-center">
	
		<table class="table">
			<thead>
				<tr>
					<th>Occasion</th>
					<th>Date d'expiration</th>
					<th>Appartient à</th>
				</tr>
			</thead>
			<tbody>
			<% 
			if (request.getAttribute("invitations") != null) {
				ArrayList<GiftList> invitations = (ArrayList<GiftList>)request.getAttribute("invitations");
				for ( GiftList invitation : invitations) {
					if(invitation.isEnabled()){
			%>
			<tr>
				<td><%= invitation.getOccasion()%></td>
				<td><%= invitation.getExpirationDate() == null ? "Pas de date d'expiration": invitation.getExpirationDate() %></td>
				<td><%= invitation.getGiftListUser().getFirstname() + " "+ invitation.getGiftListUser().getLastname()%></td>
				<td>
					<a class="btn btn-secondary" href="./sharedList?id=<%=invitation.getListId()%>">Consulter</a>
				</td>
			</tr>
			<%}
			}}
			%>
			</tbody>

		</table>

	</div>

</body>
</html>