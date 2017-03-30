package spring.inventory.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {

	/** 
	 * Initializing the variables that will make up the product object.
	 */
	private final StringProperty name;
	private final IntegerProperty amountAvailable;
	private final IntegerProperty amountSold;
	private final IntegerProperty priceEach;
	private final IntegerProperty priceMake;
	private final IntegerProperty profit;
	private final IntegerProperty money;
	
	/**
	 * Default constructor.
	 * Sets the values as blank or zero if there is an empty product.
	 */
	public Product(){
		this.name = new SimpleStringProperty(" ");
		this.amountAvailable = new SimpleIntegerProperty(0);
		this.amountSold = new SimpleIntegerProperty(0);
		this.priceEach = new SimpleIntegerProperty(0);
		this.priceMake = new SimpleIntegerProperty(0);
		this.profit = new SimpleIntegerProperty(0);
		this.money = new SimpleIntegerProperty(0);
	}
	
	/** 
	 * Overload constructor.
	 * Sets the values as the ones initialized in the parenthesis.
	 * Calculates the profit and money earned.
	 * @param name
	 * @param amountAvailable
	 * @param amountSold
	 * @param priceEach
	 * @param priceMake
	 */
	public Product(String name, int amountAvailable, int amountSold, int priceEach, int priceMake){
		this.name = new SimpleStringProperty(name);
		this.amountAvailable = new SimpleIntegerProperty(amountAvailable);
		this.amountSold = new SimpleIntegerProperty(amountSold);
		this.priceEach = new SimpleIntegerProperty(priceEach);
		this.priceMake = new SimpleIntegerProperty(priceMake);
		// Calculates the profit by subtracting the cost to make from the price its sold at
		this.profit = new SimpleIntegerProperty(priceEach-priceMake);
		// Calculates the money earned by multiplying the profit by the amount sold 
		this.money = new SimpleIntegerProperty(profit.getValue().intValue()*amountSold);
	}

	/**
	 * Getter for name.
	 * @return String
	 */
	public String getName() {
		return name.get();
	}
	/**
	 * Setter for name.
	 * @param name
	 */
	public void setName(String name){
		this.name.set(name);
	}
	/**
	 * Getter for name.
	 * @return StringProperty
	 */
	public StringProperty nameProperty(){
		return name;
	}
	
	/**
	 * Getter for amount available.
	 * @return int
	 */
	public int getAmountAvailable(){
		return amountAvailable.get();
	}
	/**
	 * Setter for amount available.
	 * @param amountAvailable
	 */
	public void setAmountAvailable(int amountAvailable){
		this.amountAvailable.set(amountAvailable);
	}
	/**
	 * Getter for amount available.
	 * @return IntegerProperty
	 */
	public IntegerProperty amountAvailableProperty() {
		return amountAvailable;
	}

	/**
	 * Getter for amount sold.
	 * @return int
	 */
	public int getAmountSold(){
		return amountSold.get();
	}
	/** 
	 * Setter for amount sold.
	 * @param amountSold
	 */
	public void setAmountSold(int amountSold){
		this.amountSold.set(amountSold);
	}
	/**
	 * Getter for amount sold.
	 * @return IntegerProperty
	 */
	public IntegerProperty amountSoldProperty() {
		return amountAvailable;
	}
	
	/**
	 * Getter for price each.
	 * @return int
	 */
	public int getPriceEach(){
		return priceEach.get();
	}
	/**
	 * Setter for price each.
	 * @param priceEach
	 */
	public void setPriceEach(int priceEach){
		this.priceEach.set(priceEach);
	}
	/**
	 * Getter for price each.
	 * @return IntegerProperty
	 */
	public IntegerProperty priceEachProperty() {
		return priceEach;
	}

	/**
	 * Getter for price make.
	 * @return int
	 */
	public int getPriceMake(){
		return priceMake.get();
	}
	/**
	 * Setter for price make.
	 * @param priceMake
	 */
	public void setPriceMake(int priceMake){
		this.priceMake.set(priceMake);
	}
	/**
	 * Getter for price make.
	 * @return IntegerProperty
	 */
	public IntegerProperty priceMakeProperty() {
		return priceMake;
	}

	/**
	 * Getter for profit.
	 * @return int
	 */
	public int getProfit(){
		return profit.get();
	}
	/** 
	 * Setter for profit.
	 * @param profit
	 */
	public void setProfit(int profit){
		this.profit.set(profit);
	}
	/** 
	 * Getter for profit.
	 * @return IntegerProperty
	 */
	public IntegerProperty profitProperty() {
		return profit;
	}

	/**
	 * Getter for money.
	 * @return int
	 */
	public int getMoney(){
		return money.get();
	}
	/**
	 * Setter for money.
	 * @param money
	 */
	public void setMoney(int money){
		this.money.set(money);
	}
	/**
	 * Getter for money.
	 * @return IntegerProperty
	 */
	public IntegerProperty MoneyProperty() {
		return money;
	}

}