package Models.StoreModels;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import Models.DBConnMgr;

/**
 * Active Record for customer's address.
 * Address class represents a customer's address
 * @author KDugan
 * @version 1.0
 */
public class Address implements Serializable {

	/**
	 * Address attributes
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String street;
	private String city;
	private String state;
	private int zip;
	private int customerID;
	
	/**
	 * REGEX Strings
	 */
	static final String VALID_ADDR = "^[a-zA-Z0-9,.-_, &#()\\/:'\"]+$";
	static final String ZIP_REGEX = "^\\d{5}$";
	
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
	private final static String insertAddress = "INSERT INTO address (customerID, street, city, state, zip) " +
			"VALUES (?,?,?,?,?)";
	private final static String getAddress = "SELECT * FROM Address WHERE customerID=?";
	private final static String updateAddr = "UPDATE ADDRESS SET street=?, city=?, state=?, zip=? WHERE customerID=?";
	
	/**
	 * Explicit Constructor.
	 * @param street Customer's street address
	 * @param city Customer's city
	 * @param state Customer's state
	 * @param zip Customer's zip code
	 */
	public Address(String street, String city, String state, String zip) {
		this.id = 0;
		this.customerID = 0;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = Integer.parseInt(zip);
		
		//Database attribute initialization
		conn = null;
		ps = null;
		result = null;
		dbMgr = new DBConnMgr();
	}
	
	/**
	 * Implicit constructor
	 */
	public Address() {
		conn = null;
		ps = null;
		result = null;
		dbMgr = new DBConnMgr();
	}

	/*****************************************
	 * Getters and Setters
	 ****************************************/
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getCustomerId() {
		return customerID;
	}

	public void setCustomerId(int id) {
		this.customerID = id;
	}
	
	/****************************************
	 * Business Logic
	 ***************************************/
	
	/**
	 * Validation of address data.
	 * @param custStreet Street address user input.
	 * @param custCity City user input.
	 * @param custState State user input.
	 * @param custZip Zip code user input.
	 * @return Address if successful, null otherwise.
	 */
	public Address validateAddress(String custStreet, String custCity, String custState, String custZip) {
		
		if (custStreet == null || custStreet.equals("Street") || custStreet.isEmpty() || custStreet.trim().length() == 0 
				||!Pattern.matches(VALID_ADDR, custStreet)){
			return null;
		} else if (custCity == null || custCity.equals("City") || custCity.isEmpty() || custStreet.trim().length() == 0 
				|| !Pattern.matches(VALID_ADDR, custCity)) {
			return null;
		} else if (custState == null || custState.equals("State") || custState.isEmpty() || custStreet.trim().length() == 0 
				|| !Pattern.matches(VALID_ADDR, custState)) {
			return null;
		} else if (custZip == null || custZip.equals("Zip Code") || custZip.isEmpty() || !Pattern.matches(ZIP_REGEX, custZip) 
				|| custZip.trim().length() == 0) {
			return null;
		}
			
		this.street = custStreet;
		this.city = custCity;
		this.state = custState;
		
		try {
			this.zip = Integer.parseInt(custZip);
		} catch (NumberFormatException e) {
			return null;
		}
		
		return this;
	}
	
	
	
	/****************************************
	 * SQL Operations
	 ***************************************/
	
	/**
	 * Attempts to insert an address into the database
	 * @return int number of rows affected
	 * @throws SQLException
	 */
	public int insert() throws SQLException {	
		
		int rowAffected = 0;
		
		try {
			//Get the DB connection
			conn = dbMgr.connection();

			//Insert the Customer into the Customer table
			ps = conn.prepareStatement(insertAddress);
			ps.setInt(1, customerID);
			ps.setString(2, street);
			ps.setString(3, city);
			ps.setString(4, state);
			ps.setInt(5, zip);
			rowAffected = ps.executeUpdate();	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
			
		return rowAffected;
	}
	
	/**
	 * Attempts to find a requested address from the database.
	 * @param custID Customer ID to locate the address
	 * @return boolean True if found, false otherwise
	 * @throws SQLException
	 */
	public boolean find(int custID) throws SQLException {
		
		boolean found = false;
		
		try {
			conn = dbMgr.connection();
			
			ps = conn.prepareStatement(getAddress);
			ps.setInt(1, custID);
			result = ps.executeQuery();
			
			//Set up the address, add it to customer attributes
			if (result.next()){
				setId(result.getInt(1));
				setCustomerId(result.getInt(2));
				setStreet(result.getString(3));
				setCity(result.getString(4));
				setState(result.getString(5));
				setZip(result.getInt(6));
				found = true;
			}
			
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    conn.close();
		}
		
		return found;
	}
	
	/**
	 * Attempts to update the address in the database.
	 * @param custID Customer who's address to update
	 * @return boolean True if updated, false otherwise
	 * @throws SQLException
	 */
	public boolean update(int custID) throws SQLException {
		
		int addrChange = 0;
		boolean success = false;
		
		try {
			conn = dbMgr.connection();
			
			//Update the Customer's address in the Address table
			ps = conn.prepareStatement(updateAddr);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setInt(4, zip);
			ps.setInt(5, custID);
			addrChange = ps.executeUpdate();
			
			if (addrChange == 1) {
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


