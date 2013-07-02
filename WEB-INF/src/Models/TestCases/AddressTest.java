package Models.TestCases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Test;

import Models.StoreModels.Address;

public class AddressTest {
	
	private Address address;
	private Address address2;

	@Test
	public void testValidateAddress() {
		
		address = new Address();
		ArrayList<String> falseOption = new ArrayList<String>();
		falseOption.add(null);
		falseOption.add("    ");
		falseOption.add("");
		falseOption.add("++___..:**&()(&^%$#@!");
		
		for (String opt : falseOption) {
			address2 = address.validateAddress(opt, null, null, null);
			assertNull(address2);
			
			address2 = address.validateAddress("123 valid,.-_, &#()\\/:'\" ", opt, null, null);
			assertNull(address2);
			
			address2 = address.validateAddress("123 valid,.-_, &#()\\/:'\" ", "123 valid,.-_, &#()\\/:'\" ", opt, null);
			assertNull(address2);
			
			address2 = address.validateAddress("123 valid,.-_, &#()\\/:'\" ", "123 valid,.-_, &#()\\/:'\" ", "123 valid,.-_, &#()\\/:'\" ", opt);
			assertNull(address2);
		}		
		
		address2 = address.validateAddress("Street", "123 valid,.-_, &#()\\/:'\" ", "123 valid,.-_, &#()\\/:'\" ", "12345");
		assertNull(address2);
		
		address2 = address.validateAddress("123 valid,.-_, &#()\\/:'\" ", "State", "123 valid,.-_, &#()\\/:'\" ", "12345");
		assertNull(address2);
		
		address2 = address.validateAddress("123 valid,.-_, &#()\\/:'\" ", "123 valid,.-_, &#()\\/:'\" ", "City", "12345");
		assertNull(address2);
		
		address2 = address.validateAddress("123 valid,.-_, &#()\\/:'\" ", "123 valid,.-_, &#()\\/:'\" ", "123 valid,.-_, &#()\\/:'\" ", "invalid");
		assertNull(address2);
		
		address2 = address.validateAddress("123 W. Main St.", "Colorado Springs", "CO", "98888");
		assertNotNull(address2);
		
	}

}
