package de.muc.vaulty;

import java.util.ArrayList;
import java.util.Date;

public class Miner extends Node {

	ArrayList<Transaction> useableTransactions = new ArrayList<Transaction>();
	Block mineableBlock;
	Block minedBlock;
	FullNode fullNode;
	int minerCount = 1;
	Wallet minerWallet;
	boolean newBlockValidatedByNote = false;

	public Miner(String nodeID, String walletName) {
		super(nodeID);
		super.NodeClass = "Miner";
		minerWallet = new Wallet(walletName);
	}

	public void buildBlock() {
		this.fullNode = StringUtil.getFullNode();
		System.out.println(nodeID + " buids block and finds fullnode: " + fullNode.nodeID );
		System.out.println();
		for(Transaction t : fullNode.memPool) {
			System.out.println("mempool: ("+nodeID + ")  " + t);
		}
		if (this.fullNode.blockchain.isEmpty()) {
			this.mineableBlock = new Block("0");
		} else {
			this.mineableBlock = new Block(this.fullNode.getLastHash());
		}
		mineableBlock.transactions.add(new Transaction(VaultyChain.coinbase.publicKey, this.minerWallet.publicKey, 0, 0,
				null, VaultyChain.coinbase));
		int i = 0;
		while (this.mineableBlock.transactions.size() < 10
				&& this.mineableBlock.timeStamp + 5000 > new Date().getTime()) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (fullNode.memPool.isEmpty())
				continue;
			if (fullNode.memPool.size() > i) {
				if (fullNode.memPool.get(i) == null)
					continue;

				if (!mineableBlock.transactions.contains(fullNode.memPool.get(i))) {
					addTransaction(fullNode.memPool.get(i));
					i++;
					System.out.println(i);
				}
			}

		}
		
		if (this.mineableBlock.transactions.size() > 0) {
			this.mineableBlock.transactions.get(0).value = this.calcMinerReward();
			this.mineableBlock.transactions.get(0).outputs.get(0).value = this.calcMinerReward();
		}
		System.out.println(nodeID + " got minable block(transactions in Block): " );
		for(Transaction t : mineableBlock.transactions)
			System.out.println(t);
	}

	public void sentToFull() {
		fullNode.recievedBlocks.add(minedBlock);
		System.out.println(nodeID + " send Block " + minedBlock.hash + "to " + fullNode.nodeID);
		minedBlock = null;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Block mineBlock(int difficulty) {
		int a = 0;
		mineableBlock.merkleRoot = StringUtil.getMerkleRoot(mineableBlock.transactions);
		String target = StringUtil.getDificultyString(difficulty); // Create a string with difficulty * "0"
		while (!mineableBlock.hash.substring(0, difficulty).equals(target)) {
			while (!mineableBlock.hash.substring(0, difficulty).equals(target) && a < 100) {
				mineableBlock.nonce++;
				mineableBlock.hash = StringUtil.calculateHash(mineableBlock);
				a++;
			}
			if (newBlockValidatedByNote) {
				newBlockValidatedByNote = false;
				mineableBlock = null;
				return mineableBlock;
			}
			a = 0;
		}
		System.out.println(nodeID + "mined Block : " + mineableBlock.hash + "!!!!!!!!!!");

		minedBlock = mineableBlock;
		return minedBlock;
	}

	public boolean addTransaction(Transaction transaction) {
		// process transaction and check if valid, unless block is genesis block then
		// ignore.
		if (transaction == null)
			return false;
		if ((!"0".equals(mineableBlock.previousHash))) {
			if ((StringUtil.validateTransaction(transaction, StringUtil.getFullNode()) != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}

		mineableBlock.transactions.add(transaction);
		System.out.println(nodeID + ": Transaction Successfully added to Block: " + transaction + "\n");
		return true;
	}

	public float calcMinerReward() {
		float temp = 0;
		for (int i = 0; i < mineableBlock.transactions.size(); i++) {
			temp = temp + mineableBlock.transactions.get(i).minerFee;
		}
		return temp + VaultyChain.blockMinedReward;
	}

	@Override

	public void run() {
		while (true) {
			buildBlock();

			mineBlock(VaultyChain.difficulty);
			if (minedBlock != null)
				sentToFull();
		}
	}
}
