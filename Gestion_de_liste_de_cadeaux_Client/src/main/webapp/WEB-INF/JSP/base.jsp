<%@page import="be.project.javabeans.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="handleException.jsp"%>
<nav class="navbar fixed-top navbar-expand-lg bg-secondary bg-opacity-10">
	<a class="navbar-brand" href="./"><img src="./resources/imgs/gift32.png"
		width="32" height="32"/>Liste de cadeaux</a>
	<div class="collapse navbar-collapse justify-content-between " id="navbarText">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item "><a class="nav-link" href="./">Mes
					listes</a></li>
			<li class="nav-item "><a class="nav-link" href="./invitations">Mes
					invitations</a></li>
			<li class="nav-item"><a class="nav-link" href="./notifications">Mes
					notifications</a></li>
		</ul>
		
		<a class="btn btn-primary me-3" href="./logout">Deconnexion</a>
	</div>
	<div class="nav-item me-3">Connecté en tant que <B><%=((User) session.getAttribute("connectedUser")).getFirstname() %></B></div>
</nav>