package com.tfowl.project.block;

import com.tfowl.project.states.IState;

public interface IBlockState extends IState<IBlockState> {

	public Block getBlock();
}
