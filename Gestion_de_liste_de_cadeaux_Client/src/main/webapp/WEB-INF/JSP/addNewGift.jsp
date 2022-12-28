<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Ajouter un autre cadeau" name="title" />
</jsp:include>
<body>
	<%@ include file="base.html"%>
	<div class="d-flex justify-content-center"><h1>Ajouter un autre cadeau? </h1></div>
	<div id="addAnotherGift" class="d-flex justify-content-center" >
	<form action="" method="post">
		<button type="submit" name="response" value="yes">Oui</button>
		<button type="submit" name="response" value="no">Non</button>
	</form>
		
	</div>
</body>
</html>