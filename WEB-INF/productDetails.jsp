<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<c:import url="/WEB-INF/header.jsp" />
	<c:import url = "/WEB-INF/nav.html" />	
	<br><br>
	
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
	
	   <div class="prodViewCart">
		   <form action="AddToCart.do" method="post">
		   
			<div class="prodImg">
				<a href="productPage.jsp"><img src="images/${product.imageURL}" width="100" height="71" /></a>
			</div>
			<div class="prodDesc">
				${product.prodName}
			</div>
			<div class="prodQty">
				<select name = "prodQuantity">
					<c:forEach var = "i" begin = "0" end = "${product.prodStock}">
						<option value = "${i}">${i}</option>
					</c:forEach>
					<option selected="selected">0</option>
				  </select>
			</div>
			<div class="prodPrice">
				<fmt:formatNumber value="${product.prodPrice}" type="currency" />
			</div>
			<input type="hidden" value="add" name="action" />
			<input type="hidden" name="prodID" value="${product.prodID}" />
			<center><input type="button" value="Add To Cart" onclick="javascript:checkQuantity(this.form)"/></center>
		  	</form>
		</div>
	<fieldset id="productDescription">
		<legend>Product Description</legend>
		${product.prodDesc}
	</fieldset>

	<c:import url ="/WEB-INF/footer.html" />