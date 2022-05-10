package de.muc.vaulty;
import java.security.*;
import java.util.ArrayList;

public class Transaction {
	
	public String transactionId; //Contains a hash of transaction*
	public PublicKey sender; //Senders address/public key.
	public PublicKey reciepient; //Recipients address/public key.
	public float value; //Contains the amount we wish to send to the recipient.
	public byte[] signature; //This is to prevent anybody else from spending funds in our wallet.
	public float minerFee;
	public Wallet senderWallet;
	
	public ArrayList<TransactionOutput> inputs = new ArrayList<TransactionOutput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	private static int sequence = 0; //A rough count of how many transactions have been generated 
	
	// Constructor: 
	public Transaction(PublicKey from, PublicKey to, float value, float minerFee,  ArrayList<TransactionOutput> inputs, Wallet senderWallet) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
		this.minerFee = minerFee;
		this.senderWallet = senderWallet;
		this.genarateTransactionOutputs();
	}
	
	public void genarateTransactionOutputs() {
		//Generate transaction outputs:
		float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
		transactionId = calulateHash();
		outputs.add(new TransactionOutput( this.reciepient, value,transactionId)); //send value to recipient
		outputs.add(new TransactionOutput( this.sender, leftOver,transactionId)); //send the left over 'change' back to sender		
	}
	
	public float getInputsValue() {
		float total = 0;
		for(TransactionOutput i : inputs) {
			total += i.value;
		}
		return total;
	}
	
	
	public float getOutputsValue() {
		float total = 0;
		for(TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}
	
	private String calulateHash() {
		sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
		return StringUtil.applySha256(
				StringUtil.getStringFromKey(sender) +
				StringUtil.getStringFromKey(reciepient) +
				Float.toString(value) + sequence
				);
	}
	
	public String toString() {
		return "\nTransaction ID: " + this.transactionId + " \nfrom: " + StringUtil.getStringFromKey(this.sender) + " \nto: " 
	+ StringUtil.getStringFromKey(this.reciepient) + "\nvalue:" + this.value + "\n" 
				+ VaultyChain.wallets.get(StringUtil.getStringFromKey(this.sender)).username +" (current balance: " + VaultyChain.wallets.get(StringUtil.getStringFromKey(this.sender)).getBalance() 
				+ ") sends " + VaultyChain.wallets.get(StringUtil.getStringFromKey(this.reciepient)).username + " (current balance: " + VaultyChain.wallets.get(StringUtil.getStringFromKey(this.reciepient)).getBalance() 
				+ ")   " + this.value
				+ " Vaulty coins.";
	}
}
