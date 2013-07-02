package Models.StoreModels;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import Models.DBConnMgr;

/**
 * Active Record for Customers.
 * Insert, Find, Update, Delete Customer records.
 * @author KDugan
 *
 */
public class Customer  {
	
	/**
	 * Customer attributes
	 */
	private int userID;
	private String name;
	private Address defAddr;
	private String email;
	private String username;
	private String password;
	private int status;
	private int numOrders;
	private BigDecimal shippingRate;
	
	/**
	 * REGEX Strings
	 */
	private static final String VALID_NAME = "^[a-z A-Z 0-9,. ]+$";
	private static final String VALID_EMAIL= "[A-Za-z0-9.%+\\-]+@[A-Za-z0-9.\\-]+" +
			"\\.[A-Za-z\\S]{2,4}";
	private static final String VALID_USERNAME= "^[a-zA-Z0-9-_.\\S]+$";
	
	/**
	 * Database attributes
	 */
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet result;
	private DBConnMgr dbMgr;
	
	/**
	 * SQL Statements
	 */
	private final static String FIND = "SELECT customerID FROM Customer WHERE userName = ?";
	private final static String INSERT_CUSTOMER = "INSERT INTO customer (custName, custEmail, userName, custPassword) " +
			"VALUES (?,?,?,?)";
	private final static String FIND_REGISTERED_CUSTOMER = "SELECT * FROM Customer WHERE username=? AND custPassword=?";
	private final static String UPDATE_CUSTOMER = "UPDATE CUSTOMER SET custName=?, custEmail=?, userName=?, custPassword=? WHERE customerID=?";
	private final static String DELETE_CUSTOMER = "DELETE FROM Customer WHERE customerID=?";

	
	/**
	 * Explicit constructor.
	 * @param name Customer's name
	 * @param addr Customer's address
	 * @param email Customer's email address
	 * @param userName Customer username
	 * @param password Customer password
	 */
 	public Customer(String name, Address addr, String email, String userName, String password) {
		//Customer attribute initialization
		this.name = name;
		this.defAddr = addr;
		this.email = email;
		this.status = 1;
		this.username = userName;
		this.password = password;
		this.numOrders = 0;
		this.userID = -1;
		this.shippingRate = new BigDecimal(0.00);
		
		//Database attribute initialization
		conn = null;
		ps = null;
		result = null;
		dbMgr = new DBConnMgr();
	}
 	
	/**
	 * Implicit Constructor.
	 */
 	public Customer() {
		conn = null;
		ps = null;
		result = null;
		dbMgr = new DBConnMgr();
	}
	
	/*****************************************
	 * Getters and Setters
	 ****************************************/
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getDefAddr() {
		return defAddr;
	}

	public void setDefAddr(Address defAddr) {
		this.defAddr = defAddr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNumOrders() {
		return numOrders;
	}

	public void setNumOrders(int numOrders) {
		this.numOrders = numOrders;
	}
	
	
	public BigDecimal getShippingRate() {
		return shippingRate;
	}
	
	public boolean setShippingRate(BigDecimal shippingRate) {
		if (shippingRate == null) {
			return false;
		}
		
		this.shippingRate = shippingRate;
		return true;
	}

	/*****************************************
	 * Business Logic
	 ****************************************/
	
	/**
	 * Validates user login credentials
	 * @param user Username iser input.
	 * @param pass Password user input.
	 * @return True if validated, false otherwise.
	 */
	public boolean validateLogin(String user, String pass) {
		
		if (user == null || user.toUpperCase().equals("USERNAME")  || user.isEmpty()
				|| user.trim().length() == 0) {
			return false;
		} else if (pass == null || pass.equals("Enter Password") || pass.isEmpty() 
				|| pass.trim().length() == 0) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Validates user personal information input.
	 * @param custName Customer's name.
	 * @param custEmail Customer's email address.
	 * @param custUsername Customer username selection.
	 * @param custPassword1 Customer password selection.
	 * @param custPassword2 Custoemr password selection.
	 * @return customer object if successful, null otherwise.
	 */
	public Customer validate(String custName, String custEmail, String custUsername, String custPassword1, String custPassword2) {
		
		if (custName == null || custName.toUpperCase().equals("NAME") || !Pattern.matches(VALID_NAME, custName) 
				|| custName.isEmpty() || custName.trim().length() == 0) {
			return null;
		} else if (custEmail == null || custEmail.toUpperCase().equals("E-MAIL ADDRESS") 
				|| !Pattern.matches(VALID_EMAIL, custEmail)  || custEmail.isEmpty()) {
			return null;
		} else if ( custUsername == null || custUsername.toUpperCase().equals("USERNAME") 
				|| !Pattern.matches(VALID_USERNAME, custUsername) || custUsername.isEmpty()) {
			return null;
		} else if (custPassword1 == null || custPassword1.toUpperCase().equals("ENTER PASSWORD") 
				|| custPassword1.isEmpty()) {
			return null;
		} else if (custPassword2 == null || custPassword2.toUpperCase().equals("RE-ENTER PASSWORD") 
				|| custPassword2.isEmpty()) {
			return null;
		} else if (!(custPassword1.equals(custPassword2))) {
			return null;
		}
		
		this.name = custName;
		this.email = custEmail;
		this.username = custUsername;
		this.password = custPassword1;

		return this;
	}

	/**
	 * Updates the shipping rate to a new value.
	 */
	public void updateShippingRate() {
		if (numOrders >= 5) {
			this.shippingRate = new BigDecimal(0.00);
		} else {
			this.shippingRate = new BigDecimal(5.00);
		}
	}

	/**
	 * Increments the number of orders a customer has placed.
	 */
	public void incrementNumOrders() {
		numOrders++;
	}
	
	/****************************************
	 * SQL Operations
	 ***************************************/
	
	
	/**
	 * Attempts to insert a customer into the database
	 * @return the number of rows affected
	 * @throws SQLException
	 */
	public int insert() throws SQLException {	
		
		int custRow = 0;
		
		try {
			if (find() < 1) {
				//Get the DB connection
				conn = dbMgr.connection();
								
				//Insert the Customer into the Customer table
				ps = conn.prepareStatement(INSERT_CUSTOMER, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, name);
				ps.setString(2, email);
				ps.setString(3, username.toLowerCase());
				ps.setString(4, password);
				custRow = ps.executeUpdate();	
				
				//Get the newly added Customer's ID, insert into in-memory object
				ResultSet res = ps.getGeneratedKeys();
				if (res.next()){
					setUserID(res.getInt(1));
					defAddr.setCustomerId(userID);
					defAddr.insert();
				} else {
					return 0;
				}
				
				shippingRate = new BigDecimal(5.00);
					
			}//end of if
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return custRow;
	}
	
	/**
	 * Attempts to find a customer in the database.
	 * @return int the ID of the customer found. 
	 * @throws SQLException
	 */
	public int find() throws SQLException {
		
		int id = -1;
		
		try {
			conn = dbMgr.connection();
			ps = conn.prepareStatement(FIND);
			ps.setString(1, username.toLowerCase());
			result = ps.executeQuery();
			
			if (result.next()) {
				id = result.getInt(1);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return id;
	}
	
	/**
	 * Attempts to log a user in, given username and password
	 * @param user Customer username
	 * @param pass Customer password
	 * @return boolean True if logged in, false otherwise
	 * @throws SQLException
	 */
	public boolean login(String user, String pass) throws SQLException {
		
		boolean custFound = false;
		boolean addrFound = false;
		
		try
		{
			//Get the DB connection
			conn = dbMgr.connection();
			
			//Get the customer information from username and password
			ps = conn.prepareStatement(FIND_REGISTERED_CUSTOMER);
			ps.setString(1, user);
			ps.setString(2, pass);
			result = ps.executeQuery();	
			
			//Set up the attributes of the customer object
			if (result.next()){
				userID = (result.getInt(1));
				name = (result.getString(2));
				email = (result.getString(3));
				username = (result.getString(4));
				status = (result.getInt(6));
				numOrders = (result.getInt(7));
				custFound = true;
			}
				
			//Set up the customer's address
			if (custFound) {
				defAddr = new Address();
				addrFound = defAddr.find(userID);
			}
			
			//update the shipping rate for the customer
			updateShippingRate();
			
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    conn.close();
		}
		
		if (custFound && addrFound) {
			return true;
		} 
		
		return false;
	}
	
	/**
	 * Attempts to update a customer's information.
	 * @return boolean True if updated, false otherwise
	 * @throws SQLException
	 */
	public boolean update() throws SQLException {
		
		int custChange = 0;
		boolean success = false;
		
		try
		{
			//Get the DB connection
			conn = dbMgr.connection();
			
			//Update the Customer in the Customer table
			ps = conn.prepareStatement(UPDATE_CUSTOMER);
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, username);
			ps.setString(4, password);
			ps.setInt(5, userID);
			custChange = ps.executeUpdate();
			
			if (custChange == 1) {
				success = defAddr.update(userID);;
			}
		
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    conn.close();
		}	
		
		return success;
	}
	
	/**
	 * Attempts to remove a customer from the database.
	 * @return boolean True if deleted, false otherwise
	 * @throws SQLException
	 */
	public boolean delete() throws SQLException {
		
		int custDelete = 0;
		boolean success = false;
		
		try
		{
			//Get the DB connection
			conn = dbMgr.connection();
			
			//Update the Customer in the Customer table
			ps = conn.prepareStatement(DELETE_CUSTOMER);
			ps.setInt(1, userID);
			custDelete = ps.executeUpdate();
			
			if (custDelete == 1) {
				success = true;
			}
		
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    conn.close();
		}	
		
		return success;
	}
}
