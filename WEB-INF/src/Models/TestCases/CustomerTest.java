package Models.TestCases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Models.StoreModels.Customer;

public class CustomerTest {

	private Customer customer1;
	private Customer customer2;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCustomer() {
		customer1 = null;
		assertNull(customer1);
		
		customer1 = new Customer();
		assertNotNull(customer1);
	}

	@Test
	public void testValidateLogin() {
		boolean success;
		customer1 = new Customer();
		success = customer1.validateLogin(null, "valid");
		assertFalse(success);
		
		success = customer1.validateLogin("valid", null);
		assertFalse(success);
		
		success = customer1.validateLogin("Username", "valid");
		assertFalse(success);
		
		success = customer1.validateLogin("valid", "Enter Password");
		assertFalse(success);
		
		success = customer1.validateLogin("", "valid");
		assertFalse(success);
		
		success = customer1.validateLogin("valid", "");
		assertFalse(success);
		
		success = customer1.validateLogin("   ", "valid");
		assertFalse(success);
		
		success = customer1.validateLogin("valid", "      ");
		assertFalse(success);
		
		success = customer1.validateLogin("valid", "valid");
		assertTrue(success);
	}

	@Test
	public void testValidate() {
		
		customer1 = new Customer();
		ArrayList<String> falseOption = new ArrayList<String>();
		falseOption.add(null);
		falseOption.add("    ");
		falseOption.add("");
		falseOption.add("++___..:**&()(&^%$#@!");
		
		for (String opt : falseOption) {
			customer2 = customer1.validate(opt, null, null, null, null);
			assertNull(customer2);
			
			customer2 = customer1.validate("valid,. ", opt, null, null, null);
			assertNull(customer2);
			
			customer2 = customer1.validate("valid,.", "email@email.com", opt, null, null);
			assertNull(customer2);
			
			customer2 = customer1.validate("valid,.", "email@email.com", "valid.user", opt, null);
			assertNull(customer2);
			
			customer2 = customer1.validate("valid,.", "email@email.com", "valid.user", "validPassword", opt);
			assertNull(customer2);
		}		
		
		customer2 = customer1.validate("Name", "email@email.com", "valid.user", "validPassword", "validPassword");
		assertNull(customer2);
		
		customer2 = customer1.validate("valid,.", "E-mail Address", "valid.user", "validPassword", "validPassword");
		assertNull(customer2);
		
		customer2 = customer1.validate("valid,.", "email@email.com", "Username", "validPassword", "validPassword");
		assertNull(customer2);
		
		customer2 = customer1.validate("valid,.", "email@email.com", "valid.user", "Enter Password", "validPassword");
		assertNull(customer2);
		
		customer2 = customer1.validate("valid,.", "email@email.com", "valid.user", "validPassword", "Re-enter Password");
		assertNull(customer2);
		
		customer2 = customer1.validate("valid,.", "email@email.com", "valid.user", "validPassword", "goodPassword");
		assertNull(customer2);
		
		customer2 = customer1.validate("valid,.", "email@email.com", "valid.user", "validPassword", "validPassword");
		assertNotNull(customer2);
		
		falseOption.clear();
	}

	@Test
	public void testSetShippingRate() {
		customer1 = new Customer();
		
		assertFalse(customer1.setShippingRate(null));
		assertTrue(customer1.setShippingRate(new BigDecimal(5.00)));
		assertTrue(customer1.setShippingRate(new BigDecimal(0.00)));
	}
}
