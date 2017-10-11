package com.tfowl.project.impl;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.tile.ITileState;
import com.tfowl.project.tile.Tile;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class TileInstance implements IRenderable {

	private Tile tile;
	private Position position;
	private ITileState state;

	public TileInstance(Tile tile) {
		this.tile = tile;
		this.state = tile.getDefaultState();
		this.position = new Position();
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
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
	public void draw(Graphics g, int gx, int gy) throws SlickException {
		ObjectRegistry.getImage(tile.getName()).draw(gx, gy);
	}
}
