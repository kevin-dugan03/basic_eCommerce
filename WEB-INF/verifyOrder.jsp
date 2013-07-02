<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<c:import url="/WEB-INF/header.jsp" />
	<c:import url = "/WEB-INF/nav.html" />
	<br><br>
	
	<div id="welcome">
		<p style="color: red">${confirmErrMsg}</p>
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
			<a href="CartUpdate?pid=${cartItem.product.prodID}&action=delete" ><img src="images/trash.jpg" width="30" height="30" /></a>
		</div>
		<div class="prodPrice">
			<fmt:formatNumber type="currency" value="${cartItem.itemCost}" />
		</div>
		</div>
	</c:forEach>	

	<hr><br>
	
	<div id="total">
		Subtotal: <fmt:formatNumber type="currency" value="${cart.subTotal}" /><br><br>
		Shipping: <fmt:formatNumber type="currency" value="${customer.shippingRate}" /><br><br>
		Total: <fmt:formatNumber type="currency" value="${cart.total}" />
	</div>
	<hr>
	
	<form action="PlaceOrder.do" method="post">
	<div id="checkoutOptions">	
	<p style="color:red">${paymentError}</p><br>
	<p style="color:red">${customerInfoError}</p><br>
		<div id="paymentInfo">
		Payment Information<br><br>
			<select name="ccInfo">
				<option>Visa</option>
				<option>Mastercard</option>
				<option>American Express</option>
				<option>Discover</option>
			</select><br>
			<input type="text" name="nameOnCard" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="Name on Card" />
			<input type="text" name="cardNumber" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="Card Number (no dashes)" /><br>
			<input type="text" name="cardExpDate" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="Exp Date (mm/yy)" /><br>
			<input type="text" name="cardCSC" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="Card Security Code" />
		</div>

			<div id="shipping">
			Shipping Address Information<br><br>
				<input type="text" name="shippingName" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="${customer.name }" />
				<input type="text" name="shippingStreet" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="${customer.defAddr.street }" />
				<input type="text" name="shippingCity" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="${customer.defAddr.city }" />
				<input type="text" name="shippingState" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="${customer.defAddr.state }" size="5" />
				<input type="text" name="shippingZip" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="${customer.defAddr.zip }" />
			</div>

			<div id="billing">
			Billing Address Information<br><br>
				<input type="text" name="billingName" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="${customer.name }" />
				<input type="text" name="billingStreet" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="${customer.defAddr.street }" />
				<input type="text" name="billingCity" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="${customer.defAddr.city }" />
				<input type="text" name="billingState" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="${customer.defAddr.state }" size="5" />
				<input type="text" name="billingZip" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue" value="${customer.defAddr.zip }" style="width: px"/>
			
			</div>
		
		<div id="checkoutButtons">
		<div class="cartCheckout">
			<input type="button" value="Place Order" onclick="javascript:checkoutValidation(this.form)" />
		</div>
		</div>
	</div>
	</form>
	
	<div class="continueShopping">
	<form action="ShowAllProducts.do" >
		<input type="submit" value="Continue Shopping"  />	
	</form>	
	</div>	<br><br>

	<c:import url = "/WEB-INF/footer.html" />