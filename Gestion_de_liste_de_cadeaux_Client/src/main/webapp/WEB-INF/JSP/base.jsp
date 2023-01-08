<%@page import="be.project.javabeans.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="handleException.jsp" session="true"%>
<jsp:useBean id="admin" class="be.project.javabeans.User" scope="session"></jsp:useBean>
<nav class="navbar navbar-expand-md bg-opacity-10">
        <a class="navbar-brand" href="./home">
            <img src="./resources/imgs/gift32.png"width="32" height="32" alt="Logo of the website"/>
            Liste de cadeaux</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggler" aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>
        <div class="collapse navbar-collapse justify-content-between"  id="navbarToggler">
            <ul class="navbar-nav mr-auto mb-2 mb-lg-0">
			<li class="nav-item "><a class="nav-link" href="./">Mes
					listes</a></li>
			<li class="nav-item "><a class="nav-link" href="./invitations">Mes
					invitations</a></li>
			<li class="nav-item">
			
			<a class="nav-link notif_href" href="./notifications">Mes notifications
			<%if(session.getAttribute("notif")!=null){ %> <span><%=session.getAttribute("notif") %></span><%} %>
			</a>
			
			</li>
			
		</ul>
		<a class="btn-red btn-logout" href="./logout">Deconnexion</a>
	</div>
	<div class="nav-item me-3">Connecté en tant que <B><%=((User) session.getAttribute("connectedUser")).getFirstname() %></B></div>
</nav>
