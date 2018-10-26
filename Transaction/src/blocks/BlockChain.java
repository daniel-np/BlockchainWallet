package blocks;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {
	private static List<Block> blocks = new ArrayList<Block>();
	
	public static String getTopBlockHash() {
		return blocks.isEmpty() ? "0" : blocks.get(blocks.size()-1).getBlockHash();
	}
	
	public static void addBlockToChain(Block block) {
		blocks.add(block);
	}
	
	public static Integer getHeight() {
		return blocks.size()-1;
	}
}
