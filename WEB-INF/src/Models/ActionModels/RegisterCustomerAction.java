package Models.ActionModels;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import Models.StoreModels.Address;
import Models.StoreModels.Customer;
import Models.StoreModels.Email;

/**
 * Controls the action to register a customer.
 * @author KDugan
 */
public class RegisterCustomerAction extends ActionCommand{

	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private Customer customer;

	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {

		int success = 0;
		customer = getNewCustomer(request);
		
		if (customer != null) {
			try {
				success = customer.insert();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//If registration failed, it was due to duplicate information
			if (success == 0) {
				//Duplicate message
				request.setAttribute("regConfirmMsg", "The username entered has already been registered.");
				
				//Send back to registration page
				forward(actionMap.getFailure());
			}
			else {
				//Otherwise, registration was successful
				session.setAttribute("customer", customer);
				Email email = new Email(customer);
				
				//send the confirmation email
				try {
					email.sendRegisterConfirm();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				
				request.setAttribute("regConfirmMsg", "Thank you for registering");
				
				//Send to Customer's new profile page
				forward(actionMap.getSuccess());
			}
		} else {
			//Otherwise, registration was successful
			request.setAttribute("regConfirmMsg", "Invalid information was entered. Please try again.");
			
			//Send to Customer's new profile page
			forward(actionMap.getFailure());
		}
	}
		
		/**
		 * Forms a new customer with parameters from user input
		 * @param request the ServletRequest
		 * @return Customer the new customer
		 */
		public Customer getNewCustomer(HttpServletRequest request) {
			Customer customer = new Customer();
			Address addr = new Address();
			
			//Validate address information
			addr = addr.validateAddress(request.getParameter("street"), request.getParameter("city"),
					request.getParameter("state"), request.getParameter("zip"));
			
			//Validate customer information
			customer = customer.validate(request.getParameter("name"), 
					request.getParameter("email"), request.getParameter("userName"), 
					request.getParameter("password1"), request.getParameter("password2"));

			if (addr == null || customer == null) {
				return null;
			}
			
			//if valid information, set up the new objects.
			addr.setStreet(request.getParameter("street"));
			addr.setCity(request.getParameter("city"));
			addr.setState(request.getParameter("state"));
			addr.setZip(Integer.parseInt(request.getParameter("zip")));
			
			customer.setName(request.getParameter("name"));
			customer.setEmail(request.getParameter("email"));
			customer.setUsername(request.getParameter("userName"));
			customer.setPassword(request.getParameter("password1"));
			customer.setDefAddr(addr);
			
			return customer;
			
		}
}
