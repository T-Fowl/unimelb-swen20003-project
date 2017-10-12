package com.tfowl.project.impl;

import com.tfowl.project.block.Block;
import com.tfowl.project.block.IBlockState;
import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Instance of a block in the {@link com.tfowl.project.world.World}. A {@link Block} instance is
 * uniquely identified by its {@link IBlockState} and {@link Position}
 */
public class BlockInstance implements IRenderable {

	private IBlockState state;
	private Position position;

	public BlockInstance(Block block) {
		this.state = block.getDefaultState();
		this.position = new Position();
	}

	public Block getBlock() {
		return getState().getBlock();
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public IBlockState getState() {
		return state;
	}

	public void setState(IBlockState state) {
		this.state = state;
	}

	@Override
	public void draw(Graphics g, float gx, float gy) throws SlickException {
		/* Lookyp the image for the block and draw */
		ObjectRegistry.getImage(state.getBlock().getName()).draw(gx, gy);
	}
}
