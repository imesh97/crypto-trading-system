package cryptoTrader.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cryptoTrader.utils.Login;

/**
 * UI for logging in to access main UI
 * @author Gurshawn Singh Lehal (glehal) , Imesh Nimsitha (inimsith) , Uzair Muhammed Salim (usalim2) , Gunveer Vilkhu (gvilkhu)
 */
public class LoginUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L; // JFrame serial version long
	private static LoginUI instance; // Private instance variable
	
	// Private instance variables for UI components
	private static JLabel usernameLabel;
	private static JTextField usernameFill;
	private static JLabel passwordLabel;
	private static JPasswordField passwordFill;
	private static JButton loginBtn;
	
	/**
	 * Access instance of login UI
	 * @return LoginUI Instance
	 */
	public static LoginUI getInstance() {
		if (instance == null) instance = new LoginUI();
		return instance;
	}
	
	/**
	 * Constructor
	 * Creates new login UI with components
	 */
	private LoginUI() {
		super("Login - Crypto Trading Tool"); // Custom title
		JPanel loginPanel = new JPanel(); // New JPanel component
		loginPanel.setLayout(null);
		
		usernameLabel = new JLabel("Username"); // "Username" label
		usernameLabel.setBounds(25, 25, 80, 25);
		
		usernameFill = new JTextField(25); // Username text fill box
		usernameFill.setBounds(100, 25, 175, 25);
		
		passwordLabel = new JLabel("Password"); // "Password" label
		passwordLabel.setBounds(25, 60, 80, 25);
		
		passwordFill = new JPasswordField(25); // Password text fill box
		passwordFill.setBounds(100, 60, 175, 25);
		
		loginBtn = new JButton("Login"); // Login button
		loginBtn.setBounds(25, 100, 250, 25);
		loginBtn.addActionListener(this); // Register action listener
		
		loginPanel.add(usernameLabel); // Add every component to JPanel
		loginPanel.add(usernameFill);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordFill);
		loginPanel.add(loginBtn);
		
		getContentPane().add(loginPanel); // Add JPanel to component pane
	}
	
	/**
	 * Main method for initializing and showing login UI instance
	 * @param args String[] Additional arguments
	 */
	public static void main(String[] args) {
		JFrame loginFrame = LoginUI.getInstance(); // Initialize instance
		loginFrame.setSize(new Dimension(325, 200));
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setVisible(true); // Show UI to user
	}
	
	/**
	 * Event listener for login button click
	 * @param e ActionEvent The action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Retrieve inputed username and password from text fill boxes
		String setUsername = usernameFill.getText();
		String setPassword = String.valueOf(passwordFill.getPassword());
		
		Login login = new Login(setUsername, setPassword); // New login
		if (login.attempt()) { // Perform login attempt -> true if success
			LoginUI.getInstance().setVisible(false);
			LoginUI.getInstance().dispose(); // Close login UI
			MainUI.main(null); // Run main UI
		}
		else { // Login attempt unsuccessful -> show error message
			JOptionPane.showMessageDialog(null, "The username or password was incorrect.");
		}
	}
	
}
