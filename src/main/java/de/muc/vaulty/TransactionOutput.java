package de.muc.vaulty;

import java.security.PublicKey;

public class TransactionOutput {
	public String id;
	public PublicKey reciepient; //also known as the new owner of these coins.
	public float value; //the amount of coins they own
	public String parentTransactionId; //the id of the transaction this output was created in
	public boolean locked;
	//Constructor
	public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) {
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		this.id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient)+Float.toString(value)+parentTransactionId);
		this.locked = false;
	}
	
	//Check if coin belongs to you
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == reciepient);
	}

	@Override
	public String toString() {
		return "TransactionOutput [id=" + id + ", reciepient=" + reciepient + ", value=" + value
				+ ", parentTransactionId=" + parentTransactionId + "]";
	}
	
	
}
