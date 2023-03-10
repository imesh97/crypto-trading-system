package cryptoTrader.gui;

//added new packages
import cryptoTrader.utils.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * MainUI uses the pre-built UI class and adds connection to the backend
 * @author Gurshawn Singh Lehal (glehal) , Imesh Nimsitha (inimsith) , Uzair Muhammed Salim (usalim2) , Gunveer Vilkhu (gvilkhu)
 * @group 46
 */
public class MainUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L; // JFrame serial version long

	private static MainUI instance;
	private JPanel stats, chartPanel, tablePanel;

	// Should be a reference to a separate object in actual implementation
	private List<String> selectedList;

	private JTextArea selectedTickerList;
//	private JTextArea tickerList;
	private JTextArea tickerText;
	private JTextArea BrokerText;
	private JComboBox<String> strategyList;
	private Map<String, List<String>> brokersTickers = new HashMap<>();
	private Map<String, String> brokersStrategies = new HashMap<>();
	private List<String> selectedTickers = new ArrayList<>();
	private String selectedStrategy = "";
	private DefaultTableModel dtm;
	private JTable table;
	
	
	// added variables 
	private Broker BrokerObj;
	private DataFetcher fetcher;
	private AvailableCryptoList AvailCryptoList;
	private Trader traderObj;
	

	/**
	 * Access instance of main UI
	 * @return MainUI Instance of main UI
	 */
	public static MainUI getInstance() {
		if (instance == null)
			instance = new MainUI();

		return instance;
	}

	/**
	 * Constructor
	 * Creates main UI for accessing system
	 */
	private MainUI() {
		super("Crypto Trading Tool"); // Set window title
		
		//Construct Objects from other classes
		BrokerObj = new Broker();
		fetcher = new DataFetcher();
		AvailCryptoList = new AvailableCryptoList();
		traderObj = new Trader();

		JPanel north = new JPanel();

		JButton trade = new JButton("Perform Trade");
		trade.setActionCommand("refresh");
		trade.addActionListener(this);

		JPanel south = new JPanel();
		south.add(trade);

		dtm = new DefaultTableModel(new Object[] { "Trading Client", "Coin List", "Strategy Name" }, 1);
		table = new JTable(dtm);
		// table.setPreferredSize(new Dimension(600, 300));
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Trading Client Actions",
				TitledBorder.CENTER, TitledBorder.TOP));
		Vector<String> strategyNames = new Vector<String>();
		strategyNames.add("None");
		strategyNames.add("Strategy-A");
		strategyNames.add("Strategy-B");
		strategyNames.add("Strategy-C");
		strategyNames.add("Strategy-D");
		TableColumn strategyColumn = table.getColumnModel().getColumn(2);
		JComboBox comboBox = new JComboBox(strategyNames);
		strategyColumn.setCellEditor(new DefaultCellEditor(comboBox));
		JButton addRow = new JButton("Add Row");
		JButton remRow = new JButton("Remove Row");
		addRow.setActionCommand("addTableRow");
		addRow.addActionListener(this);
		remRow.setActionCommand("remTableRow");
		remRow.addActionListener(this);

		scrollPane.setPreferredSize(new Dimension(800, 300));
		table.setFillsViewportHeight(true);
		
		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		east.add(scrollPane);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(addRow);
		buttons.add(remRow);
		east.add(buttons);

		// Set charts region
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(1250, 650));
		
		stats = new JPanel();
		stats.setLayout(new GridLayout(2, 2));
		west.add(stats);

		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(west, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);
	}

	/**
	 * Updates stats component
	 * @param component JComponent Stats component
	 */
	public void updateStats(JComponent component) {
		stats.add(component);
		stats.revalidate();
	}

	/**
	 * Main method for initializing and showing main UI instance
	 * @param args String[] Optional arguments
	 */
	public static void main(String[] args) {
		JFrame frame = MainUI.getInstance(); // Initialize main UI
		frame.setSize(900, 600);
		frame.pack();
		frame.setVisible(true); // Show UI
	}

	/**
	 * Event listener for button clicks
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if ("refresh".equals(command)) {
			for (int count = 0; count < dtm.getRowCount(); count++){
					Object traderObject = dtm.getValueAt(count, 0);
					if (traderObject == null) {
						JOptionPane.showMessageDialog(this, "please fill in Trader name on line " + (count + 1) );
						return;
					}
					String traderName = traderObject.toString();
					Object coinObject = dtm.getValueAt(count, 1);
					if (coinObject == null) {
						JOptionPane.showMessageDialog(this, "please fill in cryptocoin list on line " + (count + 1) );
						return;
					}
					String[] coinNames = coinObject.toString().split(",");
					Object strategyObject = dtm.getValueAt(count, 2);
					if (strategyObject == null) {
						JOptionPane.showMessageDialog(this, "please fill in strategy name on line " + (count + 1) );
						return;
					}
					String strategyName = strategyObject.toString();
					//System.out.println(traderName + " " + Arrays.toString(coinNames) + " " + strategyName); //old output
					
					//send to broker class
					BrokerObj.addBroker(traderName, coinNames, strategyName);
	        }
			
			//get list of all coins no repeats
			String[] listOfCoins = BrokerObj.getListOfCoins();
			Double[] priceOfCoins;
			//System.out.println(Arrays.toString(listOfCoins)); //with nulls
			
			//Grab index of first null
			int indexNull = BrokerObj.getFirstNull(listOfCoins);
			
			//only access if array is no null
			if (indexNull != -1) {
				priceOfCoins = new Double[indexNull]; //price of coins
				listOfCoins = BrokerObj.getSubArray(listOfCoins, indexNull);
				System.out.println(Arrays.toString(listOfCoins)); //without nulls
				
				//fetch prices of coins
				String dateToday = BrokerObj.getDateToday();//today's date
				
				//grab prices for each coin
				for (int i = 0; i < indexNull; i++) {
					String coin = listOfCoins[i];
					coin = AvailCryptoList.getFullName(coin);
					coin = coin.toLowerCase();
					double price = fetcher.getPriceForCoin(coin, dateToday);
					priceOfCoins[i] = price;
				}
				
				System.out.println(Arrays.toString(priceOfCoins));
				
				//get broker data to send to trade
				String[] brokers = BrokerObj.getBrokers();
				String[][] brokerCoins = BrokerObj.getCoins();
				String[] brokerStrats = BrokerObj.getStrats();
				int numBrokers = BrokerObj.getLength();
				
				//send information to trade
				traderObj.performTradesGroup(brokers, brokerCoins, brokerStrats, numBrokers, listOfCoins, priceOfCoins, dateToday);
			} 
			else System.out.println("Erorr, IndexNull was -1"); //if the array was filled
			
			stats.removeAll();
			DataVisualizationCreator creator = new DataVisualizationCreator();
			creator.createCharts();
		} else if ("addTableRow".equals(command)) {
			dtm.addRow(new String[3]);
		} else if ("remTableRow".equals(command)) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1)
				dtm.removeRow(selectedRow);
		}
	}

}
