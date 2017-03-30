package spring.inventory.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import spring.inventory.MainApp;
import spring.inventory.model.Product;

public class ProductOverviewController {

	@FXML
	private TableView<Product> productTable;
	@FXML
	private TableColumn<Product, String> nameColumn;
	@FXML
	private TableColumn<Product, Integer> amountAvailableColumn;
	@FXML
	private Label nameLabel;
	@FXML
	private Label amountAvailableLabel;
	@FXML
	private Label amountSoldLabel;
	@FXML
	private Label priceEachLabel;
	@FXML
	private Label priceMakeLabel;
	@FXML
	private Label profitLabel;
	@FXML
	private Label moneyLabel;
	
	private MainApp mainApp;
	
	/**
	 * Default constructor.
	 */
	public ProductOverviewController(){
	}
	
	/**
	 * Method that sets the column values to name and amount available.
	 * Shows no details when first opened.
	 * Adds a listener to each object in the table/list.
	 */
	@FXML
	private void initialize(){
		// Sets name column
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		// Sets amount available coulmn
		amountAvailableColumn.setCellValueFactory(cellData -> cellData.getValue().amountAvailableProperty().asObject());
		// Clears details on window
		showProductDetails(null);
		// Adds listener
		productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetails(newValue));
	}
	
	/**
	 * Method that displays the details of each product when clicked.
	 * @param product
	 */
	private void showProductDetails(Product product){
		// If the product is null, show empty values
		if (product == null){
			nameLabel.setText(" ");
			amountAvailableLabel.setText(" ");
			amountSoldLabel.setText(" ");
			priceEachLabel.setText(" ");
			priceMakeLabel.setText(" ");
			profitLabel.setText(" ");
			moneyLabel.setText(" ");
		}
		else{
			// Uses getters to get the values for each label
			// Uses setText to set each label value
			nameLabel.setText(product.getName());
			amountAvailableLabel.setText(String.valueOf(product.getAmountAvailable()));
			amountSoldLabel.setText(String.valueOf(product.getAmountSold()));
			priceEachLabel.setText(String.valueOf(product.getPriceEach()));
			priceMakeLabel.setText(String.valueOf(product.getPriceMake()));
			profitLabel.setText(String.valueOf(product.getProfit()));
			moneyLabel.setText(String.valueOf(product.getMoney()));	
		}
	}
	
	/**
	 * Method that deletes a product selected in the table.
	 * On action -> delete button
	 */
	@FXML
	private void handleDeleteProduct(){
		int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
		productTable.getItems().remove(selectedIndex);
	}
	
	/**
	 * Method that adds a new product.
	 * Calls showProductEditDialog to open the new window.
	 * On action -> new button
	 */
	@FXML
	private void handleNewProduct(){
		Product tempProduct = new Product();
		boolean okClicked = mainApp.showProductEditDialog(tempProduct);
		if (okClicked){
			mainApp.getProductData().add(tempProduct);
		}
	}
	
	/**
	 * Method that edits a product that is selected.
	 * Calls showProductEditDialog to open new window with old values.
	 * On action -> edit button
	 */
	@FXML
	private void handleEditProduct(){
		Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
		if (selectedProduct != null){
			boolean okClicked = mainApp.showProductEditDialog(selectedProduct);
			if (okClicked){
				showProductDetails(selectedProduct);
			}
		}
	}
	
	/**
	 * Setter for mainApp.
	 * Sets the items in the table by calling getter.
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
		productTable.setItems(mainApp.getProductData());
	}
	
}
