<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<c:import url="/WEB-INF/header.jsp" />
	<c:import url = "/WEB-INF/nav.html" />
	<br><br>
	
	<div id="orderHistoryTitle">
		${customer.name}'s Order History
	</div>
	<br><br>
	<div id="orderHistory" >
	
	<c:forEach var="order" items="${orderList}" >
	<div class="singleOrder">
		<div class="adminData">
			Order Date:  ${order.orderDate}<br><br>
			Shipping Date: ${order.shipDate }<br><br>
			Delivery Date: ${order.deliverDate }<br><br>
			Order Total: <fmt:formatNumber type="currency" value="${order.total}" />
		</div>
		
		<c:forEach var="orderItem" items="${order.itemList}" >
		<div class="productViewHistory">
		
		<div class="prodImg">
			<img src="images/${orderItem.product.imageURL}" width="100" height="71" />
		</div>
		
		<div class="prodDesc">
			${orderItem.product.prodName }
		</div>
		
		<div id="quantity">
			${orderItem.quantity}
		</div>
		</div><br>
		</c:forEach>
	</div>
	</c:forEach>
	
	</div>
	
		

	

	
	