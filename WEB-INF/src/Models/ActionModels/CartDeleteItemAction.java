package Models.ActionModels;

import java.io.IOException;

import javax.servlet.ServletException;

import Models.StoreModels.Order;

/**
 * Controls action to delete an item from the cart.
 * @author KDugan
 */
public class CartDeleteItemAction extends ActionCommand {

	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private Order cart;

	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		
		int id = Integer.parseInt(request.getParameter("pid"));
		cart = (Order) session.getAttribute("cart");
		
		//remove the item from the Order obj
		if (cart.deleteItem(id)) {
			session.setAttribute("cart", cart);
			request.setAttribute("cartMsg", "Item has been deleted.");
			forward(actionMap.getSuccess());
		} else {
			request.setAttribute("cartMsg", "Unable to delete, server error.");
			forward(actionMap.getFailure());
		}
		
	}

}
