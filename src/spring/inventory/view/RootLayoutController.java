package spring.inventory.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import spring.inventory.MainApp;

public class RootLayoutController {

	private MainApp mainApp;
	
	/**
	 * Setter for mainApp.
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	/**
	 * Method that saves the data to file.
	 * On action -> save file option
	 */
	@FXML
	private void handleSave(){
		File productFile = mainApp.getProductFilePath();
		if (productFile != null){
			mainApp.saveProductDataToFile(productFile);
		}
		else{
			handleSaveAs();
		}
	}
	
	/**
	 * Method that opens the file chooser so the user can rename the file.
	 * On action -> save as file option
	 */
	@FXML
	private void handleSaveAs(){
		// Creates new file chooser
		FileChooser fileChooser = new FileChooser();
		// Creates new extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		// Adds the extension filter to the file chooser so only .xml files are displayed
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (file != null){
			if (!file.getPath().endsWith(".xml")){
				file = new File(file.getPath() + ".xml");
			}
			mainApp.saveProductDataToFile(file);
		}
	}
	
	/**
	 * Method that exits the program.
	 * On action -> exit file option
	 */
	@FXML
	private void handleExit(){
		System.exit(0);
	}
	
	/**
	 * Method that calls the show stock stats method to open new window
	 * On action -> show stock graphs option 
	 */
	@FXML
	private void handleShowStockStatistics(){
		mainApp.showStockStatistics();
	}
	
	/**
	 * Method that calls the show sales stats method to open new window
	 * On action -> show sales graphs option 
	 */
	@FXML
	private void handleShowSalesStatistics(){
		mainApp.showSalesStatistics();
	}
	
}
