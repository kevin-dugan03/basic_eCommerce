package Models.StoreModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Order class represents an order that can contain many items.
 * @author KDugan
 */
public class Order implements Serializable {
	
	/**
	 * Order attributes
	 */
	private static final long serialVersionUID = 1L;
	private int orderID;
	private ArrayList<OrderItem> itemList;
	private BigDecimal total;
	private BigDecimal subTotal;
	private String orderDate;
	private String shipDate;
	private String deliverDate;
	
	/**
	 * implicit Constructor.
	 */
	public Order () {
		orderID = 0;
		itemList = new ArrayList<OrderItem>();
		total = new BigDecimal(0.00);
	}
	
	/*****************************************
	 * Getters and Setters
	 ****************************************/

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public ArrayList<OrderItem> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<OrderItem> itemList) {
		this.itemList = itemList;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getShipDate() {
		return shipDate;
	}

	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}

	public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	/*****************************************
	 * Business Logic
	 ****************************************/
	
	/**
	 * Updates the quantity of an item before placing the order.
	 * @param id The product to update
	 * @param qty The new quantity
	 * @return true if successful, false otherwise
	 */
	public boolean updateItem(int id, int qty) {
		
		if (qty == 0) {
			return deleteItem(id);
		}
		
		for (OrderItem oi : itemList) {
			if (oi.getProduct().getProdID() == id) {
				oi.setQuantity(qty);
				oi.setItemCost();
				updateSubTotal();
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Adjusts the subtotal of the order based on recent changes.
	 * @return true if updated, false otherwise.
	 */
	public boolean updateSubTotal() {
		
		subTotal = new BigDecimal(0.00);
		
		for (OrderItem oi : itemList) {
			subTotal = subTotal.add(oi.getItemCost());
		}
		
		return true;
	}
	
	/**
	 * Updates the grand total for the order based on the shipping rate.
	 * @param shipRate The amount to add to the total.
	 */
	public void updateTotal(BigDecimal shipRate) {
		total = new BigDecimal(0.00);	
		total = subTotal.add(shipRate);
	}
	
	/**
	 * Removes an item from the order.
	 * @param id The product id to remove.
	 * @return true if successful, false otherwise.
	 */
	public boolean deleteItem(int id) {
		for (OrderItem oi : itemList) {
			if (oi.getProduct().getProdID() == id) {
				itemList.remove(oi);
				updateSubTotal();
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Looks for duplicate product already in order. 
	 * @param id The product to look for.
	 * @return true if successful, false otherwise.
	 */
	public boolean findDuplicate(int id) {
		for (OrderItem oi : itemList) {
			if (oi.getProduct().getProdID() == id) {
				return true;
			}
		}
		
		return false;
	}
}
