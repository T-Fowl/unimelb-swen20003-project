package com.tfowl.project.unit;

import com.tfowl.project.player.Player;
import com.tfowl.project.states.properties.PropertyDirection;
import com.tfowl.project.states.properties.PropertyLong;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 11.10.2017.
 */
public class UnitSkeleton extends Unit {

	public static final PropertyDirection DIRECTION_PROPERTY = PropertyDirection.create("direction");
	public static final PropertyLong COOLDOWN_PROPERTY = PropertyLong.create("cooldown");

	private static final long COOLDOWN = 1000L;

	public UnitSkeleton() {
		setName("skeleton");
		setCanPushBlocks(false);
	}

	@Override
	public IUnitState getDefaultState() {
		IUnitState state = super.getDefaultState();
		state.setValue(DIRECTION_PROPERTY, Direction.UP);
		state.setValue(COOLDOWN_PROPERTY, 0L);
		return state;
	}

	@Override
	public void onTick(World world, long delta, Position unitPosition, IUnitState unitState) {
		super.onTick(world, delta, unitPosition, unitState);


		/*
		Perhaps instead of testing the state every tick, we can register a 1-second
		callback with the World and handle it in there
		 */

		long cooldown = unitState.getValue(COOLDOWN_PROPERTY) + delta;
		unitState.setValue(COOLDOWN_PROPERTY, cooldown);

		if (cooldown >= COOLDOWN) {
			unitState.setValue(COOLDOWN_PROPERTY, cooldown = cooldown - COOLDOWN);

			Direction direction = unitState.getValue(DIRECTION_PROPERTY);

			if (world.canUnitMove(unitPosition, unitState, direction)) {
				world.moveUnit(unitPosition, unitState, direction);
			} else {
				unitState.setValue(DIRECTION_PROPERTY, direction = Direction.reverse(direction));

				//TODO: Should skeleton move or wait another cycle before moving the reverse direction
//				if (world.canUnitMove(position, state, direction)) {
//					world.moveUnit(position, state, direction);
//				}
			}
		}
	}

	@Override
	public void onPlayerTouch(World world, Player player, Position location, IUnitState state) {
		super.onPlayerTouch(world, player, location, state);
		world.levelFailed();
	}
}
