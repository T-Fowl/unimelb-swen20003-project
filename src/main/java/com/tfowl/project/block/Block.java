package com.tfowl.project.block;

/**
 * Created by Thomas on 11.10.2017.
 */
public class Block {

	// @formatter:off

	//Resource loading
	private String name;

	//Specify the sliding nature of this block
	private boolean isSlidingBlock = false;
	//Tiles / second
	private float   slidingSpeed   = 0;

	// @formatter:on

	public Block() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSlidingBlock() {
		return isSlidingBlock;
	}

	public void setSlidingBlock(boolean slidingBlock) {
		isSlidingBlock = slidingBlock;
	}

	public float getSlidingSpeed() {
		return slidingSpeed;
	}

	public void setSlidingSpeed(float slidingSpeed) {
		this.slidingSpeed = slidingSpeed;
	}
}
