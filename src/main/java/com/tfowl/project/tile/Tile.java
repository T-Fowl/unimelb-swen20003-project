package com.tfowl.project.tile;


import com.tfowl.project.block.IBlockState;
import com.tfowl.project.impl.ImplTileState;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
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

	public ITileState getDefaultState() {
		return new ImplTileState(this);
	}

	public boolean isTileWalkable(World world, Position position, ITileState state) {
		return isWalkable();
	}

	public void onBlockMovedOff(World world,
								Position position, ITileState state,
								Position blockToPosition, IBlockState blockState) {

	}

	public void onBlockMovedOver(World world,
								 Position position, ITileState state,
								 Position blockFromPosition, IBlockState blockState) {
	}

	public boolean shouldRenderTile(World world, Position position, ITileState state) {
		return true;
	}
}
