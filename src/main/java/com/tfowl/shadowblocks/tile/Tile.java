package com.tfowl.shadowblocks.tile;


import com.tfowl.shadowblocks.block.IBlockState;
import com.tfowl.shadowblocks.impl.TileState;
import com.tfowl.shadowblocks.util.Position;
import com.tfowl.shadowblocks.world.World;

/**
 * Represents the definition of a Tile in the game.
 * <p>
 * Created by Thomas on 6/09/2017.
 */
public class Tile {

	private String name;
	private boolean isWalkable;

	public Tile() {
	}

	public String getName() {
		return name;
	}

	public Tile setName(String name) {
		this.name = name;
		return this;
	}

	public boolean isWalkable() {
		return isWalkable;
	}

	public Tile setWalkable(boolean walkable) {
		isWalkable = walkable;
		return this;
	}

	/**
	 * @return The default state of this tile
	 */
	public ITileState getDefaultState() {
		return new TileState(this);
	}

	/**
	 * Determines if a tile in the world is walkable
	 *
	 * @param world    Current world
	 * @param position Position of the tile
	 * @param state    State of the tile
	 * @return true of the tile is walkable, false otherwise
	 */
	public boolean isTileWalkable(World world, Position position, ITileState state) {
		return isWalkable();
	}

	/**
	 * Called when a block moves off a tile in the world
	 *
	 * @param world           Current world
	 * @param position        Position of the tile
	 * @param state           State of the tile
	 * @param blockToPosition Position the block moved to
	 * @param blockState      State of the block that moved off
	 */
	public void onBlockMovedOff(World world,
								Position position, ITileState state,
								Position blockToPosition, IBlockState blockState) {

	}

	/**
	 * Called when a block moves onto a tile in the world
	 *
	 * @param world             Current world
	 * @param position          Position of the tile
	 * @param state             State of the tile
	 * @param blockFromPosition Position the block moved from
	 * @param blockState        State of the block that moved on
	 */
	public void onBlockMovedOver(World world,
								 Position position, ITileState state,
								 Position blockFromPosition, IBlockState blockState) {
	}

	/**
	 * Determines if a tile in the world should be rendered.
	 *
	 * @param world    The current world.
	 * @param position Position of the tile.
	 * @param state    State of the tile.
	 * @return true if the tile should be rendered, false otherwise. Defaults to true.
	 */
	public boolean shouldRenderTile(World world, Position position, ITileState state) {
		return true;
	}
}
