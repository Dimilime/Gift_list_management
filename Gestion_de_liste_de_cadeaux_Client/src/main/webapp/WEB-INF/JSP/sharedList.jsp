<%@page import="be.project.javabeans.GiftList"%>
<%@page import="be.project.javabeans.Gift"%>
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
		GiftList giftList = (GiftList)request.getAttribute("GiftList");
	%>
	<% if( request.getAttribute("expiredList") != null){%>
		<div class="d-flex justify-content-center">
		<h1>Liste désactivée</h1>
		</div>
		<div class="alert alert-danger alert-dismissible fade show" role="alert">
			<%=request.getAttribute("expiredList")  %>
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	<%}%>
	
	<%if(giftList !=null){ %>
	<div class="d-flex justify-content-center">
	<h1>Liste <%=giftList.getOccasion()%> de <%=giftList.getGiftListUser().getLastname() + " " + giftList.getGiftListUser().getFirstname() %></h1>
	</div>
	<div class="container py-5 bg-light">
		<div class="row">
			<%for(Gift gift : giftList.getCurrentGifts()){%>
			<div class="col-md-3 col-sm-6">
				<div class="card mb-4 shadow-sm">
					<%if(gift.getImage() != null){ %>
						<img src="./resources/imgs/cadeau.png" class="w-100"></img>
					<%}else{ %>
					<img src="./resources/imgs/cadeau.png" class="w-100"></img>
					<%} %>
					<div class="card-body">
						<h5 class="card-title"><%=gift.getName() %></h5>
						<p class="card-text">
						<%=gift.getDescription() %>
						</p>
						<p class="card-text">
						<%=gift.getAveragePrice() %>
						</p>
						<%if(gift.isFullyPaid()){ %>
							<p class="card-text">
							Ce cadeau a déjà été offert
							</p>
						<%}%>
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
						<a href="<%=gift.getLink()%>" class="card-link">Lien marchand</a>
					</div>
				</div>
			</div>
			<%} %>
		</div>
	</div>
	<%} %>
	
</body>
</html>