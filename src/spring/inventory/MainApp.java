package spring.inventory;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import spring.inventory.model.Product;
import spring.inventory.model.ProductListWrapper;
import spring.inventory.view.ProductEditDialogController;
import spring.inventory.view.ProductOverviewController;
import spring.inventory.view.RootLayoutController;
import spring.inventory.view.SalesStatisticsController;
import spring.inventory.view.StockStatisticsController;

public class MainApp extends Application {

	/**
	 * Initializes some variables that are used throughout this class.
	 */
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<Product> productData = FXCollections.observableArrayList();
	
	/**
	 * Method that puts initial data in the observable array list.
	 * When the user opens the program, this data is automatically displayed.
	 * Each one adds a new Product object from /model/Product.java
	 */
	public MainApp(){
		// Product: name, amount available, amount sold, price each, price to make
		productData.add(new Product("Density Scale" , 217, 872, 20, 3));
		productData.add(new Product("21\" Soil Probe" , 179, 54, 34, 22));
		productData.add(new Product("36\" Soil Probe" , 391, 136, 36, 23));
		productData.add(new Product("36\" Step Soil Probe" , 133, 208, 38, 24));
	}
	
	/**
	 * Method that displays the primary stage when opened.
	 * Calls initRootLayout() to show file bar and showProductOverview() to show the inside of the page.
	 */
	@Override
	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
		// Sets the title displayed at the top
		this.primaryStage.setTitle("Soil Sampler Inventory");
		// Sets the icon displayed at the top and desktop
		this.primaryStage.getIcons().add(new Image("file:resources/images/logo.png"));
		initRootLayout();
		showProductOverview();
	}
	
	/**
	 * Method called in start().
	 * Loads the RootLayout.fxml file and connects the controller.
	 * If there is a saved file, it will open that one.
	 */
	public void initRootLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			// Sets location for the root layout file
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			// Loads the controller
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			// Calls .show() to display the primary stage with RootLayout
			primaryStage.show();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		// Sets the file path
		File file = getProductFilePath();
		// If it already exists, load that file
		if(file != null){
			loadProductDataFromFile(file);
		}
	}
	
	/**
	 * Method that loads the product overview to be displayed.
	 */
	public void showProductOverview(){
		try{
			FXMLLoader loader = new FXMLLoader();
			// Sets location for the product overview file
			loader.setLocation(MainApp.class.getResource("view/ProductOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			// Sets the product overview in the center of the root layout
			rootLayout.setCenter(personOverview);
			// Loads the controller
			ProductOverviewController controller = loader.getController();
			controller.setMainApp(this);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that opens the product edit dialog.
	 * Is called when user clicks new or edit button. 
	 * @param product
	 * @return boolean (true, false)
	 */
	public boolean showProductEditDialog(Product product){
		try{
			FXMLLoader loader = new FXMLLoader();
			// Gets location for product edit dialog file
			loader.setLocation(MainApp.class.getResource("view/ProductEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			// Sets the title for this new window
			dialogStage.setTitle("Edit Product");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Gets the corresponding controller
			ProductEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setProduct(product);
			// Shows this file and waits for user input
			dialogStage.showAndWait();
			// Returns whether the user clicks the okay button to save their changes
			return controller.isOkClicked();
		}
		catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Gets the file path of a saved file.
	 * @return File (file, null)
	 */
	public File getProductFilePath(){
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null){
			return new File(filePath);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Sets the file path when saving a file.
	 * @param file
	 */
	public void setProductFilePath(File file){
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null){
			prefs.put("filePath", file.getPath());
		}
		else{
			prefs.remove("filePath");
		}
		primaryStage.setTitle("Soil Sampler Inventory");
	}
	
	/**
	 * Method that loads product data when re opening the last saved file.
	 * @param file
	 */
	public void loadProductDataFromFile(File file){
		try{
			JAXBContext context = JAXBContext.newInstance(ProductListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			ProductListWrapper wrapper = (ProductListWrapper) um.unmarshal(file);
			productData.clear();
			// Gets products from wrapper class and adds them to productData
			productData.addAll(wrapper.getProducts());
			setProductFilePath(file);
		}
		catch (Exception e){
			// Shows error message if not working
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.showAndWait();
		}
	}
	
	/**
	 * Method that saves the product values.
	 * Uses setter from wrapper class.
	 * @param file
	 */
	public void saveProductDataToFile(File file){
		try{
			JAXBContext context = JAXBContext.newInstance(ProductListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ProductListWrapper wrapper = new ProductListWrapper();
			// Uses setter from wrapper class to save data
			wrapper.setProducts(productData);
			m.marshal(wrapper, file);
			// Sets the file path of saved file
			setProductFilePath(file);
		}
		catch (Exception e){
			// Shows error message if not working
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.showAndWait();
		}
	}
	
	/**
	 * Method that opens stock statistics graph when called.
	 */
	public void showStockStatistics(){
		try{
			FXMLLoader loader = new FXMLLoader();
			// Gets location of the fxml file
			loader.setLocation(MainApp.class.getResource("view/StockStatistics.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			// Sets title of new stage
			dialogStage.setTitle("Stock Statistics");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        // Loads the controller
	        StockStatisticsController controller = loader.getController();
	        controller.setProductData(productData);
	        // Shows the stage
	        dialogStage.show();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that opens the sales statistics graph when called.
	 */
	public void showSalesStatistics(){
		try{
			FXMLLoader loader = new FXMLLoader();
			// Gets location of fxml file
			loader.setLocation(MainApp.class.getResource("view/SalesStatistics.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			// Sets the title of the new stage
			dialogStage.setTitle("Sales Statistics");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        // Loads the controller
	        SalesStatisticsController controller = loader.getController();
	        controller.setProductData(productData);
	        // Shows the stage
	        dialogStage.show();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args){
		launch(args);
	}
	
	/**
	 * Primary stage getter.
	 * @return Stage
	 */
	public Stage getPrimaryStage(){
		return primaryStage;
	}
	
	/**
	 * Getter for observable list of products
	 * @return
	 */
	public ObservableList<Product> getProductData(){
		return productData;
	}
	
}