package pointOfSale;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class CardPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L; // Added to satisfy the
														// compiler
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final String RECEIPT_PATH = "Files/Receipts";
	private static final String RECEIPT_LIST = RECEIPT_PATH + "/ReceiptList";
	private static final String[] tabText = { "", "Tip: ", "Card Number: ",
			"Expiration Date (MMYY): ", "CVV: ", "Address: ", "Zipcode: "};

	private static JPanel tabPanel = new JPanel(new GridLayout(1, 4));
	private static JPanel optionPanel = new JPanel(new GridLayout(1, 3));
			
	private static JTextField display = new JTextField("", 20);

	private static JPanel buttonPanel = new JPanel(new GridLayout(4, 3));
	private static JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
	
	private static MenuButton tipButton = null, cardNumButton = null,
			cardExpButton = null, cvvButton = null, zipButton = null, addrButton = null;
	private static String tabStrings[] = { "", "", "", "", "", "", "" };
	private static String current = "";
	private static int selection = 0;
	private static File receiptSave = null;
	private static String firstLine = "";
	private static boolean isAdmin;
	private static String swipe = "";
	private static KeyBarsListener listen = null;

	private static boolean reset = false;
	private static boolean luhnErr = false;
	private static boolean digErr = false;
	private static boolean retrn = true;
	private static boolean reject = false;
	private static boolean loaded = false;
	private static boolean backspace = false;
	private static String temp = "";

	private static MenuButton one = null;
	private static MenuButton two = null;
	private static MenuButton thr = null;
	private static MenuButton fur = null;
	private static MenuButton fiv = null;
	private static MenuButton six = null;
	private static MenuButton sev = null;
	private static MenuButton eig = null;
	private static MenuButton nin = null;
	private static MenuButton zer = null;
	private static MenuButton dot = null;

	private static Timer timer = null;
	private static boolean limiter = true;
	private static int counter = 2;
	private static int deleter = 0;
	

	public CardPanel(boolean isAdmin__) {
		isAdmin = isAdmin__;
		tabPanel.removeAll();
		display.removeAll();
		optionPanel.removeAll();
		display.removeKeyListener(listen);
		listen = new KeyBarsListener();
		display.addKeyListener(listen);
		display.addMouseListener(new MouseAdapter(){
			
			public void mouseDragged(MouseEvent e){
				if(e.getID() == MouseEvent.MOUSE_DRAGGED){
					System.out.println("lala");
				}
			}
			
			public void mousePressed(MouseEvent e){
				caretPosition();
				
			}
			
			public void mouseReleased(MouseEvent e){
				if(display.getSelectedText() != null){
						deleter = display.getSelectedText().length();
					}
				else{
					deleter = 0;
				}
				
			}
	
		});
		buttonPanel.removeAll();
		bottomPanel.removeAll();
		this.removeAll();
		setLayout(new GridLayout(4, 1));
		setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,
				DARK_CHAMPAGNE));

		tipButton = new MenuButton("Tip", "13", this);
		cardNumButton = new MenuButton("Card Number", "14", this);
		cardExpButton = new MenuButton("Exp. Date", "15", this);
		
		cvvButton = new MenuButton("CVV", "18", this);
		addrButton = new MenuButton("Address", "19", this);
		zipButton = new MenuButton("ZipCode", "20", this);

		optionPanel.add(cvvButton);
		optionPanel.add(zipButton);
		optionPanel.add(addrButton);
		
		tabPanel.add(tipButton);
		tabPanel.add(cardNumButton);
		tabPanel.add(cardExpButton);
		tabPanel.add(optionPanel);
				
		one = new MenuButton("1", "1", this);
		two = new MenuButton("2", "2", this);
		thr = new MenuButton("3", "3", this);
		fur = new MenuButton("4", "4", this);
		fiv = new MenuButton("5", "5", this);
		six = new MenuButton("6", "6", this);
		sev = new MenuButton("7", "7", this);
		eig = new MenuButton("8", "8", this);
		nin = new MenuButton("9", "9", this);
		zer = new MenuButton("0", "0", this);
		dot = new MenuButton(".", "11", this);

		buttonPanel.add(one);
		buttonPanel.add(two);
		buttonPanel.add(thr);

		buttonPanel.add(fur);
		buttonPanel.add(fiv);
		buttonPanel.add(six);

		buttonPanel.add(sev);
		buttonPanel.add(eig);
		buttonPanel.add(nin);

		buttonPanel.add(new MenuButton("<-", "10", this));
		buttonPanel.add(zer);
		buttonPanel.add(dot);

		if (isAdmin) {
			bottomPanel.add(new MenuButton("VOID", "17", this));
		} else {
			Tools.addBlankSpace(bottomPanel, 1);
		}
		Tools.addBlankSpace(bottomPanel, 1);
		bottomPanel.add(new MenuButton("Done", "16", this));

		add(tabPanel);
		add(display);
		add(buttonPanel);
		add(bottomPanel);

		timer = new Timer(5000, this);
		
	}

	private class KeyBarsListener extends KeyAdapter {
	
		public void keyPressed(KeyEvent e){
			Scanner s = new Scanner(display.getText());
			s.useDelimiter(": ");
			String temp = "";
			//String first = s.next();
			//s.close();
			//s = new Scanner(display.getText());
			while(s.hasNext()){
				temp = s.next();
				
			}
	        /*if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && first != null && deleter > current.length()){
	        	display.setText(first + ":  ");
	        	deleter = 0;
	        }*/
			if( e.getKeyCode() == KeyEvent.VK_BACK_SPACE && (temp.matches("Card Number") || 
					temp.matches("Tip") || temp.matches("Expiration Date (MMYY)") || temp.matches("CVV") || temp.matches("Address") || temp.matches("Zipcode"))){
				display.setText(temp + ":  ");
				
			}
			s.close();
		}
		
		public void keyReleased(KeyEvent e){
			if(backspace && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
				String d = display.getText();
				display.setText(d.substring(0, d.length()-1));
				backspace = false;
			}
			if(deleter > 0){
				if(tipButton.isVisible()){
					tabStrings[selection] = ".00";
					current = ".00";
					display.setText(tabText[selection] + tabStrings[selection]);
					counter = 2;
					deleter = 0;
					
				}
				
			
				
			}
			
		}
		public void keyTyped(KeyEvent e) {
			Response response1 = null;

			if (e.getKeyChar() == '\n') {
				// switch for generics or encrypted mag reader
				
				String readerType = checkReader(swipe, response1);
				swipe = "";
				if (readerType.equals("IPAD100KB")) { 	
					String[] four = { getInvoiceNo() + "", getInvoiceNo() + "",
							"POS BRAVO v1.0", tabStrings[2], tabStrings[3],
							tabStrings[0], tabStrings[0], tabStrings[4], tabStrings[5], tabStrings[6] };
					response1 = new Response(4, four);

					processXML(response1);

				
				} else {
					if (firstLine.equalsIgnoreCase("OPEN")
							&& validate(tabStrings[2], tabStrings[3])) {
						// get response
						// String four[] = {"16", "16", "POS BRAVO v1.0",
						// "9500030000081C20001A",
						// "2B11F45ABEE6A6B288A76FED3BBCCE63130970C742BDD607D75F09821AAF2C6482F1AB593E0A97BA",
						// "2.00", "2.00"};
						String[] one = { getInvoiceNo() + "",
								getInvoiceNo() + "", "POS BRAVO v1.0",
								tabStrings[2], tabStrings[3], tabStrings[0],
								tabStrings[0], tabStrings[4], tabStrings[5], tabStrings[6] };
						response1 = new Response(1, one);
						processXML(response1);

						
					
					} else {
						if (!validate(tabStrings[2], tabStrings[3])) {
							display.setText("Expired Card");
							reject = true;
							timer.start();
						
						}

						else if (!firstLine.equalsIgnoreCase("OPEN")) {
							display.setText("Closed Ticket");
							reject = true;
							timer.start();
							
					
						}
					}
					// System.out.println(tabStrings[2]);
				}
			} else {
				String check = String.valueOf(e.getKeyChar());
				boolean cond = (e.getKeyChar() == KeyEvent.VK_BACK_SPACE);
				if(!tipButton.isVisible()){
				
				System.out.println(check);
				swipe += check;
				
				if(deleter > 0){
		    		
		    		if(deleter > current.length()){
		    			display.setText(tabText[selection]);
		    			deleter = deleter - tabText[selection].length();
		    		}
		    		current = current.substring(0, current.length()- deleter);
		    		System.out.print("current = s" + current + "deleter = " + deleter );
		    		deleter = 0;
		    		cond = false;
		    	}
				if(check.matches("[0-9]")){
					
					current += check;
					
				}
				else if(selection == 5){
					if(!(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
					current += check;
					}
				}
				
		    	
			    if(current.length() > 0 && cond){
			    	

				    	current = current.substring(0, current.length()-1);
			    	
			    }
			    
			    
					/*if(check.matches("[0-9]")){
						
					
					}*/

			//	return;
				
				
				
				//current = swipe;
			}
			//backspace = true;
				else{
					if(deleter > 0){

						return;
					}
					
				    Scanner run = new Scanner(display.getText() + " ");
				    run.useDelimiter(": ");
				    String temp = "";
				    while(run.hasNext()){
				    	temp = run.next();
				    }
				    run.close();
				    System.out.println("counter: = " + counter + " condition = " + (temp.length() == current.length()) + " temp = " + temp + "current = " + current);
					if(cond){
					  if((temp.length() == current.length())){
						if(counter > 0){
						counter--;
						}
						current = current.substring(0, current.length()-1);
						System.out.println(current);
					  }
						return;
					}
					if(counter < 2){
						if(check.matches("[0-9.]")){
							boolean condtem = check.equals(".");
							if(!condtem && !current.contains(".")){
								current += check;
								
							}
							else if(condtem && !current.contains(".")){
							current += check;
							counter = 0;
							}
							else if(!condtem){ 
								counter++;
								current += check;		
							}
							
						}
						else{
							backspace = true;
						}
					}
					else{
						backspace = true;
					}
				System.out.println(current);}
			}
		}
	  
	}

	private void processXML(Response response1) {
		saveTransaction(response1.getXML(), response1.getResponse(), 1);
		if (response1.getResponse().contains("Approved")) {
			ProcessPanel.closeReceipt("PROGRESS");
			System.out.println("HERE");
			tabStrings = new String[] { "", "", "", "", "", "", "" };
			display.setText("");
			loaded = false;
			SystemInit.setTransactionScreen();
		} else {
			String[] response = getText(response1.getResponse()).split("/");
			// Scanner regex = new Scanner(response1.getResponse().trim());
			// String error =
			// regex.findInLine("<TextResponse>No Live Cards on Test Merchant ID Allowed[\\.]</TextResponse>");
			// System.out.println(response1.getResponse());
			// System.out.println(error);
			String responsef = response[0];
			if(response.length > 1){
				if (response[1].equals("001007") || response[1].equals("003010")) {
					responsef = response[0] + " Please redo the transaction.";

				}
			}
			display.setText("Rejected: " + responsef/*
													 * + error.substring(
													 * "<TextResponse>"
													 * .length(), error.length()
													 * -
													 * "</TextResponse>".length
													 * ())
													 */);
			// regex.close();

			tabStrings[2] = "";
			tabStrings[3] = "";
			current = "";
			timer.start();
			reject = true;
			retrn = false;
			Tools.update(display);
		}
		return;

	}

	// need to change with different card reader
	private static void parseSwipe() {
		Scanner regex = new Scanner(swipe);
		System.out.println(swipe);
		String temp = regex.findInLine(";\\d{10,20}=");
		System.out.println(temp);
		try {
			tabStrings[2] = temp.substring(1, temp.length() - 1);
		} catch (NullPointerException | StringIndexOutOfBoundsException e) {
			display.setText("Error Swiping");
			// do i need tools.update() here?
		}
		regex.close();
		regex = new Scanner(swipe);
		temp = regex.findInLine("=\\d*");

		// System.out.println("********* " + temp);
		temp = temp.substring(1, 5);
		// System.out.println("********* " + temp);
		temp = temp.substring(2) + temp.subSequence(0, 2);

		// System.out.println("********* " + temp);
		tabStrings[3] = temp;
		regex.close();// %B4003000123456781^TEST/MPS^15121010000000000?;4003000123456781=15125025432198712345?

	}

	private static void parseSwipeE() {
		Scanner regex = new Scanner(swipe);

		System.out.println(swipe);
		// String[] firstsplit = swipe.split("\\|");
		System.out.println("-----------------------------");
		// for (String s : firstsplit) {
		// System.out.println(s);
		// if(s.matches("")){

		// }
		// }
		System.out.println("-----------------------------");
		StringBuilder temp = new StringBuilder(regex.findInLine("3~.*\\|4"));
		System.out.println(temp);
		// System.out.println(temp + "\nEmpty");
		tabStrings[3] = temp.substring(2, temp.length() - 2);

		System.out.println(tabStrings[3]);
		regex.close();
		regex = new Scanner(swipe);
		
		// System.out.println("********* " + temp);
		// temp = temp.substring(1, 5);
		// System.out.println("********* " + temp);
		// temp = temp.substring(2) + temp.subSequence(0, 2);

		// System.out.println("********* " + temp);

		temp.replace(0, temp.length(), regex.findInLine("11~.*\\|12"));
		
		tabStrings[2] = temp.substring(3, temp.length() - 3);
		System.out.println(tabStrings[2]);
		
		regex.close();
		
		regex = new Scanner(swipe);
	
		if(regex.findInLine("23~.*\\|.{0,1}") != null){
		temp.replace(0, temp.length(), regex.findInLine("23~.*\\|.{0,1}"));
		tabStrings[4] = temp.substring(3, temp.length()-2);
		}
		System.out.println(temp);
		

		
		regex.close();// %B4003000123456781^TEST/MPS^15121010000000000?;4003000123456781=15125025432198712345?
	}

	public static void loadReciept(File receipt) {
		// tabStrings = new String[] { "", "", "", "" };
		
		receiptSave = receipt;
		Scanner reader = null;
		Scanner regex = null;
		try {
			reader = new Scanner(receipt);
			String read = reader.nextLine();
			firstLine = read;
			if (read != null)
				tipButton.setVisible(read.contains("OPEN") ? false : true);

			cardNumButton.setVisible(!tipButton.isVisible());
			cardExpButton.setVisible(!tipButton.isVisible());
			zipButton.setVisible(!tipButton.isVisible());
			cvvButton.setVisible(!tipButton.isVisible());
			addrButton.setVisible(!tipButton.isVisible());

			while (reader.hasNextLine()) {
				read = reader.nextLine();
				regex = new Scanner(read);
				if (read.toLowerCase().contains("total")) {
					tabStrings[0] = regex.findInLine("\\d*\\.\\d{0,2}");
					System.out.println(tabStrings[0]);
				} else if (read.toLowerCase().contains("tip")) {
					tabStrings[1] = regex.findInLine("\\d*\\.\\d{0,2}");
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selection = tipButton.isVisible() ? 1 : 2;
		current = tabStrings[selection];

		display.setText(tabText[selection] + tabStrings[selection]);
		Tools.update(display);
		//appendHint()
	}

	private boolean checkReady(String num, String exp) {
		System.out.println(num + " and " + exp);
		if (num.matches("\\d{10,17}") && exp.matches("\\d{4}")) {

			return true;
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
	 if(loaded){	
		int command = 100;
		
		swipe = "";
		if (event.getActionCommand() != null) {
			if(Integer.parseInt(event.getActionCommand()) > 11){
				retrn = true;
			}
			if (retrn) {
				command = Integer.parseInt(event.getActionCommand());
				
			} else {
				retrn = true;
			}
		}
		if (reset) {
			tabText[selection] = temp;
			selection = 3;
			current = tabStrings[selection];
			reset = false;
			timer.stop();
			// enableButtons();
		} else if (luhnErr) {
			tabText[selection] = temp;
			selection = 2;
			current = tabStrings[selection];
			luhnErr = false;
			timer.stop();
			// enableButtons();

		} else if (digErr) {
			current = tabStrings[selection];
			tabText[selection] = temp;
			digErr = false;
			timer.stop();

		} else if (reject) {
			reject = false;
			timer.stop();
		}
		if (command < 10) {
			if (!limiter) {
				current += command;
			} else if (counter < 2) {
				current += command;
				counter++;
			}
		} else {
			switch (command) {
			case 10:
				DisplayFocus(true);
				if (current.length() > 0) {
					if (current.charAt(current.length() - 1) == '.') {
						limiter = false;
					}
					current = current.substring(0, current.length() - 1);
					if (counter > 0 && tipButton.isVisible()) {
						counter--;
					}

				}
				break;
			case 11:
				if (!current.contains(".") && selection == 1) {
					current += ".";
					limiter = true;
				}
				break;
			// case 12: tabStrings[selection] = current;
			// selection = 0;
			// current = tabStrings[selection];
			// break;
			case 13:
				DisplayFocus(true);
				tabStrings[selection] = current;
				selection = 1;
				current = tabStrings[selection];
				
				break;
			case 14:
				DisplayFocus(true);
				tabStrings[selection] = current;
				selection = 2;
				current = tabStrings[selection];
				
				break;
			case 15:
				DisplayFocus(true);
				tabStrings[selection] = current;
				System.out.println("*** " + selection);
				System.out.println("**** " + tabStrings[selection]);
				selection = 3;
				current = tabStrings[selection];

				break;
			case 16:
				Response response1 = new Response();
				response1.setIDnPas("merchantID1");
				tabStrings[selection] = current;
				
				System.out.println("Done************");
				System.out.println(tabStrings[selection]);
				
				// final String temp2 = tabStrings[2], temp3 = tabStrings[3];
				//
				// current = tabStrings[2];
				// System.out.println("HERE!");
				// System.out.println(temp2 + " first " + temp3);

				if (checkReady(tabStrings[2], tabStrings[3])) {
					//
					System.out.println("*** " + firstLine);
					System.out.println("value of the expiration date:"
							+ tabStrings[3]);
					System.out.println(validate(tabStrings[2], tabStrings[3]));
					if (firstLine.equalsIgnoreCase("OPEN")
							&& validate(tabStrings[2], tabStrings[3])) {
						System.out.println("Passed the test");
						// get response
						String[] one = { getInvoiceNo() + "",
								getInvoiceNo() + "", "POS BRAVO v1.0",
								tabStrings[2], tabStrings[3], tabStrings[0],
								tabStrings[0], tabStrings[4], tabStrings[5], tabStrings[6] };
						response1 = new Response(1, one);
						saveTransaction(response1.getXML(),
								response1.getResponse(), 1);
						if (response1.getResponse().contains("Approved")) {
							ProcessPanel.closeReceipt("PROGRESS");
							System.out.println("HERE");
							tabStrings = new String[] { "", "", "", "", "", "", "" };
							display.setText("");
							loaded = false;
							SystemInit.setTransactionScreen();
						} else {
							String response = getText(response1.getResponse());
							// Scanner regex = new
							// Scanner(response1.getResponse());
							// String error =
							// regex.findInLine("<TextResponse>[\\.a-zA-Z \\d]*</TextResponse>");
							display.setText("Rejected: " + response/*
																	 * + error.
																	 * substring
																	 * (
																	 * "<TextResponse>"
																	 * .
																	 * length(),
																	 * error
																	 * .length()
																	 * -
																	 * "</TextResponse>"
																	 * .
																	 * length())
																	 */);
							// regex.close();
							tabStrings[2] = "";
							tabStrings[3] = "";
							tabStrings[4] = "";
							tabStrings[5] = "";
							tabStrings[6] = "";
							current = "";
							timer.start();
							reject = true;
							retrn = false;
							Tools.update(display);
						}
						return;
					} else {
						if (!validate(tabStrings[2], tabStrings[3])) {
							System.out.println("Validate failed");
							if (luhnErr) {
								current = "Invalid Card Number";

							} else {
								current = "Expired Card";
								reset = true;
							}
							retrn = false;
							// disableButtons();
							temp = tabText[selection]; // fulfills same purpose
														// as return
							tabText[selection] = "";

						}
					}
				} else {
					if (!tipButton.isVisible()) {
						current = "Incorrect digits of Card number (10-17) or Expiration date (4)";
						temp = tabText[selection];
						tabText[selection] = "";
						digErr = true;
						retrn = false;
						// disableButtons();
					}
				
				}
				if (firstLine.equalsIgnoreCase("PROGRESS")
						&& tabStrings[1].matches("\\d{0,}\\.\\d{2}")) {
					System.out.println("Done************");

					String[] two = num2();
					two[3] = tabStrings[0];
					two[4] = tabStrings[0];
					two[5] = tabStrings[1];
					Response response2 = new Response(2, two);
					saveTransaction(response2.getXML(),
							response2.getResponse(), 2);
					if (response2.getResponse().contains("Approved")) {
						updateTip(tabStrings[1]);
						ProcessPanel.closeReceipt("SWIPED");
						tabStrings = new String[] { "", "", "", "", "", "", "" };
						display.setText("Transaction Complete");
						loaded = false;
						SystemInit.setTransactionScreen();
					} else {
						String response = getText(response2.getResponse());
						// Scanner regex = new Scanner(response2.getResponse());
						// String error =
						// regex.findInLine("<TextResponse>[\\.a-zA-Z \\d]*</TextResponse>");
						display.setText("Rejected: " + response/*
																 * +
																 * error.substring
																 * (
																 * "<TextResponse>"
																 * .length(),
																 * error
																 * .length() -
																 * "</TextResponse>"
																 * .length())
																 */);
						// regex.close();
						tabStrings[1] = ".00";
						tabStrings[2] = "";
						tabStrings[3] = "";
						tabStrings[4] = "";
						tabStrings[5] = "";
						tabStrings[6] = "";
						current = ".00";
						counter = 0;
						limiter = false;
						Tools.update(display);
						timer.start();
						reject = true;
					}
					return;
				}
				else{
			      if (tipButton.isVisible()) {
                   		display.setText("Please check the gratuity format (x.xx)");
						timer.start();
						reject = true;
						retrn = false;
						Tools.update(display);
						return;
					}

				}

				break;
			case 17:
				if (firstLine.equalsIgnoreCase("VOID")
						|| firstLine.equalsIgnoreCase("PROGRESS")) {
					Response response3 = new Response(3, num3());
					saveTransaction(response3.getXML(),
							response3.getResponse(), 3);
					if (response3.getResponse().contains("Approved")) {
						ProcessPanel.closeReceipt("VOIDED");
						tabStrings = new String[] { "", "", "", "", "", "", "" };
						display.setText("Voided");
						loaded = false;
						SystemInit.setTransactionScreen();
						
					} else {
						String response = getText(response3.getResponse());
						// Scanner regex = new Scanner(response3.getResponse());
						// String error =
						// regex.findInLine("<TextResponse>[\\.a-zA-Z \\d]*</TextResponse>");
						display.setText("Unable to void: " + response/*
																	 * error.
																	 * substring
																	 * (
																	 * "<TextResponse>"
																	 * .
																	 * length(),
																	 * error
																	 * .length()
																	 * -
																	 * "</TextResponse>"
																	 * .
																	 * length())
																	 */);
						// regex.close();
						
						tabStrings[2] = "";
						tabStrings[3] = "";
						current = "";
						Tools.update(display);
					}
					return;
				}
				break;
			case 18:
				DisplayFocus(true);
				tabStrings[selection] = current;
				selection = 4;
				current = tabStrings[selection];
				break;
			case 19:
				DisplayFocus(true);
				tabStrings[selection] = current;
				selection = 5;
				current = tabStrings[selection];
				break;
			case 20:
				DisplayFocus(true);
				tabStrings[selection] = current;
				selection = 6;
				current = tabStrings[selection];
				break;
			}
		}
		// for keyboard input you will have to parse the text in display when
		// the tabs are changed

		System.out.println(current);
		display.setText(tabText[selection] + current);
		
		if (digErr || luhnErr || reset) {
			timer.start();
		}
		if (command == 100) {
			retrn = true;
			DisplayFocus(true);
		}
		

		//appendHint();
		Tools.update(display);
		
	 }
	}

	protected static void saveTransaction(String sent, String response, int rev) {
		/*
		 * PrintWriter printer = null;
		 * 
		 * try { printer = new PrintWriter("Files/Transaction/Sent/" + rev + "/"
		 * + receiptSave.getName() + ".xml"); printer.println(sent);
		 * printer.flush(); printer.close(); printer = new
		 * PrintWriter("Files/Transaction/Response/" + rev + "/" +
		 * receiptSave.getName() + ".xml"); printer.println(response);
		 * printer.flush(); printer.close(); } catch (FileNotFoundException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); }
		 */

		saveTransaction(sent, response, rev, receiptSave);
	}

	protected static void saveTransaction(String sent, String response,
			int rev, File receipt) {
		PrintWriter printer = null;

		try {
			printer = new PrintWriter("Files/Transaction/Sent/" + rev + "/"
					+ receipt.getName() + ".xml");
			printer.println(sent);
			printer.flush();
			printer.close();
			printer = new PrintWriter("Files/Transaction/Response/" + rev + "/"
					+ receipt.getName() + ".xml");
			printer.println(response);
			printer.flush();
			printer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void updateTip(String tip) {
		File file = receiptSave;
		Scanner reader = null;
		PrintWriter printer = null;
		String toPrint = "";

		try {
			reader = new Scanner(file);
			String read = reader.nextLine();
			toPrint += read;
			while (reader.hasNextLine()) {
				read = reader.nextLine();
				if (read.contains("Tip")) {
					toPrint += ("\n" + Tools.toMoney(tip)
							+ manualTab(Tools.toMoney(tip)) + "Tip")
							.replaceAll("\\.\\.", ".");
				} else {
					toPrint += "\n" + read;
				}
			}
			reader.close();
			printer = new PrintWriter(file);
			printer.println(toPrint);
			printer.flush();
			printer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static int getInvoiceNo() {
		File dir = new File("Files/Transaction/Sent/1/");
		return dir.list().length + 1;
	}

	private String[] num2() {
		String toReturn[] = new String[8];

		Scanner reader = null;
		Scanner regex = null;

		try {
			reader = new Scanner(new File("Files/Transaction/Response/1/"
					+ receiptSave.getName() + ".xml"));
			while (reader.hasNextLine()) {
				String read = reader.nextLine();
				read = read.trim();
				regex = new Scanner(read);
				if (read.contains("AuthCode")) {
					toReturn[6] = regex
							.findInLine("<AuthCode>[\\da-zA-Z]*</AuthCode>");
					toReturn[6] = toReturn[6].substring("<AuthCode>".length(),
							toReturn[6].length() - "</AuthCode>".length());
				} else if (read.contains("AcqRefData")) {
					System.out.println(read);
					toReturn[7] = regex
							.findInLine("<AcqRefData>[\\d a-zA-Z]*</AcqRefData>");
					System.out.println(toReturn[7]);
					toReturn[7] = toReturn[7].substring(
							"<AcqRefData>".length(), toReturn[7].length()
									- "</AcqRefData>".length());
				} else if (read.contains("RecordNo")) {
					toReturn[2] = regex.findInLine("<RecordNo>.*</RecordNo>");
					toReturn[2] = toReturn[2].substring("<RecordNo>".length(),
							toReturn[2].length() - "</RecordNo>".length());
				}
			}
			reader.close();
			reader = new Scanner(new File("Files/Transaction/Sent/1/"
					+ receiptSave.getName() + ".xml"));
			while (reader.hasNextLine()) {
				String read = reader.nextLine();
				read = read.trim();
				regex = new Scanner(read);
				if (read.contains("InvoiceNo")) {
					toReturn[0] = regex
							.findInLine("<InvoiceNo>[\\da-zA-Z]*</InvoiceNo>");
					toReturn[0] = toReturn[0].substring("<InvoiceNo>".length(),
							toReturn[0].length() - "</InvoiceNo>".length());
				} else if (read.contains("RefNo")) {
					toReturn[1] = regex
							.findInLine("<RefNo>[\\da-zA-Z]*</RefNo>");
					toReturn[1] = toReturn[1].substring("<RefNo>".length(),
							toReturn[1].length() - "</RefNo>".length());
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return toReturn;
	}

	private static String[] num3() {
		return num3(receiptSave);
	}

	// changed to Static
	protected static String[] num3(File receiptSaveTemp) {
		String toReturn[] = new String[9];

		String file1 = "Files/Transaction/", file2 = "/"
				+ receiptSaveTemp.getName() + ".xml";
		Scanner reader = null;
		Scanner regex = null;

		try {
			reader = new Scanner(new File(file1 + "Sent/1" + file2));
		} catch (FileNotFoundException e) {
			try {
				reader = new Scanner(new File(file1 + "Sent/2" + file2));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				System.out.println("HERE2");
				return null;
			}
		}
		while (reader.hasNextLine()) {
			String read = reader.nextLine();
			regex = new Scanner(read);
			if (read.contains("MerchantID")) {
				toReturn[8] = regex
						.findInLine("<MerchantID>[\\da-zA-Z =]+</MerchantID>");
				toReturn[8] = toReturn[8].substring("<MerchantID>".length(),
						toReturn[8].length() - "</MerchantID>".length());
				Pattern p = Pattern.compile("[\\da-zA-z=]+");
				Matcher m = p.matcher(toReturn[8]);
			    m.find();
			    toReturn[8] = m.group();
				
			}
			else if (read.contains("InvoiceNo")) {
				toReturn[0] = regex
						.findInLine("<InvoiceNo>[\\da-zA-Z]*</InvoiceNo>");
				toReturn[0] = toReturn[0].substring("<InvoiceNo>".length(),
						toReturn[0].length() - "</InvoiceNo>".length());
			} else if (read.contains("RefNo")) {
				// try getting from recieved
				toReturn[1] = regex.findInLine("<RefNo>[\\da-zA-Z]*</RefNo>");
				toReturn[1] = toReturn[1].substring("<RefNo>".length(),
						toReturn[1].length() - "</RefNo>".length());
			} else if (read.contains("Purchase")) {
				toReturn[4] = regex
						.findInLine("<Purchase>[\\d\\.]*</Purchase>");
				toReturn[4] = toReturn[4].substring("<Purchase>".length(),
						toReturn[4].length() - "</Purchase>".length());
			} else if (read.contains("Memo")) {
				toReturn[2] = regex.findInLine("<Memo>[\\da-zA-Z \\.]*</Memo>");
				toReturn[2] = toReturn[2].substring("<Memo>".length(),
						toReturn[2].length() - "</Memo>".length());
			} else if (read.contains("RecordNo")) {
				toReturn[3] = regex.findInLine("<RecordNo>.*</RecordNo>");
				toReturn[3] = toReturn[3].substring("<RecordNo>".length(),
						toReturn[3].length() - "</RecordNo>".length());
			} else if (read.contains("AuthCode")) {
				toReturn[7] = regex
						.findInLine("<AuthCode>[\\da-zA-Z]</AuthCode>");
				toReturn[7] = toReturn[7].substring("<AuthCode>".length(),
						toReturn[7].length() - "</AuthCode>".length());
			} else if (read.contains("AcqRefData")) {
				toReturn[5] = regex
						.findInLine("<AcqRefData>[\\da-zA-Z ]*</AcqRefData>");
				toReturn[5] = toReturn[5].substring("<AcqRefData>".length(),
						toReturn[5].length() - "</AcqRefData>".length());
			}
		}
		reader.close();
		try {
			reader = new Scanner(new File(file1 + "Response/1" + file2));
		} catch (FileNotFoundException e) {
			try {
				reader = new Scanner(new File(file1 + "Response/2" + file2));
			} catch (FileNotFoundException e1) {
				System.out.println("HERE3");
				return null;
			}
		}
		while (reader.hasNextLine()) {
			String read = reader.nextLine();
			regex = new Scanner(read);
			if (read.contains("InvoiceNo")) {
				toReturn[0] = regex
						.findInLine("<InvoiceNo>[\\da-zA-Z]*</InvoiceNo>");
				toReturn[0] = toReturn[0].substring("<InvoiceNo>".length(),
						toReturn[0].length() - "</InvoiceNo>".length());
			} else if (read.contains("RefNo")) {
				// try getting from recieved
				toReturn[1] = regex.findInLine("<RefNo>[\\da-zA-Z]*</RefNo>");
				toReturn[1] = toReturn[1].substring("<RefNo>".length(),
						toReturn[1].length() - "</RefNo>".length());
			} else if (read.contains("Purchase")) {
				toReturn[4] = regex
						.findInLine("<Purchase>[\\d\\.]*</Purchase>");
				toReturn[4] = toReturn[4].substring("<Purchase>".length(),
						toReturn[4].length() - "</Purchase>".length());
			} else if (read.contains("Memo")) {
				toReturn[2] = regex.findInLine("<Memo>[\\da-zA-Z \\.]*</Memo>");
				toReturn[2] = toReturn[2].substring("<Memo>".length(),
						toReturn[2].length() - "</Memo>".length());
			} else if (read.contains("RecordNo")) {
				toReturn[3] = regex.findInLine("<RecordNo>.*</RecordNo>");
				toReturn[3] = toReturn[3].substring("<RecordNo>".length(),
						toReturn[3].length() - "</RecordNo>".length());
			} else if (read.contains("ProcessData")) {
				toReturn[6] = regex
						.findInLine("<ProcessData>[\\d\\|]*</ProcessData>");
				toReturn[6] = toReturn[6].substring("<ProcessData>".length(),
						toReturn[6].length() - "</ProcessData>".length());
			} else if (read.contains("AuthCode")) {
				toReturn[7] = regex
						.findInLine("<AuthCode>[\\da-zA-Z]*</AuthCode>");
				toReturn[7] = toReturn[7].substring("<AuthCode>".length(),
						toReturn[7].length() - "</AuthCode>".length());
			} else if (read.contains("AcqRefData")) {
				toReturn[5] = regex
						.findInLine("<AcqRefData>[\\da-zA-Z ]*</AcqRefData>");
				toReturn[5] = toReturn[5].substring("<AcqRefData>".length(),
						toReturn[5].length() - "</AcqRefData>".length());
			}

		}
		reader.close();

		return toReturn;
	}

	private static boolean validate(String cardNum, String expDate) {
		boolean toReturn = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		if ((cal.get(Calendar.YEAR) % 100) <= Integer.parseInt(expDate
				.substring(2))) {

			// possible error because Calendar.Month does not return correct
			// date
			if ((cal.get(Calendar.MONTH) + 1) <= Integer.parseInt(expDate
					.substring(0, 2))) {

				toReturn = true;
			}
		}
		if (toReturn) {
			int sum1 = 0;
			for (int i = cardNum.length() - 1; i >= 0; i -= 2) {
				sum1 += cardNum.charAt(i) - 48;
			}
			String sum2 = "";
			for (int i = cardNum.length() - 2; i >= 0; i -= 2) {
				sum2 += 2 * (cardNum.charAt(i) - 48);
			}
			int sum3 = 0;
			for (int i = 0; i < sum2.length(); i++) {
				sum3 += sum2.charAt(i) - 48;
			}
			System.out.println(sum1 + " + " + sum3);
			if ((sum3 + sum1) % 10 == 0) {
				System.out.println("validated");
				return true;
			} else {
				luhnErr = true;
				return false;
			}
		}
		return toReturn;
	}

	private static String manualTab(String entry) {
		String tab = "";
		for (int count = 0; count < 15 - entry.length(); count++)
			tab += " ";
		return tab;
	}

	protected static String getText(String in) {
		System.out.println(in);
		String[] lines = in.split("\n");
		String temp = "";
		for (int i = 0; i < lines.length; i++) {
			System.out.println(lines[i] + ":Get:");
			// <DSIXReturnCode>000000</DSIXReturnCode>
			if (lines[i].contains("DSIXReturnCode")) {
				temp = new Scanner(lines[i])
						.findInLine("<DSIXReturnCode>[\\da-zA-Z\\s\\.]*</DSIXReturnCode>");
				temp = temp.substring("<DSIXReturnCode>".length(),
						temp.length() - "</DSIXReturnCode>".length());

			}
			if (lines[i].contains("TextResponse")) {
				String toReturn = new Scanner(lines[i])
						.findInLine("<TextResponse>[\\da-zA-Z\\s\\.\\S]*</TextResponse>");

				toReturn = toReturn.substring("<TextResponse>".length(),
						toReturn.length() - "</TextResponse>".length());
				return toReturn + "/" + temp;
			} else if (lines[i].equals("Timeout")) {
				return "Client Timeout. Please check the Internet Connection.";
			}
			else if(lines[i].equals("Timeout2")){
				return "Please Try Again.";
			}
		}

		return "Error";
	}
/*	private static void appendHint(){
		String check = display.getText();
		int x= 0, y = 0;
		if(check.contains("Card Number")){
			if(check.length() == "Card Number: ".length()){
				x = check.length();
				check = check + "1234567891...";
				y = check.length();
			}
		}
		else if(check.contains("Tip")){
			if(check.length() == "Tip: ".length()){
				x = check.length();
				check = check + "123.12";
				y = check.length();
			}
		}
		else if(check.contains("Expiration Date")){
			if(check.length() == "Expiration Date (MMYY): ".length()){
				x = check.length();
				check = check + "1215";
				y = check.length();
			}
		}
		if(x != 0 || y != 0){
		display.setText(check);
		display.select(x, y);
		display.requestFocus();
		display.setSelectedTextColor(new Color(51, 51 ,51, 51));
		display.setSelectionColor(Color.white);
		display.setCaretColor(Color.white);
		}
	}
	*/
	public static void DisplayFocus(boolean set) {
		if (set) {
			display.requestFocusInWindow();
		}

	}

	public static String checkReader(String swipe, Response res) {
		res = new Response();

		swipe = swipe.substring(2, 11);

		String retrn;
		switch (swipe) {
		case "IPAD100KB":
			retrn = "IPAD100KB";
			res.setIDnPas("merchantID2");

			System.out.println("sent encrypted");
			// set merchantID and password
			parseSwipeE();
			return retrn;
		default:
			retrn = "nonecrypted";
			res.setIDnPas("merchantID1");
			System.out.println("sent not encrypted");
			parseSwipe();
			return retrn;
		}
	}

	public static void reset() {
		current = "";
		swipe = "";
		tabStrings[2] = "";
		tabStrings[3] = "";
		tabStrings[4] = "";
		tabStrings[5] = "";
		tabStrings[6] = "";
		tabText[2] = "Card Number: ";
		tabText[3] = "Expiration Date (MMYY): ";
		tabText[4] = "CVV: ";
		tabText[5] = "Address: ";
		tabText[6] = "Zipcode: ";
		digErr = false;
		reset = false;
		luhnErr = false;
		counter = 2;
		if(timer != null){
			if(timer.isRunning()){
				timer.stop();
			}
		}
	}

	public static void setLimiter(boolean cond) {
		limiter = cond;
	}
	public static void setLoaded(boolean cond){
		loaded = cond;
	}

	public static boolean tipVisible() {
		return tipButton.isVisible();
	}
	public static void timerCheck(){
		if(timer != null){
			if(timer.isRunning()){
				timer.stop();
			}
		}
	}
	public static void clearDisplay() {
		display.setText("");
	}
	public static void caretPosition(){
		String c = display.getText();
		display.setCaretPosition(c.length());
	}
}
