package de.muc.vaulty;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Wallet {

	public String username;
	public PrivateKey privateKey;
	public PublicKey publicKey;
	public FullNode fullNode;

	public HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

	public Wallet(String username) {

		this.username = username;
		generateKeyPair();
	}

	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); // 256
			KeyPair keyPair = keyGen.generateKeyPair();
			// Set the public and private keys from the keyPair
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
			VaultyChain.wallets.put(StringUtil.getStringFromKey(this.publicKey), this);
			VaultyChain.walletsNeu.add(this);
			this.fullNode = StringUtil.getFullNode();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public float getBalance() {
		float total = 0;
		for (Map.Entry<String, TransactionOutput> item : fullNode.UTXOset.entrySet()) {
			TransactionOutput UTXO = item.getValue();
			if (UTXO.isMine(publicKey) && !UTXO.locked) { // if output belongs to me ( if coins belong to me )
				UTXOs.put(UTXO.id, UTXO); // add it to our list of unspent transactions.
				total += UTXO.value;
			}
		}
		return total;
	}

	public Transaction sendFunds(PublicKey _recipient, float value, float fee) {

		fullNode = StringUtil.getFullNode();

		if (getBalance() < value + fee) {
			System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
			return null;
		}
		ArrayList<TransactionOutput> inputs = new ArrayList<TransactionOutput>();

		float total = 0;
		for (Map.Entry<String, TransactionOutput> item : UTXOs.entrySet()) {
			TransactionOutput UTXO = item.getValue();
			total += UTXO.value;
			inputs.add(UTXO);
			if (total >= value + fee)
				break;
		}

		Transaction newTransaction = new Transaction(publicKey, _recipient, value, fee, inputs, this);
		System.out.println("Hier steht die Transaction ID von SendFunds: " + newTransaction.transactionId);
		StringUtil.generateSignature(privateKey, newTransaction);
		StringUtil.validateTransaction(newTransaction, fullNode);
		
		System.out.println("+++++++++++++++now the Transaction should be added.+++++++++++++++++" + fullNode.nodeID);
		System.out.println(fullNode.recievedTransactions.size());
		fullNode.recievedTransactions.add(newTransaction);
		System.out.println(fullNode.recievedTransactions.size());
		StringUtil.getFullNode().recievedTransactions.add(newTransaction);
		System.out.println("+++++++++++++++now the Transaction should be added2.+++++++++++++++++" + fullNode.nodeID);
		return newTransaction;
	}

}
