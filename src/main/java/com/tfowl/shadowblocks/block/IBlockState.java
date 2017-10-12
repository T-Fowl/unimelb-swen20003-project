package com.tfowl.shadowblocks.block;

import com.tfowl.shadowblocks.states.IState;

/**
 * Represents the state of a block in the world. This will hold properties
 * assigned to the block as well as the type of block.
 */
public interface IBlockState extends IState<IBlockState> {

	/**
	 * @return The type of the block
	 */
	public Block getBlock();
}
