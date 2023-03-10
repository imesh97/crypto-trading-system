package cryptoTrader.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * TradeDB stores the trades in txt and can access all data 
 * @author Gurshawn Singh Lehal (glehal) , Imesh Nimsitha (inimsith) , Uzair Muhammed Salim (usalim2) , Gunveer Vilkhu (gvilkhu) 
 *
 */
public class TradeDB {
	//file name
	private String DB_FILENAME = "trades_db.txt";
	private File myObj;
	private boolean firstWrite;
	
	/**
	 * Constructor
	 * Opens file and creates one if it does not exist
	 */
	public TradeDB() {
		firstWrite = false;
		try { //try making new file
			myObj = new File(DB_FILENAME);
			if (myObj.createNewFile()) {
				System.out.println("Created New File: " + myObj.getName());
				firstWrite = true;
			} else {
				System.out.println("File Already Exists!");
			}
		} catch (IOException e) {
			System.out.println("There was an Error while making the file");		
		}
	}
	
	/**
	 * writeData takes data and inputs it to the txt file
	 * @param trade String[] that contains trade details
	 */
	public void writeData (String[] trade) {
		try {
			//open writers
			FileWriter tradeFileWriter = new FileWriter(DB_FILENAME, true); //append mode on
			PrintWriter writerObj = new PrintWriter(tradeFileWriter);
			
			if (!firstWrite) { //if not first write skip line
				writerObj.print("\n"+Arrays.toString(trade));				
			} else { //else skip line 
				writerObj.print(Arrays.toString(trade));
			}
			
			writerObj.close(); //close file
		} catch (IOException e) { // I/O error
			System.out.println("There was an Error while reading the file");		
		}
	}
	
	/**
	 * getData reads all data and returns as a Object[][]
	 * @return Object[][] 2-d array that returns all trades 
	 */
	public Object[][] getData() {
		
		Object[][] data = new Object[1000][7]; //data store object
		int objLen = 0; // size of array
		try {
			//try opening file and scanning
			File dbFile = new File(DB_FILENAME);
			Scanner sc = new Scanner(dbFile);
			
			while (sc.hasNextLine()) { //while there is still lines to be scanned
				String dataLine = sc.nextLine();
				dataLine = dataLine.substring(1, dataLine.length()-1); //cut []
				dataLine = dataLine.replaceAll("\\s",""); //take out whitespace
				String[] dataLineArr = dataLine.split(","); //put into array
				for (int j = 0; j < dataLineArr.length; j++) { //store into data object
					data[objLen][j] = dataLineArr[j];					
				}
				objLen++; //increase size of length							
			}
			
			data = Arrays.copyOfRange(data, 0, objLen); //cut null		
		} catch (FileNotFoundException e) { //error IO
			System.out.println("There was an Error reading making the file");	
		}	
		
		return data; //return data
	}

}
