//Logout function
function logoutAlert() {
	alert("Logout successful/unsuccessful");
}

//Resets Registration form
function resetForm(form) {
    form.reset();
    
    //Reset password
    document.getElementById('pwordBox1').type='text';
	document.getElementById('pwordBox1').value='Enter Password';
	
	//Reset password re-entry
	document.getElementById('pwordBox2').type='text';
	document.getElementById('pwordBox2').value='Re-enter Password';
}

//Change password field from text to password
function changePass(element) {
	document.getElementById(element.id).type='password';
	document.getElementById(element.id).value='';
	
}

//Change password field from password to text
function changeText(element) {
	if (element.id == 'pwordBox1' || element.id == 'logPassword') {
		if (document.getElementById(element.id).value=='') {
			document.getElementById(element.id).type='text';
			document.getElementById(element.id).value='Enter Password';
		}
	}
	else {
		if (document.getElementById(element.id).value=='') {
			document.getElementById(element.id).type='text';
			document.getElementById(element.id).value='Re-enter Password';
		}
	}
}

//Validates log in information
function logInCheck(form) {
	if (form.logUsername.value == "Username") {
		alert ("Username field cannot be empty.");
		form.username.focus();
		return;
	}
	else if (form.logPassword.value == "Enter Password") {
		alert ("Password field cannot be empty.");
		form.password.focus();
		return;
	}
	
	form.submit();
}


//validates user input on registration form
function regValidation(form)
{
    if (form.name.value == "Name" || !(/^[a-z A-Z,. ]+$/.test(form.name.value)) || form.name.value == ' ')
    {
        alert("NAME field cannot be empty and cannot contain numbers.");
        form.name.focus();
        return;
    }
    else if (form.street.value == "Street Address" || form.street.value == ' ')
    {
        alert("STREET field cannot be empty.");
        form.street.focus();
        return;

    }
    else if (form.city.value == "City" || form.city.value == ' ')
    {
        alert("CITY field cannot be empty.");
        form.city.focus();
        return;

    }
    else if (form.state.value == "State" || !(form.state.value.length == 2) || form.state.value == ' ')
    {
        alert("STATE field cannot be empty and must be 2 character abbreviation.");
        form.state.focus();
        return;

    }
    else if (form.zip.value == "Zip Code" || !(/(^\d{5}$)/.test(form.zip.value)) || form.zip.value == ' ')
    {
        alert("ZIP CODE field cannot be empty and must be 5 digits.");
        form.zip.focus();
        return;

    }
    else if (form.email.value == "E-mail Address" || !(/\S+@\S+\.\S+/.test(form.email.value)) || form.email.value == ' ')
    {
        alert("E-MAIL ADDRESS field cannot be empty and must be in proper format");
        form.email.focus();
        return;

    }
    else if (form.userName.value == "Username" || form.userName.value == ' ')
    {
    	alert("USERNAME field cannot be empty.");
        form.userName.focus();
        return;
    }
    else if (form.password1.value == "Enter Password" || form.password1.value == ' ')
    {
    	alert("PASSWORD field cannot be empty");
    	form.password1.focus();
    	return;
    }
    else if (form.password2.value == "Re-enter Password" || form.password2.value == ' ')
    {
    	alert("PASSWORD RE-ENTRY field cannot be empty");
    	form.password2.focus();
    	return;
    }
    else if (form.password1.value != form.password2.value)
    {
    	alert("Passwords do not match.");
    	form.password2.focus();
    	return;
    }
    
    form.submit();
}

//Makes sure a person wants to delete their account
function deleteAcct(form) {
	if (confirm("Are you sure you want to delete?")) {
		
		form.submit();
	}
}

//Checks if a customer is logged in already
function checkLoggedIn(form) {
	if (form.hdCustValue.value == "") {
		alert("Please log in first");
		document.getElementById("username").focus();
		return;
	}
	else if (form.emptyCart == true){
		alert("Cannot submit order with empty cart!");
		return;	
	} else {
		form.submit();
	}
}

//Checks if the quantity of an item the user wants to add isn't 0
function checkQuantity(form) {
	if (form.prodQuantity.value == 0) {
		alert("Quantity must be greater than 0.");
		form.prodQuantity.focus();
		return;
	} 
	
	form.submit();
}

//Validates the checkout information
function checkoutValidation(form) {
	
	if (!validatePayment(form)) {
		return;
	}
	
	if (!validateShipping(form)) {
		return;
	}
	
	if (!validateBilling(form)) {
		return;
	}
    
    form.submit();
}

//Validates payment information
function validatePayment(form) {
	if (form.nameOnCard.value == "Name on Card" || !(/^[a-z A-Z,. ]+$/.test(form.nameOnCard.value)) || form.nameOnCard.value == ' ')
    {
        alert("NAME field cannot be empty and cannot contain numbers.");
        form.nameOnCard.focus();
        return false;
    }
	else if (isNaN(form.cardNumber.value) || form.cardNumber.value == "Card Number (no dashes)" || form.cardNumber.value == ' ') 
	{
		alert ("Card Number cannot be empty and must contain only numbers");
		form.cardNumber.focus();
		return false;
	}
	else if (form.cardExpDate.value == "Exp Date (mm/yy)")
	{
		alert ("Expiration date must be in the correct format and cannot be empty.");
		form.cardExpDate.focus();
		return false;
	}
	else if (form.cardCSC.value == "Card Security Code" || form.cardCSC.value == ' ') 
	{
		alert ("Card CSC valud cannot be empty.");
		form.cardCSC.focus();
		return false;
	}
	
	return true;
}


//Validate shipping information
function validateShipping(form) {
	if (form.shippingName.value == "Name" || !(/^[a-z A-Z,. ]+$/.test(form.shippingName.value)) || form.name.value == ' ')
    {
		 alert("NAME field cannot be empty and cannot contain numbers.");
        form.shippingName.focus();
        return false;
    }
    else if (form.shippingStreet.value == "Street Address" || form.shippingStreet.value == ' ')
    {
        alert("STREET field cannot be empty.");
        form.shippingStreet.focus();
        return false;

    }
    else if (form.shippingCity.value == "City" || form.shippingCity.value == ' ')
    {
        alert("CITY field cannot be empty.");
        form.shippingCity.focus();
        return false;

    }
    else if (form.shippingState.value == "State" || !(form.shippingState.value.length == 2) || form.shippingState.value == ' ')
    {
        alert("STATE field cannot be empty and must be 2 character abbreviation.");
        form.shippingState.focus();
        return false;

    }
    else if (form.shippingZip.value == "Zip Code" || !(/(^\d{5}$)/.test(form.shippingZip.value)) || form.shippingZip.value == ' ')
    {
        alert("ZIP CODE field cannot be empty and must be 5 digits.");
        form.shippingZip.focus();
        return false;

    }
	
	return true;
	
}

//Checks billing information
function validateBilling(form) {
	if (form.billingName.value == "Name" || !(/^[a-z A-Z,. ]+$/.test(form.billingName.value)) || form.billingName.value == ' ')
    {
		alert("NAME field cannot be empty and cannot contain numbers.");
        form.billingName.focus();
        return false;
    }
    else if (form.billingStreet.value == "Street Address" || form.billingStreet.value == ' ')
    {
        alert("STREET field cannot be empty.");
        form.billingStreet.focus();
        return false;

    }
    else if (form.billingCity.value == "City" || form.billingCity.value == ' ')
    {
        alert("CITY field cannot be empty.");
        form.billingCity.focus();
        return false;

    }
    else if (form.billingState.value == "State" || !(form.billingState.value.length == 2) || form.billingState.value == ' ')
    {
        alert("STATE field cannot be empty and must be 2 character abbreviation.");
        form.billingState.focus();
        return false;

    }
    else if (form.billingZip.value == "Zip Code" || !(/(^\d{5}$)/.test(form.billingZip.value)) || form.billingZip.value == ' ')
    {
        alert("ZIP CODE field cannot be empty and must be 5 digits.");
        form.billingZip.focus();
        return false;

    }
	
	return true;
	
}