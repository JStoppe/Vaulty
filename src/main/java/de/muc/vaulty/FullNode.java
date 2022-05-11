package de.muc.vaulty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FullNode extends Node implements Serializable {

	public HashMap<String, TransactionOutput> UTXOset = new HashMap<String, TransactionOutput>();
	public ArrayList<Transaction> memPool = new ArrayList<Transaction>();
	public ArrayList<Block> blockchain = new ArrayList<Block>();

	public FullNode(String id) {
		super(id);
		super.NodeClass = "FullNode";
	}

	public ArrayList<Block> recievedBlocks = new ArrayList<Block>();
	public ArrayList<Transaction> recievedTransactions = new ArrayList<Transaction>();

	private void processReceivedBlocks() {
		Block rb = recievedBlocks.get(0);
		if (isBlockValid(rb)) {
			blockchain.add(rb);
			for (int i = 0; i < VaultyChain.Network.size(); i++) {
				if (VaultyChain.Network.get(i).NodeClass.equals("Miner")) {
					Miner m = (Miner) (VaultyChain.Network.get(i));
					m.newBlockValidatedByNote = true;
				}
			}
			ArrayList<FullNode> fullnodes = getOtherFullNodes();
			for (FullNode fn : fullnodes) {
				fn.recievedBlocks.add(rb);
			}
			updateUTXOs();
			updateMemPool();
		}
		recievedBlocks.remove(0);
	}

	private void processRecievedTransactions() {
		for (Transaction t : recievedTransactions) {
			if (StringUtil.validateTransaction(t, this)) {
				for (FullNode n : getOtherFullNodes()) {
					n.recievedTransactions.add(t);
				}
				if (!memPool.contains(t)) {
					for (TransactionOutput tout : t.inputs) {
						tout.locked = true;
						memPool.add(t);
					}
				}
			}
		}
		recievedTransactions.clear();
	}

	private void updateUTXOs() {
		
		for (Transaction transaction : this.blockchain.get(this.blockchain.size() - 1).transactions) {
			if(transaction.inputs != null) {
				for (TransactionOutput tout : transaction.inputs) 
					UTXOset.remove(tout.id);
			}
			for (TransactionOutput tout : transaction.outputs) 
				UTXOset.put(tout.id, tout);				
		}
	}

	private void updateMemPool() {
		// Blocks successfully mined

		for (Transaction transaction : this.blockchain.get(this.blockchain.size() - 1).transactions) {
			for (int i = 0; i < this.memPool.size(); i++) {
				if (transaction.transactionId.equals(memPool.get(i).transactionId)) {
					memPool.remove(i);
				}
			}
		}

		// delete Transactions included in this Block

	}

	public Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[VaultyChain.difficulty]).replace('\0', '0');
		HashMap<String, TransactionOutput> tempUTXOs = new HashMap<String, TransactionOutput>(); 
		// loop through blockchain to check hashes:
		for (int i = 1; i < blockchain.size(); i++) {
			if (i == 1) {
				tempUTXOs.put(blockchain.get(0).transactions.get(0).transactionId,
						blockchain.get(0).transactions.get(0).outputs.get(0));
			}
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// compare registered hash and calculated hash:
			if (!currentBlock.hash.equals(StringUtil.calculateHash(currentBlock))) {
				System.out.println("#Current Hashes not equal");
				return false;
			}
			// compare previous hash and registered previous hash
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("#Previous Hashes not equal");
				return false;
			}
			// check if hash is solved
			if (!currentBlock.hash.substring(0, VaultyChain.difficulty).equals(hashTarget)) {
				System.out.println("#This block hasn't been mined");
				return false;
			}

			// loop through blockchains transactions:
			TransactionOutput tempOutput;
			for (int t = 0; t < currentBlock.transactions.size(); t++) {
				Transaction currentTransaction = currentBlock.transactions.get(t);

				if (!StringUtil.verifySignature(currentTransaction)) {
					System.out.println("#Signature on Transaction(" + t + ") is Invalid");
					return false;
				}

				for (TransactionOutput input : currentTransaction.inputs) {
					tempOutput = tempUTXOs.get(input.id);

					if (tempOutput == null) {
						System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
						return false;
					}

					if (input.value != tempOutput.value) {
						System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
						return false;
					}

					tempUTXOs.remove(input.id);
				}

				for (TransactionOutput output : currentTransaction.outputs) {
					tempUTXOs.put(output.id, output);
				}

				if (currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
					System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
					return false;
				}
				if (currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
					System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
					return false;
				}

			}

		}
		return true;
	}

	private boolean isBlockValid(Block block) {
		if (block.merkleRoot != StringUtil.getMerkleRoot(block.transactions))
			return false;
		if(blockchain.size()>0) {
			if (block.previousHash != blockchain.get(blockchain.size() - 1).hash)
				return false;
		}
		String target = StringUtil.getDificultyString(VaultyChain.difficulty); // Create a string with difficulty * "0"
		if (StringUtil.calculateHash(block).substring(0, VaultyChain.difficulty).equals(target)) {
			return true;
		} else {
			return false;
		}
	}

	private ArrayList<FullNode> getOtherFullNodes() {
		ArrayList<FullNode> FullNodes = new ArrayList<FullNode>();
		for (Node n : VaultyChain.Network) {
			if (n.NodeClass.equals("FullNode") && n.nodeID != this.nodeID)
				FullNodes.add((FullNode) n);
		}
		return FullNodes;
	}

	public String getLastHash() {
		return this.blockchain.get(this.blockchain.size() - 1).hash;
	}

	@Override
	public void run() {
		while(true) {
			if (recievedTransactions.size() != 0)
				processRecievedTransactions();
			if (recievedBlocks.size() != 0)
				processReceivedBlocks();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}