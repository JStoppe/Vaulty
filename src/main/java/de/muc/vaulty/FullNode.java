package de.muc.vaulty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FullNode extends Node implements Serializable{
	
	public HashMap<String,TransactionOutput> UTXOset = new HashMap<String,TransactionOutput>();
	public ArrayList<Transaction> memPool = new ArrayList<Transaction>();
	public ArrayList<Block> blockchain = new ArrayList<Block>();
	
	public FullNode(String id) {
		super(id);
		super.NodeClass = "FullNode";
	}
	
	public ArrayList<Block> recievedBlocks = new ArrayList<Block>();
	public ArrayList<Transaction> recievedTransactions = new ArrayList<Transaction>();
	
	
	private void processResievedBlocks() {
		
	}
	
	private void processRecievedTransactions() { 
		for(Transaction t : recievedTransactions) {
			if(StringUtil.validateTransaction(t, this)) {
				for(Node n : VaultyChain.Network) {
					if(n.NodeClass == "FullNode") {
						FullNode fn = (FullNode)n;
						fn.recievedTransactions.add(t);
					}
				}
				for(TransactionOutput tout : t.inputs) {
					tout.locked = true;
					memPool.add(t);
				}
			}
		}
	}
	private void updateUTXOs() {
		// delete used Outputs
		// create new unspent Outputs
	}
	
	private void updateMemPool() {
		// Blocks successfully mined
		// delete Transactions included in this Block
	}
	
	private boolean isChainValid() {
		for(int i = 0; i<blockchain.size(); i++) {
			if(!isBlockValid(blockchain.get(i)))
				return false;
			if(i!=0)
				if(blockchain.get(i).previousHash != blockchain.get(i-1).previousHash)
					return false;
		}
		return true;
	}
	
	private boolean isBlockValid(Block block) {
		if(block.merkleRoot != StringUtil.getMerkleRoot(block.transactions))
			return false;
		String target = StringUtil.getDificultyString(VaultyChain.difficulty); //Create a string with difficulty * "0" 
		if(StringUtil.calculateHash(block).substring( 0, VaultyChain.difficulty).equals(target)) {
			return true;
		}
		else {
			return false;
		}
	}

	private void compareChain() {
		
	}
	
	@Override
	public void run() {
		
	}

	private FullNode getOtherFullNode() {
		ArrayList<FullNode> FullNodes = new ArrayList<FullNode>();
		for(Node n : VaultyChain.Network) {
			if(n.NodeClass == "FullNode" && n.nodeID != this.nodeID)
				FullNodes.add((FullNode)n);
		}
		Random random = new Random();
		return FullNodes.get(random.nextInt(FullNodes.size()));
	}
}

