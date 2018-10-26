package transactions;

public class Transaction {
	private Integer value;
	private String recipient;
	private String sender;
	private String message;
	private String transactionID;
	private byte[] signature;
	private byte[] publicKey;
	private String data;
	private static long sequence = 0;

	/** Regular Transaction
	 * 
	 * @param privateKey
	 * @param sender
	 * @param recipient
	 * @param value
	 */
	public Transaction(String privateKey, String sender, Integer value, String recipient) {
		this.sender = sender;
		this.recipient = recipient;
		this.value = value;
		data = recipient + sender + value + sequence++;
		publicKey = TransactionUtil.getPrivateKeyFromHex(privateKey).getPubKey();
		this.transactionID = TransactionUtil.calculateHashID(data);
		signature = generateSignature(privateKey, data);
	}
	

	/** Coinbase Transaction
	 * 
	 * 
	 * @param recipient
	 * @param value
	 * @param message
	 */
	public Transaction(String recipient, Integer value, String message) {
		this.sender = "Coinbase";
		this.recipient = recipient;
		this.value = value;
		this.message = message;
		data = recipient + sender + value + message + sequence++;
		this.transactionID = TransactionUtil.calculateHashID(data);
	}
	
	public byte[] generateSignature(String privateKeyHex, String input) {
		return TransactionUtil.applySignature(privateKeyHex, TransactionUtil.hashInput(input));
	}
	
	public boolean verifySignature() {
		
		return TransactionUtil.verifySignature(TransactionUtil.hashInput(data).getBytes(), signature, publicKey);
	}
	
	public boolean processTransaction() {
		if(verifySignature()) {
			System.out.println("Transaction verified");
			return verifySignature();
		}else {
			System.err.println("Transaction failed to verify");
			return verifySignature();
		}
	}
	
	

	public Integer getValue() {
		return value;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getSender() {
		return sender;
	}

	public String getMessage() {
		return message;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public static long getSequence() {
		return sequence;
	}
}
