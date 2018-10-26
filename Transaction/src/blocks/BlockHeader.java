package blocks;

public class BlockHeader {
	private String prev; //Previous block hash
	private Integer difficulty;
	private Integer timestamp;
	private long nonce;
	
	public BlockHeader(String prev, Integer difficulty, long nonce) {
		this.prev = prev;
		this.difficulty = difficulty;
		this.nonce = nonce;
	}

	public String getPrev() {
		return prev;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public Integer getTimestamp() {
		return timestamp;
	}

	public long getNonce() {
		return nonce;
	}
}
