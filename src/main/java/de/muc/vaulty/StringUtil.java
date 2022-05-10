package de.muc.vaulty;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.Random;

public class StringUtil {
	
	//Applies Sha256 to a string and returns the result. 
	public static String applySha256(String input){
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
	        
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//Applies ECDSA Signature and returns the result ( as bytes ).
	public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
		Signature dsa;
		byte[] output = new byte[0];
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}
	
	//Verifies a String signature 
	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//Short hand helper to turn Object into a json string
	public static String getJson(Object o) {
		return new GsonBuilder().setPrettyPrinting().create().toJson(o);
	}
	
	//Returns difficulty string target, to compare to hash. eg difficulty of 5 will return "00000"  
	public static String getDificultyString(int difficulty) {
		return new String(new char[difficulty]).replace('\0', '0');
	}
	
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	public static String getMerkleRoot(ArrayList<Transaction> transactions) {
		int count = transactions.size();
		
		List<String> previousTreeLayer = new ArrayList<String>();
		for(Transaction transaction : transactions) {
			previousTreeLayer.add(transaction.transactionId);
		}
		List<String> treeLayer = previousTreeLayer;
		
		while(count > 1) {
			treeLayer = new ArrayList<String>();
			for(int i=1; i < previousTreeLayer.size(); i+=2) {
				treeLayer.add(applySha256(previousTreeLayer.get(i-1) + previousTreeLayer.get(i)));
			}
			count = treeLayer.size();
			previousTreeLayer = treeLayer;
		}
		
		String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
		return merkleRoot;
	}
	
	public static void printBlockchainToTerminal(ArrayList<Block> blockchain) {
		System.out.println("\n##############################################################\nThe Vaulty Block Chain:\n##############################################################\\n");
		for(Block b : blockchain) {
			System.out.println("\n\n\n##############################################################\nBlock_ID: " + b.hash);
			for(int i = 0; i < b.transactions.size(); i++) {
				System.out.println(b.transactions.get(i));
			}
			System.out.println("##############################################################");
		}
	}
	
	public static String calculateHash(Block block) {
		String calculatedhash = StringUtil.applySha256( 
				block.previousHash +
				Long.toString(block.timeStamp) +
				Integer.toString(block.nonce) + 
				block.merkleRoot
				);
		return calculatedhash;
	}
	
	public static FullNode getFullNode() {
		ArrayList<FullNode> FullNodes = new ArrayList<FullNode>();
		for(Node n : VaultyChain.Network) {
			if(n.NodeClass == "FullNode")
				FullNodes.add((FullNode)n);
		}
		Random random = new Random();
		return FullNodes.get(random.nextInt(FullNodes.size()));
	}

	public static boolean validateTransaction(Transaction t, FullNode fn) {
		if(StringUtil.verifySignature(t) == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
		for(TransactionOutput i : t.inputs) {
			if(!fn.UTXOset.containsValue(i)) {
				System.out.println("TransactionInput not found in UTXOset!" + i);
				return false;
			}
		}
		if(t.value < VaultyChain.minimumTransaction) {
			System.out.println("Transaction value to small! Minimum transaction value is " + VaultyChain.minimumTransaction);
			return false;
		}
		if(t.senderWallet.getBalance() < t.value + t.minerFee) {
			System.out.println("You don't have the funds to execute this transaction");
			return false;
		}
		return true;
	}
	
	public static void generateSignature(PrivateKey privateKey, Transaction t) {
		String data = StringUtil.getStringFromKey(t.sender) + StringUtil.getStringFromKey(t.reciepient) + Float.toString(t.value) + Float.toString(t.minerFee);
		t.signature = StringUtil.applyECDSASig(privateKey,data);		
	}
	
	public static boolean verifySignature(Transaction t) {
		String data = StringUtil.getStringFromKey(t.sender) + StringUtil.getStringFromKey(t.reciepient) + Float.toString(t.value) + Float.toString(t.minerFee);
		return StringUtil.verifyECDSASig(t.sender, data, t.signature);
	}
}
