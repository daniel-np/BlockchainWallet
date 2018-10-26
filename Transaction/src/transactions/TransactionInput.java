package transactions;

public class TransactionInput {
	private String transactionOutputID;
	public TransactionOutput UTXO;
	
	public TransactionInput(String transactionOutputID) {
		this.transactionOutputID = transactionOutputID;
	}

	public String getTransactionOutputID() {
		return transactionOutputID;
	}

	public TransactionOutput getUTXO() {
		return UTXO;
	}

}
