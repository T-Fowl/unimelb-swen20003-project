package com.tfowl.shadowblocks.tile;

import com.tfowl.shadowblocks.block.IBlockState;
import com.tfowl.shadowblocks.init.Tiles;
import com.tfowl.shadowblocks.reference.Resources;
import com.tfowl.shadowblocks.states.properties.PropertyBoolean;
import com.tfowl.shadowblocks.util.Position;
import com.tfowl.shadowblocks.world.World;

/**
 * Tile for a switch in the world. When activated these unlock all doors in the current level.
 * <p>
 * Created by Thomas on 11.10.2017.
 */
public class TileSwitch extends Tile {

	/**
	 * Boolean Property of the activated status of this switch.
	 * Public field so others can use it to reference the state of a switch.
	 */
	public static final PropertyBoolean ACTIVATED_PROPERTY = PropertyBoolean.create("activated");

	public TileSwitch() {
		setName(Resources.Tiles.SWITCH_NAME);
		setWalkable(true);
	}

	@Override
	public ITileState getDefaultState() {
		ITileState state = super.getDefaultState();
		/* default to unactivated */
		state.setValue(ACTIVATED_PROPERTY, false);
		return state;
	}

	/**
	 * Toggle all doors in the world to be either open or not
	 *
	 * @param world Current world
	 * @param open  State of the doors
	 */
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
