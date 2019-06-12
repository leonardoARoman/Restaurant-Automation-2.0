package model;

import java.util.ArrayList;

/**
 * 
 * @author leonardoroman
 *
 */
public class Order {

	private int orderNumber;
	private ArrayList<String> items;
	private ArrayList<Double> prices;
	
	public Order(int orderNumber, ArrayList<String> items,ArrayList<Double> prices) {
		this.orderNumber = orderNumber;
		this.items = items;
		this.prices = prices;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getOrderNumber() {
		return orderNumber;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getItems(){
		return items;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getTotal() {
		Double total = 0.0;
		for(Double price: prices) {
			total += price;
		}
		return total;
	}
}
