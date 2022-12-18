<%@page import="be.project.javabeans.Gift"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listes de cadeaux</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
	<table class="table">
		<tr>
			<th>Ocassion</th><th>Status</th><th>Date d'expiration</th><th>Modifier</th>
		</tr>
		<% 
			//ArrayList<Gift> userList = (ArrayList<Gift>) request.getAttribute("userList");
			for(int i=0; i<8;i++)
			{
		%>
				<tr>
					<td></td><td></td><td></td><td><button><a href="./<%=i%>">Modifier</a></button></td>
				</tr>
		<%
			}
		%>
	</table>
</div>
	
</body>
</html>