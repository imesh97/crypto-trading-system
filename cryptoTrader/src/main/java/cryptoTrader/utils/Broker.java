package cryptoTrader.utils;

import java.util.Arrays;
import java.text.SimpleDateFormat;  
import java.util.Date;  

/**
 * Broker stores the names, coins, strategies, and number of brokers 
 * @author Gurshawn Singh Lehal (glehal) , Imesh Nimsitha (inimsith) , Uzair Muhammed Salim (usalim2) , Gunveer Vilkhu (gvilkhu)
 */
public class Broker { //create a broker object to deal with brokers
	
	//list of brokers
	private String [] names;
	private String [][] coins;
	private String [] strategies; 
	private int len; //number of brokers
	
	//Constants
	private int MAX = 1000;
	private int coinMAX = 10;
	
	/**
	 * Constructor
	 * Create new broker object with properties
	 */
	public Broker () { 
		names = new String[MAX];
		coins = new String[MAX][coinMAX];
		strategies = new String[MAX];
		len = 0;
	}
	
	/** AddBroker - adds a new broker
	 * @param name - name of broker
	 * @param coinList - list of coins broker has picked
	 * @param strat - strategy chosen by broker
	 */
	public void addBroker (String name, String[] coinList, String strat) {
		names[len] = name;
		coins[len] = coinList;
		strategies[len] = strat;
		len++; //add one to length to adjust array
	}
	
	/** 
	 * GetBroker prints broker to console
	 * @param id
	 */
	public void getBroker (int id) {
		System.out.println(names[id] + " " + Arrays.toString(coins[id]) + " " + strategies[id]);
	}
	
	/**
	 * getBrokers gets string of all brokers
	 * @return String[] of all brokers 
	 */
	public String[] getBrokers () {
		return this.names;
	}
	
	/**
	 * getStrats return the lists of all strategies 
	 * @return String[] list of all strats
	 */
	public String[] getStrats() {
		return this.strategies;
	}
	
	/**
	 * getLength returns the number of brokers
	 * @return number of brokers as an int
	 */
	public int getLength() {
		return this.len;
	}
	
	/**
	 * getCoins returns list of all broker chosen coins
	 * @return String[][] of all coins in a 2-d array
	 */
	public String[][] getCoins() {
		return this.coins;		
	}
	
	/**
	 * getListofCoins retunrs list of coins
	 * No repeats of coins in a string
	 * @return STring[] of coins no duplicates 
	 */
	public String[] getListOfCoins() {
		//all coins list
		String[] allCoins = new String[coinMAX];
		int lenAllCoins = 0;
		
		//loop through all sets of broker coins
		for (String[] list : coins) {
			for (String item : list) {
				if (item != null) {
					boolean itemExists = false;
					
					for (String coins: allCoins) {
						if (item.equals(coins)) {
							itemExists = true;
						}
					}
					
					if (!itemExists) { //add if coin exists in brokers name
						allCoins[lenAllCoins] = item;
						lenAllCoins++;
					}
				}
			}
		}
		
		return allCoins; //return non-coin duplicated array
	}
	
	//return the coin max 
	/**
	 * GetCoinMax return the max number of coins 
	 * @return int as the max number of coins in an array
	 */
	public int getCoinMax() {
		return this.coinMAX;
	}
	
	/**
	 * getFirstNull gets the int of the first null
	 * @param coinList, list of coins to be searched
	 * @return int of index for first null
	 */
	public int getFirstNull(String[] coinList) {
		for (int i = 0; i < getCoinMax(); i++) { //search entire array
			if (coinList[i] == null) {
				return i; //first null found
			}
		}
		return -1; //no null found
	}
	
	/**
	 * getSubArray returns a clean array with no nulls
	 * @param coinList - array to be cleaned
	 * @param firstNull - index of first null
	 * @return String[] array with no nulls
	 */
	public String[] getSubArray(String[] coinList, int firstNull) {
		String[] cleanArray = null;
		
		//find first instance of non null items
		if (firstNull != -1) {
			cleanArray = Arrays.copyOfRange(coinList, 0, firstNull);
		}
		
		return cleanArray;
	}
	
	/**
	 * getDateToday get todays date in dd/mm/yyyy format
	 * @return String of date today
	 */
	public String getDateToday() {
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy"); //format 
		String DateToday = simpleDate.format(date);
		DateToday = DateToday.replace('/', '-'); //switch slash to dash
		return DateToday;
	}

}
