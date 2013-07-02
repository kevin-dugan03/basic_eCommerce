package Models.StoreModels;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Represents a single product in a customer's order.
 * @author KDugan
 *
 */
public class Product implements Serializable {
	
	/**
	 * Product attributes
	 */
	private static final long serialVersionUID = 1L;
	private int prodID;
	private String prodName;
	private String prodDesc;
	private int prodCategory;
	private BigDecimal prodPrice;
	private int prodStock;
	private int prodFeatured;
	private String imageURL;

	/**
	 * Implicit Constructor
	 */
	public Product() {
		//implicit constructor
	}
	
	//Explicit Constructor
	public Product(int id, String name, String desc, int cat, BigDecimal price, int stock, int featured, String image) {
		prodID = id;
		prodName = name;
		prodDesc = desc;
		prodCategory = cat;
		prodPrice = price;
		prodStock = stock;
		prodFeatured = featured;
		imageURL = image;
	}
	
	/*****************************************
	 * Getters and Setters
	 ****************************************/

	public int getProdID() {
		return prodID;
	}

	public void setProdID(int prodID) {
		this.prodID = prodID;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdDesc() {
		return prodDesc;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	public int getProdCategory() {
		return prodCategory;
	}

	public void setProdCategory(int prodCategory) {
		this.prodCategory = prodCategory;
	}

	public BigDecimal getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(BigDecimal prodPrice) {
		this.prodPrice = prodPrice;
	}

	public int getProdStock() {
		return prodStock;
	}

	public void setProdStock(int prodStock) {
		this.prodStock = prodStock;
	}

	public int getProdFeatured() {
		return prodFeatured;
	}

	public void setProdFeatured(int prodFeatured) {
		this.prodFeatured = prodFeatured;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}
