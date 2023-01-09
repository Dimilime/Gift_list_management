<%@page import="be.project.javabeans.GiftList"%>
<%@page import="be.project.javabeans.Gift"%>
<%@page import="be.project.javabeans.Participation"%>
<%@page import="be.project.javabeans.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="handleException.jsp"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Offrir un cadeau" name="title" />
</jsp:include> 
<body>
	<%!

	%>
	<% 	String sharing = (String)request.getAttribute("sharing");
		Gift gift = (Gift)request.getAttribute("gift");
	%>
	<%@ include file="base.jsp" %> 
	<div class="d-flex justify-content-center"><h1>Vous êtes sur le point d'offrir <%=gift.getName() %></h1></div>

			<% if(request.getAttribute("error")!=null){%>
				<div class="alert alert-danger" role="alert">
	  				<%= request.getAttribute("error") %>
				</div>
			<% } %>
			<%if(sharing.equals("no")){ %>
			
			<form action="offergift" method="POST">

				<input type="hidden" name="giftId" value="<%=gift.getGiftId()%>">
			
				<div class="d-grid gap-2 col-6 mx-auto">
					<h2>Êtes-vous sûr de vouloir payer l'intégralité soit <%=String.format("%.2f",  gift.getAveragePrice())%> euros?</h2>
					<button type="submit" class="btn-red btn-confirm">Confirmer</button>
					<a class="btn-red" href="./invitations">Annuler</a>
				</div>
			</form>
			
			<%} %>
			
			<%if(sharing.equals("yes")){ %>
			<form action="offergift" method="POST">
	
				<input type="hidden" name="giftId" value="<%=gift.getGiftId()%>">
				<div class="d-grid gap-2 col-6 mx-auto">
				<h2>Prix total du cadeau : <%=String.format("%.2f",  gift.getAveragePrice())%> euros</h2>
				<%if(gift.priceRemain() != gift.getAveragePrice()){ %>
				<h3>Une partie du prix étant partagée, il reste encore <%=String.format("%.2f",gift.priceRemain())%> euros à régler</h3>
				<%} %>
				<div class="mb-3">
					<label for="payInput" class="form-label">Le prix que vous souhaitez mettre :</label> 
					<input id="payInput" type="number" class="form-control" name="pay"  min=0 max=<%=gift.priceRemain()%>>
				</div>
				
					<button type="submit" class="btn-red btn-confirm">Confirmer</button>
					<a class="btn-red" href="./invitations">Annuler</a>
				</div>
			</form>
			
			<%} %>
				
</body>
</html>