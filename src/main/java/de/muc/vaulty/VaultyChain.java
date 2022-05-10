package de.muc.vaulty;
import java.security.Security;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.HashMap;
//import com.google.gson.GsonBuilder;
import java.util.Map;

import com.google.gson.GsonBuilder;

import de.muc.vaulty.view.MainFrame;

public class VaultyChain {
	
	public static ArrayList<Node> Network = new ArrayList<Node>();
	
	public static int difficulty = 3;
	public static float minimumTransaction = 0.1f;
	public static Wallet walletA;
	public static Wallet walletB;
	public static Transaction genesisTransaction;
	public static HashMap<String, Wallet> wallets = new HashMap<String,Wallet>();
	public static float blockMinedReward = 20.0f;
	public static Wallet coinbase;
	

	public static void main(String[] args) {	
		//add our blocks to the blockchain ArrayList:
		
		new MainFrame();
		
	}
	
	
	
}

/*
 * public static void main(String[] args) {	
		//add our blocks to the blockchain ArrayList:
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider
		
		//walletA = new Wallet();
		//walletB = new Wallet();
		
		//System.out.println("Private and public keys:");
		//System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		//System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
		
		createGenesis();
		
		//Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5);
		//transaction.signature = transaction.generateSignature(walletA.privateKey);
		
		//System.out.println("Is signature verified:");
		//System.out.println(transaction.verifiySignature());
		
	}
 */

//System.out.println("Trying to Mine block 1... ");
//addBlock(new Block("Hi im the first block", "0"));
