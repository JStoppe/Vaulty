package de.muc.vaulty;

import java.util.ArrayList;
import java.util.Date;

public class Miner extends Node{
	
	ArrayList<Transaction> useableTransactions = new ArrayList<Transaction>();
	Block mineableBlock;
	Block minedBlock;
	FullNode fullNode;
	int i=1;
	Wallet minerWallet = new Wallet("Miner " + i++);	
	boolean newBlockValidatedByNote = false;
	
	
	public Miner(String nodeID, Wallet walletID) {
		super(nodeID);
		super.NodeClass = "Miner";
		
	}
	
	
		
	public void buildBlock() {
		this.fullNode= StringUtil.getFullNode();
		if(this.fullNode.blockchain.isEmpty()) {
			this.mineableBlock = new Block("0");
		}
		else {
		this.mineableBlock = new Block(this.fullNode.getLastHash());
		}
		mineableBlock.transactions.add(new Transaction(VaultyChain.coinbase.publicKey, this.minerWallet.publicKey, 0, 0, null, VaultyChain.coinbase));
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
		if(this.mineableBlock.transactions.size()>0) {
		this.mineableBlock.transactions.get(0).value = this.calcMinerReward();
		}
		this.mineableBlock.transactions = useableTransactions;
	}
	
	public void sentToFull() {
		StringUtil.getFullNode().recievedBlocks.add(minedBlock);
		minedBlock = null;
	}
	
	public Block mineBlock(int difficulty) {
		int a = 0;
		mineableBlock.merkleRoot = StringUtil.getMerkleRoot(mineableBlock.transactions);
		String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		while(!mineableBlock.hash.substring( 0, difficulty).equals(target)) {
			while(!mineableBlock.hash.substring( 0, difficulty).equals(target) && a < 100) {
				mineableBlock.nonce ++;
				mineableBlock.hash = StringUtil.calculateHash(mineableBlock);
				a++;
			}
			if(newBlockValidatedByNote) {
				newBlockValidatedByNote = false;
				mineableBlock = null;
				return mineableBlock;
			}
			a = 0;
		}
		System.out.println("Block Mined!!! : " + mineableBlock.hash);
		minedBlock = mineableBlock;
		return minedBlock;
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
	
	public float calcMinerReward() {
		float temp=0;
		for(int i=0; i<mineableBlock.transactions.size(); i++) {
			temp = temp + mineableBlock.transactions.get(i).minerFee;
		}
		return temp + VaultyChain.blockMinedReward;
	}
	
	@Override
	
	public void run() {
		while(true) {
		buildBlock();
		
		mineBlock(VaultyChain.difficulty);
		if(minedBlock != null)
		sentToFull();
		}
	}
}
