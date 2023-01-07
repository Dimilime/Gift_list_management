<%@page import="be.project.javabeans.Notification"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Mes notifications" name="title" />
</jsp:include> 
<body>
	<%@ include file="base.jsp" %> 
	<div class="d-flex justify-content-center"><h1>Mes notifications</h1></div>
	<%
	if (request.getAttribute("message") != null) {
	%>
	<div class="alert alert-success alert-dismissible fade show"
		role="alert">
		<%=request.getAttribute("message")%>
		<button type="button" class="btn-close" data-bs-dismiss="alert"
			aria-label="Close"></button>
	</div>
	<%
	}
	if (request.getAttribute("errorShareList") != null) {
	%>
	<div class="alert alert-warning alert-dismissible fade show"
		role="alert">
		<%=request.getAttribute("errorShareList")%>
		<button type="button" class="btn-close" data-bs-dismiss="alert"
			aria-label="Close"></button>
	</div>
	<%
	}
	%>
	<div class="container d-flex flex-wrap justify-content-center">
		<table class="table">

			<thead>
				<tr>
					<th>Titre</th>
					<th>Message</th>
				</tr>
			</thead>
			<tbody>
			<% 
			if (request.getAttribute("notifications") != null) {
				ArrayList<Notification> notifications = (ArrayList<Notification>)request.getAttribute("notifications");
				if(notifications != null)
					for ( Notification notif : notifications) {
			%>
			<tr>
				<td><%= notif.getTitle()%></td>
				<td><%= notif.getMessage()%></td>
				
			</tr>
			<%
			}}
			%>
			</tbody>

		</table>

	</div>
</body>
</html>