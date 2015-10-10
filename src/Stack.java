import java.util.ArrayList;

public class Stack {
	private ArrayList<Block> blocks;
	
	public Stack(ArrayList<Block> blocks) {
		// Init blocks stack
		this.setBlocks(blocks);
	}
	
	/**
	 * @return the blocks
	 */
	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	/**
	 * @param blocks the blocks to set
	 */
	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}
	
	/**
	 * 
	 * @param block
	 */
	public void addBlock(Block block) {
		this.blocks.add(block);
	}

} 
