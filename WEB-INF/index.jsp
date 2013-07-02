<!-- 
File: index.jsp
Home page for the app. Shows a collection of featured items
in the online store. Allows user to click on the item to get the 
item details.
Will show error message if sign in fails.
 -->

<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:import url="header.jsp" />
	<c:import url = "nav.html" />
	<br><br>
	
	<div id = "welcome">
		<b>Welcome to Portable Computers, LLC.</b><br>
		<i>Offering the best portable products at the lowest prices!</i>
		<br><br>
		<p style="color: red">${loginErrMsg}</p>
		<noscript style="color: red">You must enable JavaScript to use this site.</noscript>
		<br>
	</div>
	 
	<div class="featuredProducts">	
		<c:forEach var="product" items="${productList}">
			<div class = "product">
				<a href = "ShowProductDetails.do?pid=${product.key}" >
				<br><img src = "images/${product.value.imageURL}" width="200" height="142"/>
				<br>${product.value.prodName}</a>
				<br><span>$${product.value.prodPrice}</span>
			</div>
		</c:forEach>
	</div>
	<c:import url ="footer.html" />