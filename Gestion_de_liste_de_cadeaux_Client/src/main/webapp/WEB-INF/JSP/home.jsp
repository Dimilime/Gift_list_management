<%@page import="be.project.javabeans.Gift"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Mes listes de cadeaux" name="title" />
</jsp:include> 
<body>
	<%@ include file="base.jsp" %> 
	<div class="d-flex justify-content-center"><h1>Mes listes de cadeaux</h1></div>
	
	<div class="container d-flex justify-content-center">
		
		<table class="table">
			<tr>
				<th>Ocassion</th>
				<th>Status</th>
				<th>Date d'expiration</th>
				<th>Modifier</th>
			</tr>
			<%
			//ArrayList<Gift> userList = (ArrayList<Gift>) request.getAttribute("userList");
			for (int i = 0; i < 8; i++) {
			%>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td>
					<a class="btn btn-secondary" href="./<%=i%>">Modifier</a>
				</td>
				<td>
					<a class="btn btn-secondary" href="./<%=i%>">Partager</a>
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