package Models.ActionModels;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;

import Models.StoreModels.Customer;
import Models.StoreModels.Order;

public class VerifyOrderAction extends ActionCommand{

	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private Customer customer;
	private Order cart;
	
	//Constructor
	public VerifyOrderAction() {
		customer = null;
		cart = null;
	}
	
	/**
	 * Takes the customer to the order verification page prior to placing the order.
	 * The user must be logged in to access the verfication page.
	 */
	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		
		//method variables
		session = request.getSession();
		customer = (Customer) session.getAttribute("customer");
		cart = (Order) session.getAttribute("cart");
		
		if (customer == null) {
			request.setAttribute("cartMsg", "You must log in before placing order");
			forward(actionMap.getFailure());
		} else if (cart == null) {
			request.setAttribute("cartMsg", "Cart cannot be empty");
			forward(actionMap.getFailure());
		} else {
			//calculate shipping rate and order total
			BigDecimal shipRate = customer.getShippingRate();
			cart.updateTotal(shipRate);

			forward(actionMap.getSuccess());
			
		}
	}
}
