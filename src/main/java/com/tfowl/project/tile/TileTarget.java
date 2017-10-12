package com.tfowl.project.tile;

import com.tfowl.project.block.IBlockState;
import com.tfowl.project.states.properties.PropertyBoolean;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 11.10.2017.
 */
public class TileTarget extends Tile {

	public static final PropertyBoolean COVERED_PROPERTY = PropertyBoolean.create("covered");

	public TileTarget() {
		setName("target");
		setWalkable(true);
	}

	@Override
	public ITileState getDefaultState() {
		ITileState state = super.getDefaultState();
		state.setValue(COVERED_PROPERTY, false);
		return state;
	}

	@Override
	public void onBlockMovedOver(World world, Position position, ITileState state, Position blockFromPosition, IBlockState blockState) {
		super.onBlockMovedOver(world, position, state, blockFromPosition, blockState);
		state.setValue(COVERED_PROPERTY, true);

		boolean allCovered = true;
		for (Position targetPosition : world.getPositionsOfTiles(this)) {
			ITileState targetState = world.getTileState(targetPosition, this);
			allCovered &= targetState.getValue(COVERED_PROPERTY);
		}

		if (allCovered) {
			System.out.println("All covered");
			world.nextLevel();
		} else {
			System.out.println("Not all covered");
		}
	}

	@Override
	public void onBlockMovedOff(World world, Position position, ITileState state, Position blockToPosition, IBlockState blockState) {
		super.onBlockMovedOff(world, position, state, blockToPosition, blockState);
		state.setValue(COVERED_PROPERTY, false);
	}
}
