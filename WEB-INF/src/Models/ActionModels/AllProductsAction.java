package Models.ActionModels;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;

import Models.StoreModels.Product;
import Models.StoreModels.ProductMapper;

/**
 * Model that retrieves all products from the database. They will 
 * be used to display on the home page.
 * @author Kevin Dugan
 *
 */
public class AllProductsAction extends ActionCommand {

	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private ProductMapper pMap;
	private Map<Integer, Product> pList;
	
	/**
	 * Constructor for AllProductsAction. 
	 */
	public AllProductsAction() {
		pMap = new ProductMapper();
	}

	/**
	 * Processes the request by retrieving all the products from the database and 
	 * placing them into a List.
	 * @param actionMap The configuration object.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void process(ActionMapping actionMap) throws ServletException, IOException {
		
		session = request.getSession();
		pList = (Map<Integer, Product>) session.getAttribute("productList");
		
		if (pList == null || pList.size() < 19) {
			//If the products have not already been loaded, pull from database		
			try {
				pList = pMap.getAllProducts();

				//If the list isn't empty, create a session object with all the products.
				if (pList.size() > 0) {
					session.setAttribute("productList", pList);
					forward(actionMap.getSuccess());
				} else {
					forward(actionMap.getFailure());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			forward(actionMap.getSuccess());
		}
	}
}
