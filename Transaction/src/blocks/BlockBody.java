package blocks;

import java.util.ArrayList;

import transactions.Transaction;

public class BlockBody {
	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	public BlockBody(Transaction coinbase) {
		transactions.add(coinbase);
	}
	
	public void addTransactionToBlock(Transaction transaction) {
		transactions.add(transaction);
	}
}
