package Models.ActionModels;

import java.io.IOException;

import javax.servlet.ServletException;

import Models.StoreModels.Order;
import Models.StoreModels.OrderItem;
import Models.StoreModels.Product;

/**
 * Adds a specified product to the session's cart.
 * @author Kevin Dugan
 *
 */
public class CartAddAction extends ActionCommand {
	
	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private Order cart;
	
	//Constructor.
	public CartAddAction() {
		cart = null;
	}
	
	/**
	 * Processes the request to add a product to the cart. If the item
	 * is already in the cart, it updates the item's quantity.
	 */
	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		
		session = request.getSession();
		
		//If no cart exists, make one.
		if (session.getAttribute("cart") == null) {
			cart = new Order();
			
		} else {
			cart = (Order) session.getAttribute("cart");
		}
		
		//Get the product and quantity to be added.
		Product prod = (Product) session.getAttribute("product");
		int amt = Integer.parseInt(request.getParameter("prodQuantity"));
		
		//Look for duplicates, if found, update the item already in the cart
		if (cart.findDuplicate(prod.getProdID())) {
			cart.updateItem(prod.getProdID(), amt);
			cart.updateSubTotal();
		} else { //insert new item into the cart
			OrderItem cItem = new OrderItem(prod, amt);
			cart.getItemList().add(cItem);			
			cart.updateSubTotal();
		}
		
		//re-establish the cart with new data
		session.setAttribute("cart", cart);
		forward(actionMap.getSuccess());
	}
}
