package transactions;

public class TransactionOutput {
	private String transactionOutputID;
	private String recipient;
	private long value;
	private String transactionID;
	
	public TransactionOutput(String recipient, Integer value, String transactionID) {
		this.recipient = recipient;
		this.value = value;
		this.transactionID = transactionID;
		String hashData = recipient + value + transactionID;
		transactionOutputID = TransactionUtil.calculateHashID(hashData);
	}
	
	public boolean ownedValue(String publicKey) {
		return (publicKey == recipient);
	}

	public String getTransactionOutputID() {
		return transactionOutputID;
	}

	public String getRecipient() {
		return recipient;
	}

	public long getValue() {
		return value;
	}

	public String getTransactionID() {
		return transactionID;
	}
}
