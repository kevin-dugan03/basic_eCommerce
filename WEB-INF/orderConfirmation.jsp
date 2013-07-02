<!-- 
File: orderconfirmation.jsp
Is the order confirmation page for the app. Displays summary
data when a customer submits an order.
 -->

<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<c:import url="/WEB-INF/header.jsp" />
	<c:import url = "/WEB-INF/nav.html" />
	<br><br>
	
	<div id = "welcome">
		${orderConfirm}
	</div>
	
	<div id="cartColumns">
		<div id="prodInfoHeader">
			<span>DESCRIPTION</span>
		</div>
		<div id="prodQtyHeader">
			<span>QTY</span>
		</div>
		<div id="prodTtlHeader">
			<span>TOTAL</span>
		</div>
	</div>

	<c:forEach var = "orderItem" items = "${order.itemList}">
		<div class="prodViewCart">
			<div class="prodImg">
				<a href="productPage.jsp"><img src="images/${orderItem.product.imageURL}" width="100" height="71" /></a>
			</div>
			<div class="prodDesc">
				${orderItem.product.prodName}
			</div>
			<div class="prodQty">
				${orderItem.quantity}
			</div>
			<div class="prodPrice">
				<fmt:formatNumber value="${orderItem.itemCost}" type="currency" />
			</div>
		</div>
	</c:forEach>	

	<hr><br>
		<div class="subtotal">
		Subtotal: <fmt:formatNumber value="${order.subTotal}" type="currency" />
	</div>
	<br><br>
	
	<div id="total">
		<div id="shippingCharge">
			Shipping: <fmt:formatNumber value="${customer.shippingRate}" type="currency" />
		</div><br>
		<div id="orderTotal">
			Total: <fmt:formatNumber value="${order.total}" type="currency" />
		</div>
	</div>
	<br><br>