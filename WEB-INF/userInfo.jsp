<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
	<c:import url="/WEB-INF/header.jsp" />
	<c:import url="/WEB-INF/nav.html" />
	<br><br>
	
	<div id="regConfirmMsg">
		${regConfirmMsg}<br><br>
		In order to make changes, you will have to re-enter your password<br><br>
	</div>
	
	<div id="registerPage">
		<form method="post" action="UpdateCustomer.do" id="userForm">
			<table id="userInfoTable">
				<tr>
					<td><input type="text" name="name" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value='${customer.getName()}' /></td>				
				</tr>
				<tr>
					<td><input type="text" name="street" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value='${customer.getDefAddr().street}' /></td>
				</tr>
				<tr>
					<td><input type="text" name="city" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value='${customer.getDefAddr().city}'/></td>
				</tr>
				<tr>
					<td><input type="text" name="state" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value='${customer.getDefAddr().getState()}'/></td>
				</tr>
				<tr>
					<td><input type="text" name="zip" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value='${customer.getDefAddr().getZip()}'/></td>
				</tr>
				<tr>
					<td><input type="text" name="email" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value='${customer.getEmail()}'/></td>
				</tr>
				<tr>
					<td><input type="text" name="userName" onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value='${customer.getUsername()}'/></td>
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
				<input type="submit" value="Update"/>
				<input type="reset" value="Reset" /><br><br>
				<a href="ShowAllProducts.do">Cancel</a><br><br>
				<a href ="DeleteCustomer.do">Delete Account</a><br><br>
				
				
			</div>
		</form>		
	</div>
	<c:import url="/WEB-INF/footer.html" />