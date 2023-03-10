package cryptoTrader.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Load and attempt login attempts for login UI
 * @author Gurshawn Singh Lehal (glehal) , Imesh Nimsitha (inimsith) , Uzair Muhammed Salim (usalim2) , Gunveer Vilkhu (gvilkhu)
 */
public class Login {

	private final String DB_FILENAME = "login_db.txt"; // Constant filename
	
	// Private instance variables for login attempt data
	private String username, password;
	private HashMap<String, String> loginData;
	
	/**
	 * Constructor
	 * Create new login attempt
	 * @param username String Inputed username
	 * @param password String Inputed password
	 */
	public Login(String username, String password) {
		// Set new username and password
		this.username = username;
		this.password = password;
		// Initialize and load login data
		this.loginData = new HashMap<String, String>();
		this.loadLoginData();
	}
	
	/**
	 * Load login data from text file
	 */
	public void loadLoginData() {
		File dbFile = new File(DB_FILENAME); // Retrieve text file
		try {
			if (!dbFile.exists()) { // Check if exists -> if not, then create
				dbFile.createNewFile();
			}
			
			Scanner sc = new Scanner(dbFile); // Scanner to read text file
			while (sc.hasNextLine()) { // Iterate through each line
				// Retrieve username and password using split
				String dataLine = sc.nextLine();
				String dataUsername = dataLine.split(":")[0];
				String dataPass = dataLine.split(":")[1];
				
				loginData.put(dataUsername, dataPass); // Add to hashmap
			}
			
		} catch (IOException error) { // Exception: Unable to read file
			System.err.println("Login database file not found.");
		}
	}
	
	/**
	 * Attempt to login using inputed username and password
	 * @return boolean Attempt was successful or not
	 */
	public boolean attempt() {
		if (this.loginData.get(this.username) != null) { // Check if user exists
			if (this.loginData.get(this.username).equals(this.password)) { // Check password equality
				return true; // Correct username and password -> successful
			}
		}
		
		return false; // Incorrect -> unsuccessful
	}
	
}
