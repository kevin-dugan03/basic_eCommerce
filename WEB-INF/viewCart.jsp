<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<c:import url="/WEB-INF/header.jsp" />
	<c:import url = "/WEB-INF/nav.html" />
	<br><br>
	
	<div id="regMsg">
		${cartMsg}<br><br>
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

	<c:forEach var = "cartItem" items = "${cart.itemList}">
		<div class="prodViewCart">
			<div class="prodImg">
				<a href="productPage.jsp"><img src="images/${cartItem.product.imageURL}" width="100" height="71" /></a>
			</div>
			<div class="prodDesc">
				${cartItem.product.prodName}
			</div>
			<form action="UpdateCart.do" method="post">
			<div class="prodQty">
			<select name = "prodQuantity">
				<c:forEach var = "i" begin = "0" end = "${cartItem.product.prodStock}">
					<option value = "${i}">${i}</option>
				</c:forEach>
				<option selected="selected">${cartItem.quantity}</option>
		  	</select>
		  	<input type="hidden" name="prodID" value="${cartItem.product.prodID}" />
		  	<br><input type="submit" value="update" style="margin-top: 3px" />
			</div>
			</form>
			
			<div class="trashcanImg">
				<a href="DeleteItem.do?pid=${cartItem.product.prodID}" ><img src="images/trash.jpg" width="20" height="20" /></a>
			</div>
			<div class="prodPrice">
				<fmt:formatNumber value="${cartItem.itemCost}" type="currency" />
			</div>
		</div>
	</c:forEach>	

	<hr><br>
	<div class="subtotal">
		Subtotal: <fmt:formatNumber value="${cart.subTotal}" type="currency" />
	</div>
	<br><br>
	
	<div id="cartOptions">
		<div class="cartCheckout">
		<form action="VerifyOrder.do">
			<input type="hidden" name="hdCustValue" value="${customer.userID}" />
			<input type="hidden" name="emptyCart" value="${cart.itemList.isEmpty()}" />
			<input type="button" value="Checkout"  onclick="javascript:checkLoggedIn(form)"/>
		</form>
		</div>
		
		<div class="continueShopping">
		<form action="ShowAllProducts.do">
			<input type="submit" value="Continue Shopping" />
		</form>			
		</div>
	</div>
	<hr>

	<c:import url = "/WEB-INF/footer.html" />