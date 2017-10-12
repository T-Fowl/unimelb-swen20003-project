package com.tfowl.project.impl;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.tile.ITileState;
import com.tfowl.project.tile.Tile;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Instance of a block in the {@link com.tfowl.project.world.World}. A {@link Tile} instance is
 * uniquely identified by its {@link ITileState} and {@link Position}
 */
public class TileInstance implements IRenderable {

	private Position position;
	private ITileState state;

	public TileInstance(Tile tile) {
		this.state = tile.getDefaultState();
		this.position = new Position();
	}

	public Tile getTile() {
		return getState().getTile();
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public ITileState getState() {
		return state;
	}

	public void setState(ITileState state) {
		this.state = state;
	}

	@Override
	public void draw(Graphics g, float gx, float gy) throws SlickException {
		/* Lookup the tiles image and draw */
		ObjectRegistry.getImage(state.getTile().getName()).draw(gx, gy);
	}
}
