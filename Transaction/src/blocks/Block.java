package blocks;

import transactions.Transaction;

public class Block {
	private BlockHeader blockHeader;
	private BlockBody blockBody;
	private String blockHash;
	private long blockHeight;
	private Integer blockReward = 100;
	
	public Block(Transaction coinbase) {
		blockHeight = BlockChain.getHeight();
		blockHeader = new BlockHeader(getPreviousBlockHash(), 0, 0);
		blockBody = new BlockBody(coinbase);
	}
	
	private String getPreviousBlockHash() {
		return BlockChain.getTopBlockHash();
	}
	
	public BlockHeader getBlockHeader() {
		return blockHeader;
	}

	public BlockBody getBlockBody() {
		return blockBody;
	}

	public String getBlockHash() {
		return blockHash;
	}

	public long getBlockHeight() {
		return blockHeight;
	}

	public Integer getBlockReward() {
		return blockReward;
	}

}
