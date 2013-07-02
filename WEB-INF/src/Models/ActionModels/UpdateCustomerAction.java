package Models.ActionModels;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import Models.StoreModels.Address;
import Models.StoreModels.Customer;

/**
 * Controls the action to update customer information.
 * @author KDugan
 */
public class UpdateCustomerAction extends ActionCommand {
	
	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private Customer customer;
	
	/**
	 * Processes request to update customer.
	 */
	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		
		//
		int id = -1;
		boolean success = false;
		
		//get the current customer information prior to updated info.
		customer = (Customer) session.getAttribute("customer");
		id = customer.getUserID();
		
		//Get a new customer object based on new user input
		Customer customer2 = getNewCustomer(request);
		
		//If unable to get new customer object, something in the request failed.
		if ( customer2 == null) {
			request.setAttribute("regConfirmMsg", "Update failed.");
			forward(actionMap.getFailure());
		} else {
			
			//set the new customer object with the current customer id.
			customer2.setUserID(id);

			//call the update method on the new customer.
			try {
				success = customer2.update();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (!success) {
				request.setAttribute("regConfirmMsg", "Update failed.");
				forward(actionMap.getFailure());
			} else {
				request.setAttribute("regConfirmMsg", "Your information has been updated");
				session.setAttribute("customer", customer2);
				forward(actionMap.getSuccess());
			}
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
		
		//validate the address
		addr = addr.validateAddress(request.getParameter("street"), request.getParameter("city"),
				request.getParameter("state"), request.getParameter("zip"));
		
		//Validate customer information
		customer = customer.validate(request.getParameter("name"), 
				request.getParameter("email"), request.getParameter("userName"), 
				request.getParameter("password1"), request.getParameter("password2"));

		if (addr == null || customer == null) {
			return null;
		}
		
		//if all information is valid, build new customer object.
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
