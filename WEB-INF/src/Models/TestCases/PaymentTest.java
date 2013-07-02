package Models.TestCases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Test;

import Models.StoreModels.Payment;

public class PaymentTest {
	
	private Payment payment;
	private Payment payment2;

	@Test
	public void testValidatePayment() {
		payment = new Payment();
		
		ArrayList<String> falseOption = new ArrayList<String>();
		falseOption.add(null);
		falseOption.add("    ");
		falseOption.add("");
		
		for (String opt : falseOption) {
			payment2 = payment.validatePayment(opt, null, null, null, null);
			assertNull(payment2);
			
			payment2 = payment.validatePayment("Mr. Valid Name,.Jr III", opt, null, null, null);
			assertNull(payment2);
			
			payment2 = payment.validatePayment("Mr. Valid Name,.Jr III", "Visa Mastercard AMEX Discover", opt, null, null);
			assertNull(payment2);
			
			payment2 = payment.validatePayment("Mr. Valid Name,.Jr III", "Visa Mastercard AMEX Discover", "1111222233334444", opt, null);
			assertNull(payment2);
			
			payment2 = payment.validatePayment("Mr. Valid Name,.Jr III", "Visa Mastercard AMEX Discover", "1111222233334444", "12/14", opt);
			assertNull(payment2);	
		}		
		
		payment2 = payment.validatePayment("Name on Card", "Visa Mastercard AMEX Discover", "1111222233334444", "12/14", "111");
		assertNull(payment2);
		
		payment2 = payment.validatePayment("Mr. Valid Name,.Jr III", "Visa Mastercard AMEX Discover", "1111-1111-1111-1111", "12/14", "111");
		assertNull(payment2);
		
		payment2 = payment.validatePayment("Mr. Valid Name,.Jr III", "Visa Mastercard AMEX Discover", "1111.1111.1111/1111", "12/14", "111");
		assertNull(payment2);
		
		payment2 = payment.validatePayment("Mr. Valid Name,.Jr III", "Visa Mastercard AMEX Discover", "not a number", "12/14", "111");
		assertNull(payment2);
		
		payment2 = payment.validatePayment("Mr. Valid Name,.Jr III", "Visa Mastercard AMEX Discover", "1111222233334444", "Exp Date (mm/yy)", "111");
		assertNull(payment2);
		
		payment2 = payment.validatePayment("Mr. Valid Name,.Jr III", "Visa Mastercard AMEX Discover", "1111222233334444", "12/14", "Card Security Code");
		assertNull(payment2);
		
		payment2 = payment.validatePayment("Mr. Valid Name,.Jr III", "Visa Mastercard AMEX Discover", "1111222233334444", "12/14", "Not a number");
		assertNull(payment2);
		
		payment2 = payment.validatePayment("Mr. Valid Name,.Jr III", "Visa Mastercard AMEX Discover", "1111222233334444", "12/14", "233");
		assertNotNull(payment2);
	}
}
