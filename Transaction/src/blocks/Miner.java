package blocks;

import transactions.Transaction;

public class Miner {
	Block block;
	
	public Miner(Transaction coinbase) {
		block = new Block(coinbase);
	}
	
	public boolean addTransactionToBlock(Transaction transaction) {
		if(verifyTransaction()) {
			block.getBlockBody().addTransactionToBlock(transaction);
			return true;
		}else {
			return false;
		}
		
	}
	
	private boolean verifyTransaction() {
		return true;
	}
}
