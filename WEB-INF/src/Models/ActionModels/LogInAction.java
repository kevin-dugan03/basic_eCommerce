package Models.ActionModels;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;

import Models.StoreModels.Customer;

/**
 * Action to log in a registered user.
 * @author Kevin Dugan
 *
 */
public class LogInAction extends ActionCommand {
 
	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private String username;
	private String password;
	private Customer customer;
	
	//Constructor
	public LogInAction() {
		customer = new Customer();
	}
	
	/**
	 * Processes the log in information for a user. Logs the user in if
	 * valid credentials.
	 */
	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		
		//Method variables
		username = request.getParameter("logUsername");
		password = request.getParameter("logPassword");
		session = request.getSession();
		
		//Validate login and password
		if (!customer.validateLogin(username, password)) {
			request.setAttribute("loginErrMsg", "Username or Password is not valid. " +
					"Please try again or register.");
			forward(actionMap.getFailure());
		} else {
			
			//Try to login the customer
			try {
				if (customer.login(username, password)) {
					session.setAttribute("customer", customer);
					session.setAttribute("loginMsg", "Welcome, " + customer.getName());
					forward(actionMap.getSuccess());
				} else {
					request.setAttribute("loginErrMsg", "Username or Password is not valid. " +
							"Please try again or register.");
					forward(actionMap.getFailure());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
