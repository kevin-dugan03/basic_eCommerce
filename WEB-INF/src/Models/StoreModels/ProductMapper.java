package Models.StoreModels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Models.DBConnMgr;

/**
 * Does all the database work for the Products
 * @author KDugan
 *
 */
public class ProductMapper {
	
	/**
	 * ProductDAO attributes
	 */
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet result;
	private DBConnMgr dbMgr;
	Product product;
	//List<Product> products;
	Map<Integer, Product> products;
	
	/**
	 * Implicit Constructor
	 */
	public ProductMapper() {
		conn = null;
		ps = null;
		result = null;
		dbMgr = new DBConnMgr();
		product = new Product();
		//products = new ArrayList<Product>();
		products = new HashMap<Integer, Product>();
	}
	
	/**
	 * Attempts to get the featured items from the database for the home page.
	 * @return List<Product> A list of featured products
	 * @throws SQLException
	 */
	//public List<Product> getAllProducts() throws SQLException{
	public Map<Integer, Product> getAllProducts() throws SQLException {	
		String getProducts = "SELECT * FROM Product";
		
		try {
			conn = dbMgr.connection();
			
			ps = conn.prepareStatement(getProducts);
			result = ps.executeQuery();
			
			while (result.next()) {
				product = new Product(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4), 
						result.getBigDecimal(5), result.getInt(6), result.getInt(7), result.getString(8));
				products.put(product.getProdID(), product);
				//products.add(product);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		
		return products;
	}
	
	/**
	 * Attempts to get only the laptops from the database.
	 * @return List<Product> The list of laptops
	 * @throws SQLException
	 */
	//public List<Product> getLaptops() throws SQLException {
	public Map<Integer, Product> getLaptops() throws SQLException {
		String laptops = "SELECT * FROM Product WHERE prodCategory=1";
		
		try {
			conn = dbMgr.connection();
			
			ps = conn.prepareStatement(laptops);
			result = ps.executeQuery();
			
			while (result.next()) {
				product = new Product(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4), 
						result.getBigDecimal(5), result.getInt(6), result.getInt(7), result.getString(8));
				//products.add(product);
				products.put(product.getProdID(), product);

			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		
		return products;
	}
	
	/**
	 * Attempts to get the list of Ultrabooks from the database
	 * @return List<Product> The list of ultrabooks.
	 * @throws SQLException
	 */
	//public List<Product> getUltrabooks() throws SQLException {
	public Map<Integer, Product> getUltrabooks() throws SQLException {	
		String laptops = "SELECT * FROM Product WHERE prodCategory=2";
		
		try {
			conn = dbMgr.connection();
			
			ps = conn.prepareStatement(laptops);
			result = ps.executeQuery();
			
			while (result.next()) {
				product = new Product(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4), 
						result.getBigDecimal(5), result.getInt(6), result.getInt(7), result.getString(8));
				//products.add(product);
				products.put(product.getProdID(), product);

			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		
		return products;
	}
	
	/**
	 * Attempts to get the list of Tablets from the database
	 * @return List<Product> The list of tablets.
	 * @throws SQLException
	 */
	//public List<Product> getTablets() throws SQLException {
		public Map<Integer, Product> getTablets() throws SQLException {
		String laptops = "SELECT * FROM Product WHERE prodCategory=3";
		
		try {
			conn = dbMgr.connection();
			
			ps = conn.prepareStatement(laptops);
			result = ps.executeQuery();
			
			while (result.next()) {
				product = new Product(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4), 
						result.getBigDecimal(5), result.getInt(6), result.getInt(7), result.getString(8));
				//products.add(product);
				products.put(product.getProdID(), product);

			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		
		return products;
	}
	
	/**
	 * Attempts to find a specific product from the database.
	 * @param id The product ID to find
	 * @return Product The product from the database
	 * @throws SQLException
	 */
	public Product find(int id) throws SQLException {
		
		String find = "SELECT * FROM Product WHERE productID=?";
		Product product = null;
		
		try {
			conn = dbMgr.connection();
			
			ps = conn.prepareStatement(find);
			ps.setInt(1, id);
			result = ps.executeQuery();
			
			while (result.next()) {
				product = new Product(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4), 
						result.getBigDecimal(5), result.getInt(6), result.getInt(7), result.getString(8));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		
		return product;
	}
}
