package com.tfowl.project.unit;

import com.tfowl.project.states.properties.DirectionProperty;
import com.tfowl.project.states.properties.LongProperty;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 11.10.2017.
 */
public class UnitSkeleton extends Unit {

	public static final DirectionProperty DIRECTION_PROPERTY = DirectionProperty.create("direction");
	public static final LongProperty COOLDOWN_PROPERTY = LongProperty.create("cooldown");

	private static final long COOLDOWN = 1000L;

	public UnitSkeleton() {
		setName("skeleton");
	}

	@Override
	public IUnitState getDefaultState() {
		IUnitState state = super.getDefaultState();
		state.setValue(DIRECTION_PROPERTY, Direction.UP);
		state.setValue(COOLDOWN_PROPERTY, 0L);
		return state;
	}

	@Override
	public void onTick(World world, long delta, Position position, IUnitState state) {
		super.onTick(world, delta, position, state);

		/*
		TODO: Perhaps instead of testing the state every tick, we can register a 1-second
		callback with the World and handle it in there
		 */

		long cooldown = state.getValue(COOLDOWN_PROPERTY) + delta;

		if (cooldown >= COOLDOWN) {
			state.setValue(COOLDOWN_PROPERTY, cooldown = cooldown - COOLDOWN);

			//TODO: Move the skeleton
		}
	}
}
