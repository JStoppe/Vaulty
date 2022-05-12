package de.muc.vaulty.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.security.*;
import de.muc.vaulty.*;

public class Control {
   private Model model;
   JDialog jd = new JDialog();
   JPanel jp = new JPanel();
   JButton ok = new JButton("OK");
   JButton cancel = new JButton("Abbrechen");
   
   private String name;
   private int age;
   private int birthYear;

   public Control(Model model) {
      this.model = model;
//      JDialog jd = new JDialog();
//      JPanel jp = new JPanel();
      jd.setLayout(new BorderLayout());
      jd.setTitle("SEND FUNDS");
      jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
      jd.add(jp);
//      this.jd = jd;
      
      name = "WalletA";
      age = 19;
      birthYear = 1994;
   }

   // all this simplistic control does is change the state of the model, that's it
//   public void startButtonActionPerformed(ActionEvent ae) {
////      model.setState(State.START);
////	   JDialog jd = new JDialog();
//	   	jd.setSize(350, 100);
////		showWin.setText("Dein Gewinn beträgt: " + Integer.toString(rp.getLastScore()));
////		jd.add(showWin, BorderLayout.PAGE_START);
//		jp.add(Box.createHorizontalGlue());
//		jp.add(ok);
//		jp.add(cancel);
//		jd.setModal(true);
//		jd.getRootPane().setDefaultButton(ok);
//		jd.setLocationRelativeTo(null);
//		jd.setResizable(false);
//		jd.setVisible(true);
//		if(ae.getSource() == ok) {
//   			jd.setVisible(false);
//	}
//
//   }
   
   public void addWallet(ActionEvent ae) {
	   
	   JPanel fields = new JPanel(new GridLayout(2, 1));
	   JTextField field = new JTextField(10);
	   JComboBox<String> comboBox = new JComboBox<>(new String[]{"Wallet", "Miner", "FullNode"});

	   fields.add(field);
	   fields.add(comboBox);

	   int result = JOptionPane.showConfirmDialog(null, fields, "Breakfast", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	   switch (result) {
	       case JOptionPane.OK_OPTION:
	           // Process the results...
	           break;
	   }
   }
   public void addMiner(ActionEvent ae) {
	   
   }
   public void addFullNode(ActionEvent ae) {
	   
   }
   
   public void changeWallet(ActionEvent ae) {  
	   String[] names = getWallets();
	   String input = (String) JOptionPane.showInputDialog(null, "Select Wallet",
	        "CHANGE WALLET", JOptionPane.QUESTION_MESSAGE, null, names, names[1]);

	   View.walletNameA.setText(input);
	   Wallet myWallet = new Wallet("jokeWallet");
	   for(Wallet w : VaultyChain.walletsNeu) {
		   if(w.username == input) {
			   myWallet = w;
			   break;
		   }
	   }
	   View.balanceA.setText(String.valueOf(myWallet.getBalance()));
	   myWallet = null;
	    
   }
   
   public String[] getWallets() {
	   String[] walletNames = new String[VaultyChain.walletsNeu.size()];
	   
	   int i = 0;
	   for(Wallet walletName : VaultyChain.walletsNeu) {
		   walletNames [i]= walletName.username;
		   i++;
	   }
	   for(String s : walletNames) {
		   System.out.println(s);
	   }
	   return walletNames;
   }
   
   public void changeWalletB(ActionEvent ae) {  
	   String[] names = getWallets();
	   String input = (String) JOptionPane.showInputDialog(null, "Select Wallet",
	        "CHANGE WALLET", JOptionPane.QUESTION_MESSAGE, null, names, names[1]);

	   View.walletNameB.setText(input);
	   Wallet myWallet = new Wallet("jokeWallet");
	   for(Wallet w : VaultyChain.walletsNeu) {
		   if(w.username == input) {
			   myWallet = w;
			   break;
		   }
	   }
	   View.balanceB.setText(String.valueOf(myWallet.getBalance()));
	   myWallet = null;  
   }
   
//   public String[] getWalletKey() {
//	   String[] walletBalance = new String[VaultyChain.walletsNeu.size()];
//	   int x=0;
//	   for(Wallet walletName : VaultyChain.walletsNeu) {
//	
////		   System.out.println(walletName);
//		   
//		   walletBalance [x]= String.valueOf(VaultyChain.wallets.get(StringUtil.getStringFromKey(Wallet.publicKey)).getBalance());
//		   x++;
//	   }	   
//	   return walletBalance;
//   }
	   
	
//   public String[] getWallets() {
//	   String[] walletNames = new String[VaultyChain.wallets.size()];
//	   for(Wallet walletName : VaultyChain.wallets) {
//		   
//		   String temp = walletName.username;
//		   for(int i=0; i<VaultyChain.wallets.size(); i++) {
//			  walletNames[i] = temp;
//		   }
//		   
//	   }
//	   return walletNames;
//	  }
//	   
	   
//	   String[] keys = new String[VaultyChain.wallets.size()];
//	   Object[] values = new Object[VaultyChain.wallets.size()];
//	   int index = 0;
//	   for (Map.Entry<String, Wallet> mapEntry : VaultyChain.wallets.entrySet()) {
//	       keys[index] = mapEntry.getKey();
//	       values[index] = mapEntry.getValue();
//	       index++;
//	   }
//	   
//	 
//
//	   	String[] arr1 = new String[VaultyChain.wallets.size()];
//	    String[] arr2 = new String[VaultyChain.wallets.size()];
//	    Set entries = VaultyChain.wallets.entrySet();
//	    Iterator entriesIterator = entries.iterator();
//
//	    int i = 0;
//	    while(entriesIterator.hasNext()){
//
//	        Map.Entry mapping = (Map.Entry) entriesIterator.next();
//
//	        arr1[i] = mapping.getKey().toString();
//	        arr2[i] = mapping.getValue().toString();
//
//	        i++;
//	    }
//	    
//	    String input = (String) JOptionPane.showInputDialog(null, "Select Wallet",
//		        "CHANGE WALLET", JOptionPane.QUESTION_MESSAGE, null, // Use
//		                                                                        // default
//		                                                                        // icon
//		        arr2, // Array of choices
//		        arr2[1]);
//	    
//	    System.out.println(arr2);
	   

	   
	   
	   //Liste erstellter Wallets (usernames)
//	   for() {
//	   String user = String.valueOf(Wallet.username);
//	   String[] 
//	   }
	   
//	   String walletName = VaultyChain.wallets.get(StringUtil.getStringFromKey(Wallet.publicKey)).username;
//	   String walletName = VaultyChain.wallets.String.valueOf(Wallet.username);

//	   System.out.println(user);
	   
//	   String[] choices = { "A", "B", "C", "D", "E", "F" };
//	    String input = (String) JOptionPane.showInputDialog(null, "Select Wallet",
//	        "CHANGE WALLET", JOptionPane.QUESTION_MESSAGE, null, // Use
//	                                                                        // default
//	                                                                        // icon
//	        choices, // Array of choices
//	        choices[1]); // Initial choice
//	    System.out.println(input);
	   
//   }
   
   public void selectRecipient(ActionEvent ae) {
	   String[] names = getWallets();
	   String input = (String) JOptionPane.showInputDialog(null, "Select Wallet",
	        "CHANGE WALLET", JOptionPane.QUESTION_MESSAGE, null, names, names[1]);

//	   View.walletNameA.setText(input);
	   Wallet otherWallet = new Wallet("jokeWallet");
	   for(Wallet w : VaultyChain.walletsNeu) {
		   if(w.username == input) {
			   otherWallet = w;
			   break;
		   }
	   }
//	   View.balanceA.setText(String.valueOf(myWallet.getBalance()));
//	   String store =;
//	   otherWallet = null;
	   

	    
   }
   
   public void transferVaulty(ActionEvent ae) {
	   
	   
	   // If valid Transaction 
	   JOptionPane.showMessageDialog(jd,
			    "Transaction successfully added to mempool",
			    "SEND FUNDS",
			    JOptionPane.PLAIN_MESSAGE);
   }
   
   public void showWalletInfo(ActionEvent ae) {
	   JOptionPane.showMessageDialog(
	            null, getPanel(), "WALLET INFO",
	                JOptionPane.INFORMATION_MESSAGE);
   }
	   
//	   	jd.setSize(350, 200);
//	   	jp.add(Box.createHorizontalGlue());
//		jp.add(ok);
//		jp.add(cancel);
//		JLabel success = new JLabel("Transaction successfully added to mempool");
//		jp.add(success);
//	   	jd.setModal(true);
//		jd.getRootPane().setDefaultButton(ok);
//	   	jd.setLocationRelativeTo(null);
//		jd.setResizable(false);
//		jd.setVisible(true);
//		if(ae.getSource() == ok) {
//   			jd.dispose();
//   			jd.setVisible(false);
	   	
   
   public void endButtonActionPerformed(ActionEvent ae) {
      model.setState(State.END);
   }

   private JPanel getPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
		JLabel nameLabel = getLabel("Name: " + name);
		JLabel adressLabel = getLabel("Adress: " + age);
		JLabel yearLabel = getLabel("Your Birth Year : " + birthYear);
		panel.add(nameLabel);
		panel.add(adressLabel);
		panel.add(yearLabel);

		return panel;
   }
   private JLabel getLabel(String title) {
       return new JLabel(title);
   }

   
   public static String selectedWallet(String input) {
	  
	   
	   	return input;
   }

}

