<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
	<c:import url="/WEB-INF/header.jsp" />
	<c:import url="/WEB-INF/nav.html" />
	<br><br>
	<div id="regMsg">
		${regConfirmMsg}
	</div>
	 
	<br><br>
	
	<div id="registerPage">
		<form method="post" action="RegisterCustomer.do" id="regForm">
			<table id="userInfoTable">
				<tr>
				<td><input type="text" name="name" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value="Name"/></td>				
				</tr>
				<tr>
				<td><input type="text" name="street" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value="Street"/></td>
				</tr>
				<tr>
				<td><input type="text" name="city" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value="City"/></td>
				</tr>
				<tr>
				<td><input type="text" name="state" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value="State"/></td>
				</tr>
				<tr>
				<td><input type="text" name="zip" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value="Zip Code"/></td>
				</tr>
				<tr>
				<td><input type="text" name="email" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value="E-mail Address"/></td>
				</tr>
				<tr>
				<td><input type="text" name="userName" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value="Username"/></td>
				</tr>
				<tr>
				<td><input id="pwordBox1" type="text" name="password1" onfocus="javascript:changePass(this);return false;" 
						onblur="javascript:changeText(this);return false;" 
						value="Enter Password"/></td>
				</tr>
				<tr>
				<td><input id="pwordBox2" type="text" name="password2" onfocus="javascript:changePass(this);return false;" 
						onblur="javascript:changeText(this);return false;" 
						value="Re-enter Password"/></td>
				</tr>
			</table>
			<div id="regButtons">
				<input type="submit" name="regOption" value="Register Me" />
				<input type="reset" value="Reset" />			
			</div>
		</form>		
		<div id="regButtons">
		<form action ="ShowAllProducts.do">
			<input type="submit" value="Cancel" onclick="ShowAllProducts.do" />
		
		</form>
		</div>
		
	</div>
	
	<c:import url="/WEB-INF/footer.html" />