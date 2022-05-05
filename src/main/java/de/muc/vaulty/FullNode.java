package de.muc.vaulty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class FullNode extends Node implements Serializable{
	public HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
	public ArrayList<Transaction> memPool = new ArrayList<Transaction>();
	
	public FullNode(String id) {
		super(id);
		super.NodeClass = "FullNode";
	}
	
	public ArrayList<Block> recievedBlocks = new ArrayList<Block>();
	
	
	
	private void processRB() {
		
	}
	
	private void updateUTXOs() {
		// delete used Outputs
		// create new unspent Outputs
	}
	
	private void updateMemPool() {
		// Blocks successfully mined
		// delete Transactions included in this Block
	}
	
	private void checkChain() {
		// isChainValid Methode from VaultyChain
	}
	
	private void checkBlock() {
		
	}

	private void compareChain() {
		
	}
	
	@Override
	public void run() {
		
	}
}

