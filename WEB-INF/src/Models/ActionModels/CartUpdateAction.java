package Models.ActionModels;

import java.io.IOException;

import javax.servlet.ServletException;

import Models.StoreModels.Order;

/**
 * Updates the cart as per the client request. Either updates quantity
 * or removes product, depending on client request.
 * @author Kevin Dugan
 *
 */
public class CartUpdateAction extends ActionCommand{

	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private Order cart;
	
	//Constructor
	public CartUpdateAction() {
		cart = null;
	}
	
	/**
	 * Processes the request by updating the quantity of an item in the cart.
	 */
	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		
		session = request.getSession();
		
		int qty = Integer.parseInt(request.getParameter("prodQuantity"));
		int id = Integer.parseInt(request.getParameter("prodID"));

		cart = (Order) session.getAttribute("cart");
		
		//update the Order obj
		if (qty == 0) {
			cart.deleteItem(id);
			request.setAttribute("cartMsg", "Your cart has been updated.");
		} else if (cart.updateItem(id,qty)) {
			session.setAttribute("cart", cart);
			request.setAttribute("cartMsg", "Your cart has been updated.");
		} else {
			forward(actionMap.getFailure());
		}

		forward(actionMap.getSuccess());
	}
}
