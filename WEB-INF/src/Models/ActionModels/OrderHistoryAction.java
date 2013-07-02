package Models.ActionModels;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import Models.StoreModels.Customer;
import Models.StoreModels.Order;
import Models.StoreModels.OrderMapper;

/**
 * Retrieves the order history for a customer.
 * @author KDugan
 */
public class OrderHistoryAction extends ActionCommand{

	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private Customer customer;
	private OrderMapper orders;
	
	//Constructor
	public OrderHistoryAction() {
		orders = new OrderMapper();
	}
	
	/**
	 * Retrieves the order history from the OrderMapper.
	 */
	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		//method variables
		session = request.getSession();
		customer = (Customer) session.getAttribute("customer");
				
		//retrieve the history of the logged in customer
		try {
			ArrayList<Order> orderList = orders.viewHistory(customer.getUserID());
			session.setAttribute("orderList", orderList);
			
			forward(actionMap.getSuccess());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
