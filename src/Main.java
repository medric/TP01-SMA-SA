import java.util.ArrayList;

/**
 * 
 */

/**
 * @author medric
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Init base
		Environment environment = new Environment(initSMA(3));
		
	}
	
	public static ArrayList<Stack> initSMA(int nbStacks) {
		// Init 4 blocks
		Block block1 = new Block("A");
		Block block2 = new Block("B");
		Block block3 = new Block("C");
		Block block4 = new Block("D");
		
		// Set target blocks
		block1.setLowerTargetBlock(block2);
		block2.setLowerTargetBlock(block3);
		block3.setLowerTargetBlock(block4);
		block4.setLowerTargetBlock(block1);
		
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		// Add blocks to stack
		blocks.add(block1);
		blocks.add(block2);
		blocks.add(block3);
		blocks.add(block4);
		
		ArrayList<Stack> stacks = new ArrayList<Stack>();
	
		Stack firstStack = new Stack(blocks);
		
		// Fill first stack
		stacks.add(firstStack);
		
		for(int i = 1; i < nbStacks; i++) {
			// Empty 
			stacks.add(new Stack(new ArrayList<Block>()));
		}
		
		return stacks;
	}
}
