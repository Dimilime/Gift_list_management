<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Ajouter une nouvelle liste" name="title" />
</jsp:include>
<body>
	<%@ include file="base.jsp"%>
	<div class="d-flex justify-content-center"><h1>Ajouter une nouvelle liste</h1></div>
	<div class="d-flex justify-content-center">
		<form action="" method="post">
			<div class="mb-3">
				<label for="ocassion" class="form-label">Occasion</label> 
					<select id="ocassion" class="form-select" name="occasion">
						<option selected value="Christmas">Noël</option>
						<option value="Wedding">Mariage</option>
						<option value="Birthday">Anniversaire</option>
					</select>
					<%
						String error = (String)request.getAttribute("error");
						if(error != null){
					%>
						<div class="form-text"> Erreur : <%=error %></div>
					<%} %>
					
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