package com.tfowl.project.tile;

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
	public void onBlockMovedOn(World world, Position position, ITileState state) {
		super.onBlockMovedOn(world, position, state);
		state.setValue(COVERED_PROPERTY, true);

		boolean allCovered = true;
		for (Position targetPosition : world.getPositionsOfTiles(this)) {
			ITileState targetState = world.getTileState(targetPosition, this);
			allCovered = targetState.getValue(COVERED_PROPERTY);
			if (!allCovered)
				break;
		}

		if (allCovered) {
			System.out.println(allCovered);
			world.nextLevel();
		} else {
		}
	}

	@Override
	public void onBlockMovedOff(World world, Position position, ITileState state) {
		super.onBlockMovedOff(world, position, state);
		state.setValue(COVERED_PROPERTY, false);
	}
}
