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
			System.out.println(invitation.getGifts().size());
		}
	%>
	
	<div class="container py-5 bg-light">
		<div class="row">
			<%for(int i =0; i<8; i++){%>
			<div class="col-md-3 col-sm-6">
				<div class="card mb-4 shadow-sm">
					<img src="./resources/imgs/cadeau.png" class="w-100"></img>
					<div class="card-body">
						<h5 class="card-title">Nom du cadeau</h5>
						<p class="card-text">
						Description : Lorem ipsum dolor sit amet, consectetur adipisicing elit. Non odio!
						Lorem ipsum dolor sit amet, consectetur adipisicing elit. Adipisci eum.
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
						<a href="#" class="card-link">Lien marchand</a>
					</div>
				</div>
			</div>
			<%} %>
		</div>
	</div>
	
</body>
</html>