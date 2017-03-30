package spring.inventory.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Defines the name of the root element
@XmlRootElement(name = "products")
public class ProductListWrapper {

	// Initializes empty list
	private List<Product> products;
	
	// Specified name for the element
	@XmlElement(name = "products")
	/**
	 * Getter for product list.
	 * @return List of products
	 */
	public List<Product> getProducts(){
		return products;
	}
	
	/**
	 * Setter for product list.
	 * @param products
	 */
	public void setProducts(List<Product> products){
		this.products = products;
	}
	
}
