package Models.ActionModels;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;

import Models.StoreModels.Customer;

/**
 * Deletes customer account, which also deletes their order history.
 * @author KDugan
 */
public class DeleteCustomerAction extends ActionCommand {

	private static final long serialVersionUID = 1L;


	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		
		Customer customer;
		
		customer = (Customer) session.getAttribute("customer");
		boolean success = false;
		
		//remove the customer from the database
		try {
			success = customer.delete();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//invalidate the session if successful
		if (success) {
			session.invalidate();
			forward (actionMap.getSuccess());
		} else {
			forward(actionMap.getFailure());
		}
	}
}
