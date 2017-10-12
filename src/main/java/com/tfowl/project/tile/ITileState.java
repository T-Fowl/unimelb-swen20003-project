package com.tfowl.project.tile;

import com.tfowl.project.states.IState;

public interface ITileState extends IState<ITileState> {

	public Tile getTile();
}
