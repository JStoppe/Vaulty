package de.muc.vaulty;
import java.security.Security;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.HashMap;
//import com.google.gson.GsonBuilder;
import java.util.Map;

import com.google.gson.GsonBuilder;

public class VaultyChain {
	
	public static ArrayList<Node> Network = new ArrayList<Node>();
	public static int difficulty = 5;
	public static float minimumTransaction = 0.1f;
	public static HashMap<String, Wallet> wallets = new HashMap<String,Wallet>();
	public static ArrayList<Wallet> walletsNeu = new ArrayList<Wallet>();
	public static float blockMinedReward = 20.0f;
	public static Wallet coinbase; 
	

	public static void main(String[] args) {	
//		//add our blocks to the blockchain ArrayList:
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		
		FullNode node01 = new FullNode("node01");
		FullNode node02 = new FullNode("node02");
		
		coinbase = new Wallet("coinbase");
		Wallet walletA = new Wallet("Wallet A");
		Wallet walletB = new Wallet("Wallet B");

		Miner miner01 = new Miner("miner01","Miner 01");
		Miner miner02 = new Miner("miner02","Miner 02");
		
		node01.start();
		node02.start();
		miner01.start();
		miner02.start();
		
		java.awt.EventQueue.invokeLater(new Runnable() {
		       public void run() {
		          de.muc.vaulty.view.MVC.createAndShowUI();
		       }
		    });
		
		while(true) {
			try {
				Thread.sleep(10000);
				StringUtil.printBlockchainToTerminal(node01.blockchain);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}


//	}
	
	
	


//public static void main(String[] args) {	
//		//add our blocks to the blockchain ArrayList:
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
//		
////		Wallet walletA = new Wallet("WalletA");
////		Wallet walletB = new Wallet("WalletB");
//		
//		//System.out.println("Private and public keys:");
//		//System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
//		//System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
//		
//		//createGenesis();
//		
////		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5);
////		transaction.signature = transaction.generateSignature(walletA.privateKey);
//		
//		//System.out.println("Is signature verified:");
//		//System.out.println(transaction.verifiySignature());
//		
//	}


//System.out.println("Trying to Mine block 1... ");
//addBlock(new Block("Hi im the first block", "0"));
