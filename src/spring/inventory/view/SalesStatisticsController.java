package spring.inventory.view;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import spring.inventory.model.Product;

public class SalesStatisticsController {

	@FXML
	private BarChart<String, Integer> barChart;
	@FXML
	private CategoryAxis xAxis;
	private ObservableList<String> productNames = FXCollections.observableArrayList();
	
	/**
	 * Method that puts the x axis labels in an array.
	 * Adds the values to a list.
	 * Sets the values as x axis categories.
	 */
	@FXML
	private void initialize(){
		String[] products = new String[4];
		products[0] = "Density Scale";
		products[1] = "21\" Soil Probe";
		products[2] = "36\" Soil Probe";
		products[3] = "36\" Step Soil Probe";
		productNames.addAll(Arrays.asList(products));
		xAxis.setCategories(productNames);
	}
	
	/**
	 * Method that gets and sets the data for the graph.
	 * Loops through the products and puts the money value in an array.
	 * Gets the name and value.
	 * Adds the data to the series.
	 * @param products
	 */
	public void setProductData(List<Product> products){
		int[] stock = new int[4];
		for(int x=0; x<products.size(); x++){
			int num = products.get(x).getMoney() - 1;
			stock[x] = num;
		}
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		for(int x=0; x<stock.length; x++){
			series.getData().add(new XYChart.Data<>(productNames.get(x) , stock[x]));
		}
		barChart.getData().add(series);
	}
	
}
