<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Oups une erreur est survenue</title>
</head>
<body>
	<% String error = (String)request.getAttribute("error"); %>
	<div class="alert alert-danger" role="alert">
		<h3>Oups une erreur est survenue</h3>
		<h3>Veuillez contacter l'administrateur</h3>
		<p>Informations sur l'erreur rencontrée <strong><%=error %></strong></p>
	</div>
</body>
</html>