package com.tfowl.project.block;

/**
 * Created by Thomas on 11.10.2017.
 */
public class BlockIce extends Block {

	private static final float SLIDING_SPEED = 4;

	public BlockIce() {
		super();
		setName("ice");
		setSlidingBlock(true);
		setSlidingSpeed(SLIDING_SPEED);
	}
}
