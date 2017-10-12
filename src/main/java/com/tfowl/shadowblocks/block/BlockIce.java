package com.tfowl.shadowblocks.block;

import com.tfowl.shadowblocks.reference.Resources;
import com.tfowl.shadowblocks.states.properties.PropertyBoolean;
import com.tfowl.shadowblocks.states.properties.PropertyDirection;
import com.tfowl.shadowblocks.states.properties.PropertyLong;
import com.tfowl.shadowblocks.util.Direction;
import com.tfowl.shadowblocks.util.Position;
import com.tfowl.shadowblocks.world.World;

/**
 * Represents a block of ice that can slide once pushed.
 * <p>
 * Created by Thomas on 11.10.2017.
 */
public class BlockIce extends Block {

	/**
	 * Boolean property of whether of not a block of ice is sliding.
	 * Public field so others can use it to get the values in a ice blocks state.
	 */
	public static final PropertyBoolean SLIDING_PROPERTY = PropertyBoolean.create("sliding");
	/**
	 * Direction property of the sliding direction of a block of ice.
	 * Public field so others can use it to get the values in a ice blocks state.
	 */
	public static final PropertyDirection SLIDING_DIRECTION_PROPERTY = PropertyDirection.create("sliding_direction");
	/**
	 * Long property of the cool-down time since the block of ice last moved.
	 * Public field so others can use it to get the values in a ice blocks state.
	 */
	public static final PropertyLong COOLDOWN_PROPERTY = PropertyLong.create("cooldown");

	private static final long SLIDING_COOLDOWN = 250L; //quarter of a second

	public BlockIce() {
		super();
		setName(Resources.Blocks.ICE_NAME);
	}

	@Override
	public IBlockState getDefaultState() {
		IBlockState state = super.getDefaultState();
		/* Default to not sliding */
		state.setValue(SLIDING_PROPERTY, false);
		state.setValue(SLIDING_DIRECTION_PROPERTY, Direction.NONE);
		state.setValue(COOLDOWN_PROPERTY, 0L);
		return state;
	}

	private void setInMotion(Direction direction, IBlockState state) {
		/* Set the state to sliding in a given direction with 0 elapsed time */
		state.setValue(SLIDING_PROPERTY, true);
		state.setValue(SLIDING_DIRECTION_PROPERTY, direction);
		state.setValue(COOLDOWN_PROPERTY, 0L);
	}

	@Override
	public void onPush(World world, Direction directionPushed, Position oldPosition, Position position, IBlockState state) {
		super.onPush(world, directionPushed, oldPosition, position, state);
		setInMotion(directionPushed, state);
	}

	@Override
	public void onWorldTick(World world, long delta, Position position, IBlockState state) {
		super.onWorldTick(world, delta, position, state);

		/*
		On a world tick, check if we have passed our threshold of time since last moving.
		If we have, 'reset' the counter (accounting for any over-waiting) and tell the world to move
		the given ice block
		 */

		if (state.getValue(SLIDING_PROPERTY)) {

			long cooldown = state.getValue(COOLDOWN_PROPERTY) + delta;
			state.setValue(COOLDOWN_PROPERTY, cooldown);

			if (cooldown >= SLIDING_COOLDOWN) {
				/* We've waited long enough */

				state.setValue(COOLDOWN_PROPERTY, cooldown % SLIDING_COOLDOWN);

				Direction direction = state.getValue(SLIDING_DIRECTION_PROPERTY);

				if (world.canBlockMove(position, state, direction)) {
					world.moveBlock(position, direction);
				} else {
					/* Stop sliding */
					state.setValue(SLIDING_PROPERTY, false);
					state.setValue(SLIDING_DIRECTION_PROPERTY, Direction.NONE);
					state.setValue(COOLDOWN_PROPERTY, 0L);
				}
			}
		}
	}
}
