<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Erreur" name="title" />
</jsp:include>
<body>
	<%@ include file="base.jsp" %> 
	<h1>Exception!</h1>
	<hr />
	<h2>Description de l'exception : <%= exception.toString() %></h2>
	<hr/>
</body>
</html>