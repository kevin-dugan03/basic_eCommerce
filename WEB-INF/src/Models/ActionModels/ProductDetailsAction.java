package Models.ActionModels;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;

import Models.StoreModels.Product;
import Models.StoreModels.ProductMapper;

/**
 * Gets the product details for a specific product.
 * @author KDugan
 *
 */
public class ProductDetailsAction extends ActionCommand {

	private static final long serialVersionUID = 1L;
	
	//Class attributes.
	private Product product;
	private ProductMapper pMap;
	
	//Constructor
	public ProductDetailsAction() {
		product = null;
		pMap = new ProductMapper();
	}

	/**
	 * Processes the retrieval of product details.
	 */
	@Override
	public void process(ActionMapping actionMap) throws ServletException,
			IOException {
		
		session = request.getSession();
		int pid = Integer.parseInt(request.getParameter("pid"));
		
		//if the product list is null, then will have to retrieve the product from the database
		if (session.getAttribute("productList") == null) {
			try {
				product = pMap.find(pid);
				
				if (product != null) {
					session.setAttribute("product", product);
					forward(actionMap.getSuccess());
				} else {
					forward(actionMap.getFailure());
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else { //if the product list is not null, look in the list for the product. Saves a trip to the database.
			@SuppressWarnings("unchecked")
			Map<Integer, Product> prods = (Map<Integer, Product>) session.getAttribute("productList");
			session.setAttribute("product", prods.get(pid));
			Product prod = prods.get(pid);
			
			if (prod == null) {
				forward(actionMap.getFailure());
			} else {
				session.setAttribute("product", prod);
				forward(actionMap.getSuccess());
			}
		}
	}
}
