package Models.ActionModels;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;

import Models.StoreModels.Address;
import Models.StoreModels.Customer;
import Models.StoreModels.Email;
import Models.StoreModels.Order;
import Models.StoreModels.OrderMapper;
import Models.StoreModels.Payment;

/**
 * Places orders for the customers. First calls validation methods, and then calls
 * method from OrderMapper to place the order. 
 * @author KDugan
 *
 */
public class PlaceOrderAction extends ActionCommand {

	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private Order order;
	private OrderMapper placeOrder;
	private Customer customer;
	private Payment payment;
	private Address shipAddr;
	private Address billAddr;
	
	//Constructor
	public PlaceOrderAction() {
		placeOrder = new OrderMapper();
	}
	
	/**
	 * Processes the order request. 
	 */
	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		
		//Gets the current state of the cart and the current customer.
		session = request.getSession();
		order = (Order) session.getAttribute("cart");
		customer = (Customer) session.getAttribute("customer");
				
		//Validates the payment, shipping address, and billing address info.
		payment = new Payment().validatePayment((String)request.getParameter("nameOnCard"), (String)request.getParameter("ccInfo"), (String)request.getParameter("cardNumber"),
				(String)request.getParameter("cardExpDate"), (String)request.getParameter("cardCSC"));
		shipAddr = new Address().validateAddress((String)request.getParameter("shippingStreet"), (String)request.getParameter("shippingCity"), 
				(String)request.getParameter("shippingState"), (String)request.getParameter("shippingZip"));
		billAddr = new Address().validateAddress((String)request.getParameter("billingStreet"), (String)request.getParameter("billingCity"), 
				(String)request.getParameter("billingState"), (String)request.getParameter("billingZip"));
		
		//Sends back error messages if user input problem, otherwise attempts to process order.
		if (payment == null) {
			System.out.println("Payment is null");
			request.setAttribute("paymentError", "There was an error with the payment information.");
			forward(actionMap.getFailure());

		} else if (shipAddr == null) {
			request.setAttribute("paymentError", "There was an error with the shipping address information.");
			forward(actionMap.getFailure());

		} else if (billAddr == null) {
			request.setAttribute("paymentError", "There was an error with the shipping address information.");
			forward(actionMap.getFailure());

		} else {
			//attempt to process the order
			try {
				order = placeOrder.processOrder(customer, order, payment);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//if the order was processed, reset session objects
			if (order != null) {
				session.setAttribute("order", order);
				session.setAttribute("cart", null);
				session.setAttribute("product", null);
				request.setAttribute("orderConfirm", "Thank you for your order.");
				
				//Send confirmation email.
				Email email = new Email(customer);
				try {
					email.sendOrderConfirm();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				
				forward(actionMap.getSuccess());		
			} else {
				request.setAttribute("confirmErrMsg", "There was an error processing your order.");
				forward(actionMap.getFailure());
			}			
		}
	}
}
