package com.tfowl.shadowblocks.tile;

import com.tfowl.shadowblocks.states.IState;

/**
 * Represents the state of a {@link Tile} in the game world. This will hold properties assigned to
 * the tile.
 */
public interface ITileState extends IState<ITileState> {

	/**
	 * @return The type of the tile
	 */
	public Tile getTile();
}
