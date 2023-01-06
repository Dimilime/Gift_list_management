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
	<jsp:param value="Consultation d'une liste" name="title" />
</jsp:include> 
<body>
	<%!
		public static String manageCodeColorPriorityLevel(int priorityLevel){
		String td = "";
			switch(priorityLevel){
				case 1: case 2: case 3:
				case 4:
					td= "<td class='code-green'>"+ priorityLevel + "</td>";
					break;
				case 5: case 6 : case 7:
					td= "<td class='code-orange'>"+ priorityLevel + "</td>";
					break;
				case 8: case 9 : case 10:
					td= "<td class='code-red'>"+ priorityLevel + "</td>";
					break;
				default:
					td= "<td>"+ priorityLevel + "</td>";
					break;
			}
			return td;
		}
	%>
	
	<% 	GiftList giftList = (GiftList)request.getAttribute("giftList");
		User u = (User)session.getAttribute("connectedUser");
	%>
	<%@ include file="base.jsp" %> 
	<div class="d-flex justify-content-center"><h1>Liste numéro <%=giftList.getListId() %></h1></div>
	<% if( request.getAttribute("message") != null){%>
		<div class="alert alert-success alert-dismissible fade show" role="alert">
			${ message }
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	<%}%>
	<div class="container d-flex justify-content-center">
		<table class="table table-bordered">
			<caption>Infos de la liste</caption>
			<tr>
				<th>Créateur de la liste</th>
				<th>Occasion</th>
				<th>Date d'expiration</th>
				<th>Status</th>
				<th>Participants</th>
				<%if(u.getUserId() == giftList.getGiftListUser().getUserId()){%>
				<th>Actions</th>
				<%} %>
			</tr>
			<tr>
				<th><%= giftList.getGiftListUser().getLastname() + " " +  giftList.getGiftListUser().getFirstname()  %></th>
				<td><%= giftList.getOccasion()%></td>
				<td><%= giftList.getExpirationDate() == null ? "Pas de date d'expiration": giftList.getExpirationDate() %></td>
				<td><%= giftList.isEnabled() ? "Activée":" Désactivée"%></td>
				<% if(giftList.getSharedUsers().isEmpty()){%>
					<td>Aucun participant pour le moment</td>
				<%}else{ %>
					<td>
						<ul>
						<% for(User user :giftList.getSharedUsers()){ %>
							<li><%=user.getLastname() + " " + user.getLastname() %></li>
							<%} %>
						</ul>
					</td>
				<% }%>
				<%if(u.getUserId() == giftList.getGiftListUser().getUserId()){%>
				<td>
					<a href="./editList?id=<%=giftList.getListId()%>">Modifier la liste</a>
				</td>
				<%} %>
				
			</tr>
		</table>
	</div>
	<div class="container d-flex justify-content-center">
		<table class="table table-bordered">
			<caption>Liste des cadeaux</caption>
			<tr>
				<th>Niveau de priorité</th>
				<th>Nom d'article</th>
				<th>Description</th>
				<th>Prix estimé</th>
				<th>Lien</th>
				<th>Image</th>
				<th>Déjà Réservé?</th>
				<th>Actions</th>
			</tr>
			<%
				for (Gift gift : giftList.getCurrentGifts()) {
			%>
			<tr>
				<%= manageCodeColorPriorityLevel(gift.getPriorityLevel()) %>
				<td><%= gift.getName()%></td>
				<td><%= gift.getDescription()%></td>
				<td><%= gift.getAveragePrice()%></td>
				<td><%= gift.isReserved() ? "Oui":" Non"%></td>
				<td><%= gift.getLink() == null ? "Pas de lien fourni": gift.getLink()  %></td>
				<td><%= gift.getImage() == null ? "Pas d'image fournie": gift.getImage() %></td>
				<% if(gift.getParticipations() != null){%>
					<td>
						<ul>
						<%for(Participation participation: gift.getParticipations()){ %>
							<li><%= participation.getParticipant().getLastname() 
								+ " " + participation.getParticipant().getFirstname() 
								+ "-> participe à hauteur de " + participation.getParticipationpart() + " euros"
								%>
							</li>
						<%} %>
						</ul>
					</td>
				<%} %>
				<%if(u.getUserId() == giftList.getGiftListUser().getUserId() && !gift.isReserved()){%>
				<td>
					<a href="./editGift?id=<%=gift.getGiftId()%>">Modifier le cadeau</a>
				</td>
				<%} %>
				<%if(!gift.isFullyPaid()){%>
				<td>
					<a href="./offerGift?id=<%=gift.getGiftId()%>">Offrir en partie ou totalement ce cadeau</a>
				</td>
				<%} %>
			</tr>
			<%} %>
		</table>
		 
	</div>
	<div class="center">
		<a class="btn-red" href="./home">Retour</a>
	</div>
	
</body>
</html>