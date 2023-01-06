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
	<jsp:param value="Modification d'une liste" name="title" />
</jsp:include> 
<body>
	<%!
		public static String showOccasionSelectOption(String selectedValue){
			String optionsHTML="";
			String[] options = { "Noel", "Mariage", "Anniversaire"};
			for(int i=0; i<3;i++){
				if(options[i].equals(selectedValue))
				{
					optionsHTML += "<option selected value=\"" + options[i]  + "\">" + options[i] + "</option>";
				}else{
					optionsHTML += "<option value=\"" + options[i]  + "\">" + options[i] + "</option>";
				}
			}
			return optionsHTML;
		}
		public static String showParticipantsSelectOption(ArrayList<User> sharedUsers, ArrayList<User> allUsers){
			String optionsHTML="";
			for(int i=0; i< allUsers.size() ;i++){
				boolean isSelected =false;
				int id= allUsers.get(i).getUserId();
				for(User user : sharedUsers){
					if(user.getUserId() ==id){
						isSelected=true;
					}
				}
				if(isSelected){
					optionsHTML += "<option selected value=\"" + id + "\">" 
					+ allUsers.get(i).getLastname() + " " + allUsers.get(i).getLastname() 
					+  "</option>";
				}else{
					optionsHTML += "<option value=\"" + id + "\">" 
							+ allUsers.get(i).getLastname() + " " + allUsers.get(i).getLastname() 
							+  "</option>";
				}
			}
			return optionsHTML;
		}

	%>
	<% 	GiftList giftList = (GiftList)request.getAttribute("giftList");
		ArrayList<User> allUsers = (ArrayList<User>)request.getAttribute("allUsers");
	%>
	<%@ include file="base.jsp" %> 
	<div class="d-flex justify-content-center"><h1>Modification de la liste numéro <%=giftList.getListId() %></h1></div>

	<div class="modifyList-form">
			<% if(request.getAttribute("error")!=null){%>
				<div class="alert alert-danger" role="alert">
	  				<%= request.getAttribute("error") %>
				</div>
			<% } %>
				<form action="editList" method="POST">
				 <div class="mb-3">
				<label for="occasionSelect" class="form-label">Occasion:</label> 
					<select id="occasionSelect" class="form-select" name="occasion">
						<%= showOccasionSelectOption(giftList.getOccasion())%>
					</select>
			</div>
			<div class="mb-3">
				<label for="expirationDateInput" class="form-label">Date d'expiration</label>
				<input type="date" class="form-control" name="expirationDate"
					id="expirationDateInput" value="<%= giftList.getExpirationDate()%>">
			</div>
			<%if(!giftList.dateIsExpired()){%>
			<div class="mb-3">
				<label for="enabledCheckbox" class="form-label">Activation de la liste</label>
				<input id="enabledCheckbox" type="checkbox" class="form-check-input" 
				name="enabled" <%if(giftList.isEnabled()) {%>checked <%} %>>
			</div>
			<% }else{%>
			<strong>La liste ayant déjà expiré, vous ne pouvez pas la désactiver/activer<br>
					Veuillez d'abord modifier la date d'expiration
			</strong>
			<%} %>
			<div class="mb-3">
				<label for="participants" class="form-label">Participants:</label> 
					<select class="mdb-select md-form colorful-select"
					id="participantsSelect" multiple  name="participants">
					<option value disabled>Partagez votre liste avec:</option>
						<%= showParticipantsSelectOption(giftList.getSharedUsers(), allUsers)%>
					</select>
					<div id="participantsSelect" class="form-text" class="invalid-feedback">CTRL+ click pour une sélection multiple</div>	
			</div>
			
			<div class="d-grid gap-2 col-6 mx-auto">
				<button type="submit" class="btn-red btn-confirm">Modifier</button>
				<a class="btn-red" href="./home">Annuler</a>
			</div>
				</form>
			</div>
</body>
</html>