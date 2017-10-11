package com.tfowl.project.block;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class BlockInstance implements IRenderable {

	private Block block;
	private Position position;

	public BlockInstance(Block block) {
		this.block = block;
		this.position = new Position();
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public void draw(Graphics g, int gx, int gy) throws SlickException {
		ObjectRegistry.getImage(block.getName()).draw(gx, gy);
	}
}
