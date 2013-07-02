<!-- 
File: header.jsp
Holds common header for all pages in the app.
Includes login/logout option and register/MyProfile option.
 -->

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>COMP461 Project</title>
	<link rel = "stylesheet" type = "text/css" href = "css/header.css" />
	<link rel = "stylesheet" type = "text/css" href = "css/main.css" />
	<link rel = "stylesheet" type = "text/css" href = "css/orderData.css" />
	<script src="helper.js" language="javascript"></script>
</head>
<body>
	<div id = "pageContent">
	<div id = "header">
		<div id = "title"><h1>Portable Computers, LLC</h1></div><!-- End of 'title'-->
		
		<c:choose>
		<c:when test="${customer == (null)}">
		<div id = "login">
			<form action = "LogIn.do" method = "post">
			<input class = "loginEntry" type = "text" id = "logUsername" name = "logUsername" 
				onfocus="if(this.value==this.defaultValue)this.value=''"
				onblur="if(this.value=='')this.value=this.defaultValue"
				value="Username" />
			<input class = "loginEntry" id="logPassword" type = "text" name = "logPassword" 
				onfocus="javascript:changePass(this)"
				onblur="if(this.value=='')javascript:changeText(this)" value="Enter Password" />
			<input type="button" value="Login" onclick="javascript:logInCheck(this.form)"/>
			</form>
			</div>
			
			<div id = "userOptions">
			<a href="registration.jsp" >Register Me</a>
			</div><!-- End of 'userOptions' -->
  			</c:when>
   			
   			<c:otherwise>
   				<div id = "login">
	   			<form action="LogOut.do" method="GET"> 
	   				${loginMsg}
					<input type="submit" value="Logout"/>
					<input type="hidden" name="hdSignInOut" value="Logout" />
				</form>
				</div><!-- End of 'login'-->
				<div id = "userOptions">
				
				<a href="ViewUserInfo.do?customer=${customer}" class="linkSpace">My Profile</a>
				<a href="OrderHistory.do" class="linkSpace">Order History</a>

				</div><!-- End of 'userOptions' -->
   			</c:otherwise>
		</c:choose>
	</div><!-- End of 'header' -->