package com.tfowl.shadowblocks.world.internal;

import com.tfowl.shadowblocks.graphics.IRenderable;
import com.tfowl.shadowblocks.registry.ObjectRegistry;
import com.tfowl.shadowblocks.tile.ITileState;
import com.tfowl.shadowblocks.tile.Tile;
import com.tfowl.shadowblocks.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Instance of a block in the {@link com.tfowl.shadowblocks.world.World}. A {@link Tile} instance is
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
