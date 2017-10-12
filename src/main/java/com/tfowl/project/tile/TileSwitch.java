package com.tfowl.project.tile;

import com.tfowl.project.block.IBlockState;
import com.tfowl.project.init.Tiles;
import com.tfowl.project.states.properties.PropertyBoolean;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 11.10.2017.
 */
public class TileSwitch extends Tile {

	public static final PropertyBoolean ACTIVATED_PROPERTY = PropertyBoolean.create("activated");

	public TileSwitch() {
		setName("switch");
		setWalkable(true);
	}

	@Override
	public ITileState getDefaultState() {
		ITileState state = super.getDefaultState();
		state.setValue(ACTIVATED_PROPERTY, false);
		return state;
	}

	private void toggleDoors(World world, boolean open) {
		for (Position doorPosition : world.getPositionsOfTiles(Tiles.DOOR)) {
			ITileState doorState = world.getTileState(doorPosition, Tiles.DOOR);
			doorState.setValue(TileDoor.OPEN_PROPERTY, open);
		}
	}

	@Override
	public void onBlockMovedOff(World world, Position position, ITileState state, Position blockToPosition, IBlockState blockState) {
		super.onBlockMovedOff(world, position, state, blockToPosition, blockState);
		state.setValue(ACTIVATED_PROPERTY, false);
		toggleDoors(world, false);
	}

	@Override
	public void onBlockMovedOver(World world, Position position, ITileState state, Position blockFromPosition, IBlockState blockState) {
		super.onBlockMovedOver(world, position, state, blockFromPosition, blockState);
		state.setValue(ACTIVATED_PROPERTY, true);
		toggleDoors(world, true);
	}
}
