package com.tfowl.project.impl;

import com.tfowl.project.block.Block;
import com.tfowl.project.block.IBlockState;

public class ImplBlockState extends ImplState<Block> implements IBlockState {

	public ImplBlockState(Block block) {
		super(block);
	}

	@Override
	public Block getBlock() {
		return super.getObject();
	}
}
