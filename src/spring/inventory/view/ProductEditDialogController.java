package spring.inventory.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import spring.inventory.model.Product;

public class ProductEditDialogController {

	// Creates fxml ids for each item in scenebuilder
	@FXML
	private TextField nameField;
	@FXML
	private TextField amountAvailableField;
	@FXML
	private TextField amountSoldField;
	@FXML
	private TextField priceEachField;
	@FXML
	private TextField priceMakeField;
	
	// Initializes some variables used later
	private Stage dialogStage;
	private Product product;
	private boolean okClicked = false;
	
	/**
	 * Method is called initially.
	 */
	@FXML
	private void initialize(){
	}
	
	/**
	 * Setter for the dialog stage.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	
	/**
	 * Method that sets the values shown in the text fields.
	 * Uses getters to get the value and set it as the text.
	 * @param product
	 */
	public void setProduct(Product product){
		this.product = product;
		nameField.setText(product.getName());
		amountAvailableField.setText(String.valueOf(product.getAmountAvailable()));
		amountSoldField.setText(String.valueOf(product.getAmountSold()));
		priceEachField.setText(String.valueOf(product.getPriceEach()));
		priceMakeField.setText(String.valueOf(product.getPriceMake()));
	}

	/**
	 * Getter for okClicked boolean value.
	 * @return boolean
	 */
	public boolean isOkClicked(){
		return okClicked;
	}

	/**
	 * Method that updates the values when the user clicks the okay button.
	 * Gets the text from the textfield, sets the new value in the product object.
	 */
	@FXML
	private void handleOk(){
		if(isInputValid()){
			product.setName(nameField.getText());
			product.setAmountAvailable(Integer.parseInt(amountAvailableField.getText()));
			product.setAmountSold(Integer.parseInt(amountSoldField.getText()));
			product.setPriceEach(Integer.parseInt(priceEachField.getText()));
			product.setPriceMake(Integer.parseInt(priceMakeField.getText()));
			// Converts the values to ints and then subtracts
			product.setProfit(Integer.parseInt(priceEachField.getText())-Integer.parseInt(priceMakeField.getText()));
			// Converts the values to ints, subtracts, and then multiplies
			product.setMoney((Integer.parseInt(priceEachField.getText())-Integer.parseInt(priceMakeField.getText()))*Integer.parseInt(amountSoldField.getText()));
			okClicked = true;
			dialogStage.close();	
		}
	}
	
	/**
	 * Closes the window if the user clicks cancel.
	 */
	@FXML
	private void handleCancel(){
		dialogStage.close();
	}
	
	/**
	 * Checks to see if teh user entered real data.
	 * If the value is null or has a length of 0, it will return false as an error.
	 * @return boolean
	 */
	private boolean isInputValid(){
		String errorMessage = "";
		if((nameField.getText() == null) || (nameField.getText().length() == 0)){
			errorMessage += "No valid product name!\n";
		}
		if((amountAvailableField.getText() == null) || (amountAvailableField.getText().length() == 0)){
			errorMessage += "No valid amount available value!\n";
		}
		if((amountSoldField.getText() == null) || (amountSoldField.getText().length() == 0)){
			errorMessage += "No valid amount sold value!\n";
		}
		if((priceEachField.getText() == null) || (priceEachField.getText().length() == 0)){
			errorMessage += "No valid price for each!\n";
		}
		if((priceMakeField.getText() == null) || (priceMakeField.getText().length() == 0)){
			errorMessage += "No valid price to make the product!\n";
		}
		if(errorMessage.length() == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
}
