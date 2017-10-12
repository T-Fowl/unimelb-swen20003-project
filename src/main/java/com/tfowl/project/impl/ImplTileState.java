package com.tfowl.project.impl;

import com.tfowl.project.tile.ITileState;
import com.tfowl.project.tile.Tile;

public class ImplTileState extends ImplState<Tile, ITileState> implements ITileState {

	public ImplTileState(Tile tile) {
		super(tile);
	}

	@Override
	public Tile getTile() {
		return super.getObject();
	}
}
