package com.tfowl.project.unit;

import com.tfowl.project.player.Player;
import com.tfowl.project.states.properties.PropertyDirection;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 11.10.2017.
 */
public class UnitRogue extends Unit {

	public static final PropertyDirection DIRECTION_PROPERTY = PropertyDirection.create("direction");

	private static final float MOVE_PER_UPDATE = 1f;


	public UnitRogue() {
		setName("rogue");
		setCanPushBlocks(true);
	}

	@Override
	public IUnitState getDefaultState() {
		IUnitState state = super.getDefaultState();
		state.setValue(DIRECTION_PROPERTY, Direction.LEFT);
		return state;
	}

	@Override
	public void onPlayerMove(World world, Player player, Direction moveDirection, float moveDistance, Position unitPosition, IUnitState unitState) {
		super.onPlayerMove(world, player, moveDirection, moveDistance, unitPosition, unitState);

		Direction move = unitState.getValue(DIRECTION_PROPERTY);

		if (world.canUnitMove(unitPosition, unitState, move)) {
			world.moveUnit(unitPosition, unitState, move);
		} else {
			unitState.setValue(DIRECTION_PROPERTY, move = Direction.reverse(move));

			//TODO: Should rogue move or wait until the player moves again?
//			if (world.canUnitMove(position, state, move)) {
//
//			}
		}
	}
}
