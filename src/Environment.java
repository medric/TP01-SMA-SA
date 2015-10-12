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
		int blockIndex = this.locateBlock(block);

		// TODO : Réinitialiser à chaque fois les getPlace à -1 ?
		
		for (int i = 0; i < this.stacks.size(); i++) {
			
			if (i != blockIndex && !Integer.valueOf(-1).equals(block.getPlace1())) {
				block.setPlace2(i);
			}
			
			if (i != blockIndex && Integer.valueOf(-1).equals(block.getPlace1())) {
				block.setPlace1(i);
			}

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
					block.setFree(false);
					block.setPushed(false);
				} catch (Exception e) {
					//
					currentBlock.setUpperBlock(null);
					block.setFree(true);
					block.setPushed(true);
				}
				
				// Actions
				if (currentBlock.isPushed()) {
					currentBlock.push(this);
				}
				
				if (currentBlock.isFree()) {
					currentBlock.move(this);
				}
			}
		}
		
		this.render();
	}

	/**
	 * 
	 */
	public void moveBlock(Block block, int toStack) {
		int blockIndex = this.locateBlock(block);
		for (int i = 0; i < this.getStacks().get(blockIndex).getBlocks().size(); i++) {
			if(this.getStacks().get(blockIndex).getBlocks().get(i).getName() == block.getName()){
				this.getStacks().get(blockIndex).getBlocks().remove(i);
			}
		}
		
		this.getStacks().get(toStack).addBlock(block);
	}

	/**
	 * 
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
				System.out.println("pas de blocs");
			} else {
				System.out.println();
			}
		}

		System.out.print("-----------------------------------Iteration suivante-----------------------------------");
	}
}
