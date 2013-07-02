package Models.ActionModels;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import Models.StoreModels.Product;
import Models.StoreModels.ProductMapper;

/**
 * Handles retrieving the appropriate product category per user request.
 * @author KDugan
 */
public class ProductCategoryAction extends ActionCommand {

	private static final long serialVersionUID = 1L;
	
	//Class attributes
	private ProductMapper pMap;
	private Map<Integer, Product> pList;
	
	//Constructor
	public ProductCategoryAction() {
		pMap = new ProductMapper();
		pList = new HashMap<Integer, Product>();
	}
	
	/**
	 * Processes the
	 */
	@Override
	public void process(ActionMapping actionMap) throws ServletException, IOException {
		
		session = request.getSession();
		String category = actionMap.getParam();
		
		switch (category.toUpperCase()) {
			case "L": 
				getLaptops();
				forward(actionMap.getSuccess());
				break;
			case "U":
				getUltrabooks();
				forward(actionMap.getSuccess());
				break;
			case "T":
				getTablets();
				forward(actionMap.getSuccess());
				break;
			default:
				ActionCommand allProducts = new AllProductsAction();
				allProducts.process(actionMap);
				break;
		}
	}
	
	/**
	 * Gets the laptops from the productMapper
	 */
	public void getLaptops() {

		try {
			pList = pMap.getLaptops();
			
			if (pList.size() > 0) {
				session.setAttribute("productList", pList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
	
	/**
	 * Gets the ultrabooks from the productMapper
	 */
	public void getUltrabooks() {
		try {
			pList = pMap.getUltrabooks();
			
			if (pList.size() > 0) {
				session.setAttribute("productList", pList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the tablets from the productMapper
	 */
	public void getTablets() {
		try {
			pList = pMap.getTablets();
			
			if (pList.size() > 0) {
				session.setAttribute("productList", pList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
