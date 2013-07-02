package Models.StoreModels;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Represents a LineItem in an order. Each line item contains
 * a product, a quantity for the product, and the cost of that
 * product based on the amount requested.
 * @author KDugan
 *
 */
public class OrderItem implements Serializable {
	
	/**
	 * OrderItem attributes
	 */
	private static final long serialVersionUID = 1L;
	private Product product;
	private int quantity;
	private BigDecimal itemCost;
	
	/**
	 * Implicit Constructor
	 */
	public OrderItem() {
		product = new Product();
		quantity = 0;
		itemCost = new BigDecimal(0.00);
	}

	/**
	 * Explicit Constructor
	 * @param prod The product in the line item
	 * @param amt The quantity of product requested
	 */
	public OrderItem(Product prod, int amt) {
		product = prod;
		quantity = amt;
		itemCost = product.getProdPrice().multiply(new BigDecimal(quantity));
	}
	
	/*****************************************
	 * Getters and Setters
	 ****************************************/
	
	public BigDecimal getItemCost() {
		return itemCost;
	}

	public void setItemCost() {
		itemCost = product.getProdPrice().multiply(new BigDecimal(quantity));
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
