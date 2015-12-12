import java.util.ArrayList;
import java.util.Random;

/**
 * 
 */

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Init base
		Environment environment = initSMA(3);

		for (int k = 0; k < 1000; k++) {
			Random rand = new Random();
			int randStack = rand.nextInt(environment.getAgents().size());
			environment.applyPerception(environment.getAgents().get(randStack));
		}
	}

	public static Environment initSMA(int nbStacks) {

		// Init 4 blocks
		Block block1 = new Block("A");
		Block block2 = new Block("B");
		Block block3 = new Block("C");
		Block block4 = new Block("D");

		// Set target blocks
		block1.setLowerTargetBlock(block2);
		block2.setLowerTargetBlock(null);
		block3.setLowerTargetBlock(block1);
		block4.setLowerTargetBlock(block3);

		ArrayList<Block> blocks = new ArrayList<Block>(4);

		// Add blocks to stack
		blocks.add(block4);
		blocks.add(block3);
		blocks.add(block2);
		blocks.add(block1);

		ArrayList<Stack> stacks = new ArrayList<Stack>(3);

		Stack firstStack = new Stack(blocks);

		// Fill first stack
		stacks.add(firstStack);

		for (int i = 1; i < nbStacks; i++) {
			Stack stack = new Stack(new ArrayList<Block>(4));

			// Empty
			stacks.add(stack);
		}

		Environment e = new Environment(stacks);

		return e;
	}
}
