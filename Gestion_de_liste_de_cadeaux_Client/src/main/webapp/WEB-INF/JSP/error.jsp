<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="true"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Oups une erreur est survenue" name="title"/>
</jsp:include>
<body>
	<%@ include file="base.jsp" %>
	<% String error = (String)request.getAttribute("error"); %>
	<div class="alert alert-danger" role="alert">
		<h3>Oups une erreur est survenue</h3>
		<h3>Veuillez contacter l'administrateur</h3>
		<p>Informations sur l'erreur rencontrée <strong><%=error %></strong></p>
	</div>
</body>
</html>