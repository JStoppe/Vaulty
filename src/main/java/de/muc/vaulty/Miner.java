package de.muc.vaulty;

import java.util.ArrayList;
import java.util.Date;

public class Miner extends Node{
	
	ArrayList<Transaction> useableTransactions = new ArrayList<Transaction>();
	Block mineableBlock;
	FullNode fullNode;
	
	
	public Miner(String nodeID, Wallet walletID) {
		super(nodeID);
		super.NodeClass = "Miner";
		
	}
		
	public void buildBlock() {
		this.fullNode= StringUtil.getFullNode();	
		this.mineableBlock = new Block(this.fullNode.getLastHash());
		while(this.mineableBlock.transactions.size() < 10 && this.mineableBlock.timeStamp + 200000 < new Date().getTime()) {
			int i = 0;
			if(fullNode.memPool.get(i) == null ){
				break;
			}
			else {
				addTransaction(fullNode.memPool.get(i));
				i++;
			}
			
		}
		this.mineableBlock.transactions = useableTransactions;
		
	}
	
	public void getTransaction() {
		
	}
	
	public void sentToFull() {
		
	}
	
	public void mineBlock(int difficulty) {
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = StringUtil.calculateHash(this);
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	
	public boolean addTransaction(Transaction transaction) {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) return false;		
		if((!"0".equals(mineableBlock.previousHash))) {
			if((StringUtil.validateTransaction(transaction,StringUtil.getFullNode()) != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}

		useableTransactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}
	
}
