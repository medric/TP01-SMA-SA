import java.util.Random;

public class Block {
	private String name;
	private Block upperBlock;
	private Block lowerBlock;
	private Block lowerTargetBlock;
	private boolean isPushed;
	private boolean isFree;
	private int place1;
	private int place2;

	public Block(String name) {
		this.setName(name);

		// Init places
		this.setPlace1(-1);
		this.setPlace2(-1);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the upperBlock
	 */
	public Block getUpperBlock() {
		return upperBlock;
	}

	/**
	 * @param upperBlock
	 *            the upperBlock to set
	 */
	public void setUpperBlock(Block upperBlock) {
		this.upperBlock = upperBlock;
	}

	/**
	 * @return the lowerBlock
	 */
	public Block getLowerBlock() {
		return lowerBlock;
	}

	/**
	 * @param lowerBlock
	 *            the lowerBlock to set
	 */
	public void setLowerBlock(Block lowerBlock) {
		this.lowerBlock = lowerBlock;
	}

	/**
	 * @return the lowerTargetBlock
	 */
	public Block getLowerTargetBlock() {
		return lowerTargetBlock;
	}

	/**
	 * @param lowerTargetBlock
	 *            the lowerTargetBlock to set
	 */
	public void setLowerTargetBlock(Block lowerTargetBlock) {
		this.lowerTargetBlock = lowerTargetBlock;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSatisfied() {

		boolean satisfied = false;

		if (this.lowerBlock == null && this.lowerTargetBlock == null) {
			satisfied = true;
		}
		if (this.lowerBlock != null) {
			if (this.lowerBlock.equals(this.lowerTargetBlock) && this.isPushed()) {
				satisfied = true;
			}
		}

		return satisfied;
	}

	/**
	 * 
	 */
	public void push(Environment environment) {
		if (!this.isSatisfied() && !this.isFree()) {
			this.upperBlock.perceive(environment);
		}
	}

	/**
	 * Move a block
	 */
	public void move(Environment environment) {
		if (!this.isSatisfied() && this.isFree()) {
			Random rand = new Random();
			int randStack = rand.nextInt(2);

			if (randStack == 0) {
				randStack = this.place1;
			} else {
				randStack = this.place2;
			}

			environment.moveBlock(this, randStack);
		}
	}

	/**
	 * 
	 */
	public void perceive(Environment environment) {
		environment.applyPerception(this);
		// this.move(environment);
	}

	/**
	 * @return the isPushed
	 */
	public boolean isPushed() {
		return isPushed;
	}

	/**
	 * @param isPushed
	 *            the isPushed to set
	 */
	public void setPushed(boolean isPushed) {
		this.isPushed = isPushed;
	}

	/**
	 * @return the isFree
	 */
	public boolean isFree() {
		return isFree;
	}

	/**
	 * @param isFree
	 *            the isFree to set
	 */
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	/**
	 * @return the place1
	 */
	public int getPlace1() {
		return place1;
	}

	/**
	 * @param place1
	 *            the place1 to set
	 */
	public void setPlace1(int place1) {
		this.place1 = place1;
	}

	/**
	 * @return the place2
	 */
	public int getPlace2() {
		return place2;
	}

	/**
	 * @param place2
	 *            the place2 to set
	 */
	public void setPlace2(int place2) {
		this.place2 = place2;
	}
}
