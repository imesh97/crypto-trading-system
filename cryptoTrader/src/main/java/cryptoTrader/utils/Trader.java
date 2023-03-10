package cryptoTrader.utils;

import java.util.Arrays;

/**
 * Trader accesses the broker's coin data, determines if the conditions were met
 * If the Conditions were met, trade is performed
 * Else nothing happens
 * @author Gurshawn Singh Lehal (glehal) , Imesh Nimsitha (inimsith) , Uzair Muhammed Salim (usalim2) , Gunveer Vilkhu (gvilkhu)
 */
public class Trader {
	/**
	 * StratConditions are the conditions to be met
	 * This constant keeps the four stratigies to a 2-d array
	 */
	private String[][] StratConditions = 
			{{"BTC", ">", "57000", "ETH", "<", "4350"},
			{"LTC", ">", "158", "DOGE", "<", "0.185"},
			{"SOL","<","125","ETH",">","4500"},
			{"ADA","<","1.58","DOGE",">","0.18"}};
	
	/**
	 * StratActions occurs if StratConditions are met
	 * 2-D array where each row represents a strategy
	 */
	private String[][] StratActions = 
		{{"buy", "2", "ETH"},
		{"buy", "10", "DOGE"},
		{"sell", "5", "SOL"},
		{"sell", "25", "ADA"}};
	
	/**
	 * CheckStrat checks if the strategy was met
	 * @param strat - and integer that represents Strategy A-D
	 * @param coins - String of coins that the broker received 
	 * @param prices - prices of coins attached in the above parameter 
	 * @return boolean to determine if strategy was met or not
	 */
	public boolean checkStrat(int strat, String[] coins, Double[] prices) {
		// get strat from conditions (0=A, 3=D)
		String[] checkStrat = StratConditions[strat];

		// parse doubles to check prices
		Double checkPrice1 = Double.parseDouble(checkStrat[2]);
		Double checkPrice2 = Double.parseDouble(checkStrat[5]);

		// get specific conditions
		String cond1 = checkStrat[1];
		String cond2 = checkStrat[4];

		// search the coin array for the wanted coin
		for (int i = 0; i < coins.length; i++) {
			if (checkStrat[0].equals(coins[i])) { // coin1 is found
				// check price of coin1
				Double price1 = prices[i];

				// compare price of coin1 to condition
				if (cond1.equals(">")) {
					if (price1 > checkPrice1) { // if condition 1 is met, check price 2
						for (int j = 0; j < coins.length; j++) {
							if (checkStrat[0].equals(coins[j])) { // coin2 is found
								// check price of coin2
								Double price2 = prices[j];

								if (cond2.equals("<")) { // if both conditions are true, return true
									if (price2 < checkPrice2) {
										return true;
									}
								} else if (cond2.equals(">")) { // if both conditions are true, return true
									if (price2 > checkPrice2) {
										return true;
									}
								}
							}
						}
					}
				}
				// case two if the condition is met
				else if (cond1.equals("<")) {
					if (price1 < checkPrice1) { // if condition 1 is met, check price 2
						for (int j = 0; j < coins.length; j++) {
							if (checkStrat[0].equals(coins[j])) { // coin2 is found
								// check price of coin2
								Double price2 = prices[j];

								if (cond2.equals(">")) { // if both conditions are true, return true
									if (price2 > checkPrice2) {
										return true;
									}
								} else if (cond2.equals("<")) { // if both conditions are true, return true
									if (price2 < checkPrice2) {
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		return false; // if no condition is met, return false
		
	}
	
	/**
	 * performTrade creates a trade if the conditions are met
	 * @param traderName - name of the trader 
	 * @param strat - strategy used by the trader
	 * @param coins - list of coins used by trader
	 * @param prices - list of prices connected by the coins ^
	 * @param date - the date of the trade 
	 */
	public void performTrade(String traderName, int strat, String[] coins, Double[] prices, String date) {
		//check if conditions were met
		if (checkStrat(strat, coins, prices)) { //if met
			String[] NewTrade = StratActions[strat]; //take the trade actions
			
			int index = indexOfCoin(NewTrade[2], coins); //find the index of the coin to be traded
			String StrStrat = changeNumbertoStrat(strat); //convert a number to a strat literal
			
			//create a log of the trade information
			String[] NewTradeLog = {traderName, StrStrat, NewTrade[2], NewTrade[0].toUpperCase(), NewTrade[1], Double.toString(prices[index]), date};
			
			//call traderdb object to write data
			TradeDB tradedbObj = new TradeDB();
			tradedbObj.writeData(NewTradeLog);
			
		} else { //condition was not met, some coins are missing
			System.out.println("The coin does not exist in the list, no trade was made");
		}
	}

	/** indexofcoin finds index of coin in string
	 * @param CoinName - name of coin to be found
	 * @param CoinList - list where coin exists
	 * @return index if found, or -1 if not found
	 */
	public int indexOfCoin (String CoinName, String[] CoinList) {
		//loop through entire list to find coin
		for (int i = 0; i < CoinList.length; i++) {
			if (CoinName.equals(CoinList[i])) {
				return i; //found
			}
		}
		return -1; //not found
	}
	
	/** changeNumbertoStrat Changing Number to String for Strategy Literal
	 * @param strat - the number literal of a strat
	 * @return string that contains the Strategy name
	 */
	public String changeNumbertoStrat (int strat) {
		if (strat == 0) {
			return "Strategy-A";
		} else if (strat == 1) {
			return "Strategy-B";
		} else if (strat == 2) {
			return "Strategy-C";
		} else if (strat == 3) {
			return "Strategy-D";
		} else {
			return null; //wrong input
		}
	}
	
	/** take group of brokers and perform trades for each broker 
	 * @param brokers - list of all brokers that have trades to be pending 
	 * @param brokerCoins - list of coins that the brokers accessed
	 * @param strategies - list of strategies that each broker has chosen
	 * @param numBrokers - number of broker in total
	 * @param allCoins - list of all coins, no duplicates
	 * @param allPrices - prices of these coins 
	 * @param date - todays date
	 */
	public void performTradesGroup (String[] brokers, String[][] brokerCoins, String[] strategies, int numBrokers, String[] allCoins, Double[] allPrices, String date) {		
		for (int i = 0; i < numBrokers; i++) { //for each broker
			
			//make own list of coins
			String[] newCoinArr = new String[10];
			Double[] newCoinPriceArr = new Double[10];
			int newArrLen = 0;
			
			//determine strats
			int strategy = -1;
			if (strategies[i].equals("Strategy-A")) {
				strategy = 0;
			} else if (strategies[i].equals("Strategy-B")) {
				strategy = 1;
			} else if (strategies[i].equals("Strategy-C")) {
				strategy = 2;
			} else if (strategies[i].equals("Strategy-D")) {
				strategy = 3;
			}
			
			//go through coins
			String[] setOfCoins = brokerCoins[i];
			for (int j = 0; j < allCoins.length; j++) { //check for coins in broker list
				for (String coin : setOfCoins) {			
					if (coin.equals(allCoins[j])) { //add coin if match is found
						newCoinArr[newArrLen] = allCoins[j];
						newCoinPriceArr[newArrLen] = allPrices[j];
						newArrLen++;
					}
				}
			}
			
			//cut array to length needed
			newCoinArr = Arrays.copyOfRange(newCoinArr, 0, newArrLen);
			newCoinPriceArr = Arrays.copyOfRange(newCoinPriceArr, 0, newArrLen);
			
			//perform trade with given information
			performTrade(brokers[i], strategy, newCoinArr, newCoinPriceArr, date);
		}
	}

}
