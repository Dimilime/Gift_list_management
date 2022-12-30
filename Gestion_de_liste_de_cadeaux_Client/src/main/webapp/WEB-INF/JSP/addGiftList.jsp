<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="handleException.jsp"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Ajouter une nouvelle liste" name="title" />
</jsp:include>
<body>
	<%@ include file="base.html"%>
	<div class="d-flex justify-content-center"><h1>Ajouter une nouvelle liste</h1></div>
	<%
		String error = (String)request.getAttribute("errorAddGiftList");
		if(error != null){
	%>
	<div class="alert alert-warning alert-dismissible fade show" role="alert">
		Erreur : <%=error %>
	<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	</div>
	<%} %>
	<div class="d-flex justify-content-center">
		<form action="addList" method="post">
			<div class="mb-3">
				<label for="ocassion" class="form-label">Occasion</label> 
					<select id="ocassion" class="form-select" name="occasion">
						<option selected value="Noel">Noël</option>
						<option value="Mariage">Mariage</option>
						<option value="Anniversaire">Anniversaire</option>
					</select>
					
					
			</div>
			<div class="mb-3">
				<label for="expirationDate" class="form-label">Date d'expiration</label>
				<input type="date" class="form-control" name="expirationDate"
					id="expirationDate">
			</div>
			<div class="d-grid gap-2 col-6 mx-auto">
				<button type="submit" class="btn btn-primary btn-lg">Ajouter</button>
			</div>
		</form>
	</div>
</body>
</html>