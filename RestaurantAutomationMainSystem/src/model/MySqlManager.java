package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 * @author leonardoroman
 *
 */
public class MySqlManager {
	// Instance variable to access mysql database
	private String url = "jdbc:mysql://restauto.c8kfv5fb1sng.us-east-2.rds.amazonaws.com:3306/restauto";
	private String myDriver = "org.gjt.mm.mysql.Driver";
	private String u_name = "restauto";
	private String pass = "restauto1";
	private static MySqlManager mysql = null;

	private MySqlManager() {}

	/**
	 * 
	 * @return
	 */
	public static MySqlManager getInstance() {
		if(mysql == null)
			mysql = new MySqlManager();

		return mysql;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getFastFood() {
		ArrayList<String> fastfood = new ArrayList<String>();
		//Accessing DBMS
		try {
			Class.forName(myDriver);
			Connection connection = DriverManager.getConnection(url, u_name, pass);
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select name, price from menu where section = 'fastfood' ");
			while(rs.next()) {
				fastfood.add(rs.getString("name")+", "+rs.getString("price"));
			}
		}catch(Exception e) {
			System.out.println(e.getMessage()+": Error!");
		}	
		return fastfood;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getSpecials() {
		ArrayList<String> specials = new ArrayList<String>();
		//Accessing DBMS
		try {
			Class.forName(myDriver);
			Connection connection = DriverManager.getConnection(url, u_name, pass);
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select name, price from menu where section = 'specials' ");
			while(rs.next()) {
				specials.add(rs.getString("name")+", "+rs.getString("price"));
			}
		}catch(Exception e) {
			System.out.println(e.getMessage()+": Error!");
		}	
		return specials;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getDesert() {
		ArrayList<String> deserts = new ArrayList<String>();
		//Accessing DBMS
		try {
			Class.forName(myDriver);
			Connection connection = DriverManager.getConnection(url, u_name, pass);
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select name, price from menu where section = 'dessert' ");
			while(rs.next()) {
				deserts.add(rs.getString("name")+", "+rs.getString("price"));
			}
		}catch(Exception e) {
			System.out.println(e.getMessage()+": Error!");
		}	
		return deserts;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getDrinks() {
		ArrayList<String> drinks = new ArrayList<String>();
		//Accessing DBMS
		try {
			Class.forName(myDriver);
			Connection connection = DriverManager.getConnection(url, u_name, pass);
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select name, price from menu where section = 'drinks' ");
			while(rs.next()) {
				drinks.add(rs.getString("name")+", "+rs.getString("price"));
			}
		}catch(Exception e) {
			System.out.println(e.getMessage()+": Error!");
		}	
		return drinks;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getRervations(){
		// make changes:
		return null;
	}

	/**
	 * 
	 * @param name
	 * @param lastname
	 * @param phone
	 */
	public void makeReservation(String name, String lastname, String phone) {

		try {
			Class.forName(myDriver);
			Connection connection = DriverManager.getConnection(url, u_name, pass);
			PreparedStatement st = connection.prepareStatement("insert into reservation(name,lastname,phone)"
					+ " values (?,?,?)");
			st.setString(1,name);
			st.setString(2 ,lastname);
			st.setString(3,phone);
			//st.setNString(4 ,time);
			st.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage()+": Error!");
		}	
	}


	public String getUserBiometricPrint(String empID) {
		String value = "";
		try {
			Class.forName(myDriver);
			Connection connection = DriverManager.getConnection(url, u_name, pass);
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select hashedImage from fingerPrint where empID = '"+empID+"'");
			while(rs.next()) {
				// get fingerprint image for matching
				value = rs.getString("hashedImage");
			}
		}catch(Exception e) {
			System.out.println(e.getMessage()+": Error!");
		}	
		return value;
	}

	public ArrayList<String> getCredentials(String empID){
		
		ArrayList<String> credentials = new ArrayList<String>();
		//Accessing DBMS
		try {
			Class.forName(myDriver);
			Connection connection = DriverManager.getConnection(url, u_name, pass);
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select * from employee where empID = '"+empID+"'");
			while(rs.next()) {
				credentials.add(rs.getString("lname")+", "+rs.getString("name")); // cred[0]
				credentials.add(rs.getString("job")); // cred[1]
				credentials.add(rs.getString("empID")); // cred[2]
			}
		}catch(Exception e) {
			System.out.println(e.getMessage()+": Error!");
		}
		
		return credentials;
	}
}
