<%@page import="be.project.javabeans.Gift"%>
<%@page import="be.project.javabeans.GiftList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
	<jsp:param value="Mes invitations" name="title" />
</jsp:include> 
<body>
	<%@ include file="base.jsp" %> 
	<% 
		if (request.getAttribute("invitation") != null){ 
			GiftList invitation = (GiftList)request.getAttribute("invitation");
			
			if(invitation.getGifts() != null || !invitation.getGifts().isEmpty()){

	%>
	
	<div class="container py-5 bg-light">
		<div class="row">
			<%for(Gift gift : invitation.getGifts()){%>
			<div class="col-md-3 col-sm-6">
				<div class="card mb-4 shadow-sm">
					<%=gift.getImage()==null? "<img alt='cadeau vide' src='./resources/imgs/cadeau.png' class='w-100'></img>" 
					:"<img alt='image du cadeau' src='data:image/png;base64,"+gift.getImage()+"' class='w-100'>" %>
					<div class="card-body">
						<h5 class="card-title"><%=gift.getName()%></h5>
						<p class="card-text">
							<%=gift.getDescription()== null? "Pas de description" : gift.getDescription() %>
						</p>
						<div class="btn-group">
							<button type ="button" class="btn btn-sm btn-outline-secondary">
								Offrir
							</button>
							<button type ="button" class="btn btn-sm btn-outline-secondary ml-2">
								Offrir à plusieurs ?
							</button>
							
						</div>
					</div>
					<div class="card-body">
						<%=gift.getLink() ==null? "Pas de lien fourni": "<a href='"+gift.getLink()+"' class='card-link'>Lien marchand</a>" %>
					</div>
				</div>
			</div>
			<%} %>
		</div>
		<%				
			}
		}else{
	%>
	<div class="d-flex justify-content-center">
		<h1>Pas de cadeaux dans cette liste</h1>
	</div>
	<%} %>
	</div>
	
</body>
</html>