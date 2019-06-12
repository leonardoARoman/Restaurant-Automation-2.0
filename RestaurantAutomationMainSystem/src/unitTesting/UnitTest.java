package unitTesting;

import java.util.ArrayList;

import model.MySqlManager;
/**
 * 
 * @author leonardoroman
 *
 */
public class UnitTest {
	
	public static void main(String[] args) {
		// Expected values
		String[] employees = {"roman, leonardo","waiter","123"};
		String[] fastfood = {"Chess steak, 6.99","Hamburger, 6.99","Pizza pie, 9.99"};
		String[] drinks = {"Beer, 9.99","Soda, 5.99","Wine, 9.99"};
		String[] specials = {"lobster, 39.99","Pasta, 19.99","steak, 29.99"};
		String[] reservationsName = {"andres","Leonardo","Juan","Marcos","Scarlet"};
		String[] reservationTime = {null};
		// TODO Auto-generated method stub
		MySqlManager mysql = MySqlManager.getInstance();
		
		// Sys call test
		test(mysql.getCredentials("123"), employees);
		test(mysql.getFastFood(), fastfood);
		test(mysql.getDrinks(), drinks);
		test(mysql.getSpecials(), specials);
		//test(mysql.getRervations(), reservationsName);
		//test(mysql.getRervations(), reservationTime);
		
	}
	
	/**
	 * 
	 * @param items
	 * @param expectedItems
	 */
	public static void test(ArrayList<String> items, String[] expectedItems) {
		int size = items.size();
		for(int i = 0; i < size; i++) {
			if(items.get(i).equals(expectedItems[i])) {
				System.out.println(items.get(i)+": Passed.");
			}else {
				System.out.println(items.get(i)+": Didn't passed!");	
			}
		}
	}

}
