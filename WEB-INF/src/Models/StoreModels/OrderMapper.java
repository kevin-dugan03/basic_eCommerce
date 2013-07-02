package Models.StoreModels;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import Models.DBConnMgr;

/**
 * Handles the logic and database interaction to place and order and
 * retrieve order history.
 * @author KDugan
 */
public class OrderMapper {
	
	/**
	 * OrderHistoryDAO attributes
	 */
	private Order order;
	private ArrayList<Order> orderList;
	private Calendar oDate;
	private Random random;
	private String orderDate;
	
	/**
	 * Implicit Constructor
	 */
	public OrderMapper () {
		order = null;
		orderList = new ArrayList<Order>();
		oDate = Calendar.getInstance();
		orderDate = oDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + " " + 
				oDate.get(Calendar.DAY_OF_MONTH) + ", " + oDate.get(Calendar.YEAR);
		random = new Random();
	}
	
	/**
	 * Attempts to process a customer's order.
	 * @param customer
	 * @return boolean True if processed, false otherwise.
	 * @throws SQLException
	 */
	public Order processOrder(Customer customer, Order order, Payment payment) throws SQLException {
		
		this.order = order;
		order.setOrderDate(orderDate);
		order.setShipDate(getShipDate());
		order.setDeliverDate(getDeliverDate());
		
		//database variables
		Connection conn = null;
		ResultSet result = null;
		DBConnMgr dbMgr = new DBConnMgr();
		
		PreparedStatement newOrder = null;
		PreparedStatement getCount = null;
		PreparedStatement updateItem = null;
		PreparedStatement insertOrderItem = null;
		PreparedStatement updateNum = null;
		PreparedStatement updateStatus = null;
		
		String insertNewOrder = "INSERT INTO `Order` (orderTotal, custID, orderDate, shipDate, deliverDate) VALUES (?,?,?,?,?)";
		String checkItemCount = "SELECT prodStock FROM Product WHERE productID=?";
		String updateItemCount = "UPDATE Product SET prodStock=? WHERE productID=?";
		String itemToOrder = "INSERT INTO OrderItem (orderID, productID, itemQuantity) VALUES (?,?,?)";
		String updateNumOrders = "UPDATE Customer SET custNumOrders=(custNumOrders + 1) WHERE customerID=?";
		String updateCustomerStatus = "UPDATE Customer SET statusID=(statusID + 1) WHERE customerID=?";
		
		int newCount = 0;	//keeps track whether there are enough items to process an order
		
		try {
			conn = dbMgr.connection();
			conn.setAutoCommit(false);
			
			//prepare the SQL statements
			newOrder = conn.prepareStatement(insertNewOrder, PreparedStatement.RETURN_GENERATED_KEYS);
			getCount = conn.prepareStatement(checkItemCount);
			updateItem = conn.prepareStatement(updateItemCount);
			insertOrderItem = conn.prepareStatement(itemToOrder);
			updateNum = conn.prepareStatement(updateNumOrders);
			updateStatus = conn.prepareStatement(updateCustomerStatus);
			

			//make new order
			newOrder.setFloat(1, order.getTotal().floatValue());
			newOrder.setInt(2, customer.getUserID());
			newOrder.setString(3, orderDate);
			newOrder.setString(4, order.getShipDate());
			newOrder.setString(5, order.getDeliverDate());
			newOrder.executeUpdate();
			
			ResultSet res = newOrder.getGeneratedKeys();
			if (res.next()){
				order.setOrderID(res.getInt(1));
			} else {
				return null;
			}
			
			//for all the products in the order
			for (OrderItem oi : order.getItemList()) {
				//get current count of the product from the database
				getCount.setInt(1, oi.getProduct().getProdID());
				result = getCount.executeQuery();
				
				if (result.next()) {
					newCount = result.getInt(1) - oi.getQuantity();
					
					//if there is enough product to order as per the request, process the order
					if (newCount >= 0) {
						//update the orderItem table in the database
						insertOrderItem.setInt(1, order.getOrderID());
						insertOrderItem.setInt(2, oi.getProduct().getProdID());
						insertOrderItem.setInt(3, oi.getQuantity());
						insertOrderItem.executeUpdate();
						
						//update the product's inventory
						updateItem.setInt(1, newCount);
						updateItem.setInt(2, oi.getProduct().getProdID());
						updateItem.executeUpdate();
					} else { //if a step fails, rollback and return false
						conn.rollback();
						return null;
					}
				}
			}
			
			//if the order process was successful, update the customer's number of orders.
			updateNum.setInt(1, customer.getUserID());
			updateNum.executeUpdate();
			customer.incrementNumOrders();
			
			if (customer.getNumOrders() == 5) {
				customer.updateShippingRate();
				updateStatus.setInt(1, customer.getUserID());
				updateStatus.executeUpdate();
			}
			
			if(!payment.processPayment()) {
				conn.rollback();
				return null;
			}
			
			//commit the transaction.
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.setAutoCommit(true);
			conn.close();
		}
		
		return order;
	}
	
	/**
	 * Attempts to retrieve the order history for a specific customer
	 * @param customerID
	 * @return ArrayList The list of orders
	 * @throws SQLException
	 */
	public ArrayList<Order> viewHistory(int customerID) throws SQLException {
		
		//Database connection attributes
		Connection conn = null;
		ResultSet result = null;
		ResultSet result2 = null;
		ResultSet result3 = null;
		DBConnMgr dbMgr = new DBConnMgr();
		
		PreparedStatement getOrders = null;
		PreparedStatement getOrderItems = null;
		PreparedStatement getProducts = null;
		
		String getUserOrders = "SELECT * FROM `Order` WHERE custID=?";
		String getOrderItemInfo = "SELECT * FROM OrderItem WHERE orderID=?";
		String getOrderProducts = "SELECT prodName, imageURL FROM Product WHERE productID=?";
		
		//In-memory objects to be used to retrieve the database information
		Order order = new Order();
		OrderItem oi = new OrderItem();
		Product product = new Product();
		
		try {
			conn = dbMgr.connection();
			conn.setAutoCommit(false);
			getOrders = conn.prepareStatement(getUserOrders);
			getOrderItems = conn.prepareStatement(getOrderItemInfo);
			getProducts = conn.prepareStatement(getOrderProducts);

			//Find all the orders made by the customer
			getOrders.setInt(1, customerID);
			result = getOrders.executeQuery();
			
			while (result.next()){
				//for each order, get the Products ordered in one specific order
				order.setOrderID(result.getInt(1));
				order.setTotal(new BigDecimal(result.getFloat(2)));
				order.setOrderDate(result.getString(4));
				order.setShipDate(result.getString(5));
				order.setDeliverDate(result.getString(6));
				
				getOrderItems.setInt(1, order.getOrderID());
				result2 = getOrderItems.executeQuery();
				
				while(result2.next()) {
					//reset the product and orderItem attributes to receive new data
					product = new Product();
					oi = new OrderItem();
					
					oi.setProduct(product);
					oi.getProduct().setProdID(result2.getInt(2));
					oi.setQuantity(result2.getInt(3));
					
					//get the product data to store in-memory
					getProducts.setInt(1, oi.getProduct().getProdID());
					result3 = getProducts.executeQuery();
					
					if(result3.next()) {
						oi.getProduct().setProdName(result3.getString(1));
						oi.getProduct().setImageURL(result3.getString(2));
						order.getItemList().add(oi);
					}
				}
				//reset the attributes for the next order
				orderList.add(order);
				order = new Order();
			}

			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.setAutoCommit(true);
			conn.close();
		}
		
		return orderList;
	}
	
	/**
	 * Generates the shipping date and sends it back as a string.
	 * @return The shipping date.
	 */
	private String getShipDate() {
		Calendar shipDate = oDate;
		int days = random.nextInt(5) + 1;
		shipDate.add(Calendar.DATE, days);
		
		return (shipDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + " " + 
			shipDate.get(Calendar.DAY_OF_MONTH) + ", " + shipDate.get(Calendar.YEAR));
	}
	
	/**
	 * Generates the delivery date and sends it back as a string.
	 * @return The delivery date.
	 */
	private String getDeliverDate() {
		Calendar deliverDate = oDate;
		int days = random.nextInt(7) + 3;
		deliverDate.add(Calendar.DATE, days);
		
		return (deliverDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + " " + 
				deliverDate.get(Calendar.DAY_OF_MONTH) + ", " + deliverDate.get(Calendar.YEAR));
	}
}
