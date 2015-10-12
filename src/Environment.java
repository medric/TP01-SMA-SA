import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Environment {
	private ArrayList<Stack> stacks;
	private ArrayList<Block> agents;

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

	public ArrayList<Block> getAgents() {

		this.agents = new ArrayList<>();

		for (int i = 0; i < this.stacks.size(); i++) {
			for (int j = this.stacks.get(i).getBlocks().size() - 1; j >= 0; j--) {
				if (this.stacks.get(i).getBlocks().get(j) != null) {
					this.agents.add(this.stacks.get(i).getBlocks().get(j));
				}
			}
		}
		return this.agents;
	}

	/**
	 * @param stacks
	 *            the stacks to set
	 */
	public void setStacks(ArrayList<Stack> stacks) {
		this.stacks = stacks;
	}

	/**
	 * 
	 * @param block
	 */
	public void applyPerception(Block block) {
		setBlockPlaces(block);

		for (int i = 0; i < this.stacks.size(); i++) {
			for (int j = this.stacks.get(i).getBlocks().size() - 1; j >= 0; j--) {
				// Current block
				Block currentBlock = this.stacks.get(i).getBlocks().get(j);

				// Try to set lower block
				try {
					currentBlock.setLowerBlock(this.stacks.get(i).getBlocks().get(j - 1));
				} catch (Exception e) {
					//
					currentBlock.setLowerBlock(null);
				}

				// Try to set upper block
				try {

					currentBlock.setUpperBlock(this.stacks.get(i).getBlocks().get(j + 1));
					currentBlock.setFree(false);
					currentBlock.setPushed(true);
				} catch (Exception e) {
					//
					currentBlock.setUpperBlock(null);
					currentBlock.setFree(true);
					currentBlock.setPushed(false);
				}
			}
		}

		// Actions
		if (block.isPushed()) {
			block.push(this);
		}

		if (block.isFree()) {
			block.move(this);
		}

		this.render();
		
		this.checkEndGame();
	}

	/**
	 * 
	 */
	public void moveBlock(Block block, int toStack) {
		int blockIndex = this.locateBlock(block);
		for (int i = 0; i < this.getStacks().get(blockIndex).getBlocks().size(); i++) {
			if (this.getStacks().get(blockIndex).getBlocks().get(i).getName() == block.getName()) {
				this.getStacks().get(blockIndex).getBlocks().remove(i);
			}
		}

		this.getStacks().get(toStack).addBlock(block);
	}

	/**
	 * Give a block position stack
	 */
	public int locateBlock(Block block) {
		int index = -1;
		for (int i = 0; i < this.stacks.size(); i++) {
			for (int j = this.stacks.get(i).getBlocks().size() - 1; j >= 0; j--) {
				// Current block
				Block currentBlock = this.stacks.get(i).getBlocks().get(j);

				if (currentBlock.getName().equals(block.getName())) {
					index = i;
					break;
				}
			}
		}
		return index;
	}

	/**
	 * 
	 * @param block
	 */
	private void setBlockPlaces(Block block) {
		int blockIndex = this.locateBlock(block);
		int[] places = new int[2];
		int cursor = 0;

		for (int i = 0; i < this.stacks.size(); i++) {
			if (i != blockIndex) {
				places[cursor] = i;
				cursor++;
			}
		}

		// Set places
		block.setPlace1(places[0]);
		block.setPlace2(places[1]);
	}

	private void checkEndGame() {		
		int res = 0;
		
		for (int i = 0; i < this.agents.size(); i++) {
			if(this.agents.get(i).isSatisfied()){
				res++;
			}
		}
		
		if (res == 3) {
			System.out.println("---- END !!! ----");
			System.exit(0);
		}
	}

	/**
	 * Rendering the environment
	 */
	private void render() {
		for (int i = 0; i < this.stacks.size(); i++) {
			System.out.print("Stacks " + i + " : ");
			for (int j = this.stacks.get(i).getBlocks().size() - 1; j >= 0; j--) {
				Block block = this.stacks.get(i).getBlocks().get(j);
				if (block.getLowerTargetBlock() != null) {
					System.out.print(block.getName() + " (cible ->" + block.getLowerTargetBlock().getName() + ") ");
				} else {
					System.out.print(block.getName() + " (cible -> en bas) ");
				}
			}
			if (this.stacks.get(i).getBlocks().size() == 0) {
				System.out.println("pas de bloc");
			} else {
				System.out.println();
			}
		}

		System.out.print("-----------------------------------Iteration suivante-----------------------------------\n");
	}
}
