import java.lang.reflect.Array;
import java.util.ArrayList;

public class Environment {
	private ArrayList<Stack> stacks;
	
	/**
	 * 
	 * @param nbStacks
	 */
	public Environment(ArrayList<Stack> stacks) {
		// Init matrix
		this.setStacks(stacks);
	}

	/**
	 * @return the stacks
	 */
	public ArrayList<Stack> getStacks() {
		return stacks;
	}

	/**
	 * @param stacks the stacks to set
	 */
	public void setStacks(ArrayList<Stack> stacks) {
		this.stacks = stacks;
	}
	
	/**
	 * 
	 * @param block
	 */
	public void applyPerception(Block block) {
		int blockIndex = this.locateBlock(block);
		
		for(int i = 0; i < this.stacks.size(); i++) {
			if(i != blockIndex && block.getPlace2() != -1) {
				block.setPlace1(i); 
			} 
			
			if(i != blockIndex && block.getPlace1() != -1) {
				block.setPlace2(i);
			}
				
			for(int j = this.stacks.get(i).getBlocks().size(); j > 0 ; j--) {
				// Current block
				Block currentBlock = this.stacks.get(i).getBlocks().get(j);
				
				// Try to set lower block
				try {
					currentBlock.setLowerBlock(this.stacks.get(i).getBlocks().get(j - 1));
				} catch(Exception e) {
					//
					currentBlock.setLowerBlock(null);
				}
				
				// Try to set upper block
				try {
					currentBlock.setUpperBlock(this.stacks.get(i).getBlocks().get(j + 1));
					block.setFree(false);
					block.setPushed(false);
				} catch(Exception e) {
					//
					currentBlock.setUpperBlock(null);
					block.setFree(true);
					block.setPushed(true);
				}
				
				// Actions
				if(currentBlock.isPushed()) {
					currentBlock.push(this);
				}
			}
		}
		
		this.render();
	}
	
	/**
	 * 
	 */
	public void moveBlock(Block block, int toStack) {
		//int blockIndex = this.locateBlock(block);
		
		this.getStacks().get(toStack).addBlock(block);
	}
	
	/**
	 * 
	 */
	public int locateBlock(Block block) {
		int index = -1;
		for(int i = 0; i < this.stacks.size(); i++) {
			for(int j = this.stacks.get(i).getBlocks().size(); j > 0 ; j--) {
				// Current block
				Block currentBlock = this.stacks.get(i).getBlocks().get(j);
				
				if(currentBlock.getName().equals(block.getName())) {
					index = i;
					break;
				}
			}
		}
		return index;
	}
	
	/**
	 * Rendering the environment 
	 */
	private void render() {
		for(int i = 0; i < this.stacks.size(); i++) {
			System.out.println(i + ": ");
			for(int j = this.stacks.get(i).getBlocks().size(); j > 0 ; j--) { 
				System.out.println(this.stacks.get(i).getBlocks().get(j) + " - ");
			}
			System.out.println("\n");
		}
	}
}
