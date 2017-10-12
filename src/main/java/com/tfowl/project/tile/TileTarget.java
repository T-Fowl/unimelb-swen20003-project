package com.tfowl.project.tile;

import com.tfowl.project.block.IBlockState;
import com.tfowl.project.reference.Resources;
import com.tfowl.project.states.properties.PropertyBoolean;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Tile in the world that must be covered by a block to progress onto the next level.
 * <p>
 * Created by Thomas on 11.10.2017.
 */
public class TileTarget extends Tile {

	/**
	 * Boolean Property of the covered state of this tile
	 */
	public static final PropertyBoolean COVERED_PROPERTY = PropertyBoolean.create("covered");

	public TileTarget() {
		setName(Resources.Tiles.TARGET_NAME);
		setWalkable(true);
	}

	@Override
	public ITileState getDefaultState() {
		ITileState state = super.getDefaultState();
		/* Default to not covered */
		state.setValue(COVERED_PROPERTY, false);
		return state;
	}

	@Override
	public void onBlockMovedOver(World world, Position position, ITileState state, Position blockFromPosition, IBlockState blockState) {
		super.onBlockMovedOver(world, position, state, blockFromPosition, blockState);
		state.setValue(COVERED_PROPERTY, true);

		/* Go through all other targets in the world, if they are all covered,
		 * then tell the world that we should move onto the next level */

		boolean allCovered = true;
		for (Position targetPosition : world.getPositionsOfTiles(this)) {
			ITileState targetState = world.getTileState(targetPosition, this);
			allCovered &= targetState.getValue(COVERED_PROPERTY);
		}

		if (allCovered) {
			world.nextLevel();
		}
	}

	@Override
	public void onBlockMovedOff(World world, Position position, ITileState state, Position blockToPosition, IBlockState blockState) {
		super.onBlockMovedOff(world, position, state, blockToPosition, blockState);
		state.setValue(COVERED_PROPERTY, false);
	}
}
