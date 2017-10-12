package com.tfowl.project.block;

import com.tfowl.project.states.properties.PropertyBoolean;
import com.tfowl.project.states.properties.PropertyDirection;
import com.tfowl.project.states.properties.PropertyLong;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 11.10.2017.
 */
public class BlockIce extends Block {

	public static final PropertyBoolean SLIDING_PROPERTY = PropertyBoolean.create("sliding");
	public static final PropertyDirection SLIDING_DIRECTION_PROPERTY = PropertyDirection.create("sliding_direction");
	public static final PropertyLong COOLDOWN_PROPERTY = PropertyLong.create("cooldown");

	private static final float SLIDING_SPEED = 4; //4 blocks per
	private static final float SLIDING_PER = 1; //1 second

	public BlockIce() {
		super();
		setName("ice");
	}

	@Override
	public IBlockState getDefaultState() {
		IBlockState state = super.getDefaultState();
		state.setValue(SLIDING_PROPERTY, false);
		state.setValue(SLIDING_DIRECTION_PROPERTY, Direction.NONE);
		state.setValue(COOLDOWN_PROPERTY, 0L);
		return state;
	}

	private void setInMotion(Direction direction, IBlockState state) {
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

		if (state.getValue(SLIDING_PROPERTY)) {

			long cooldown = state.getValue(COOLDOWN_PROPERTY) + delta;
			state.setValue(COOLDOWN_PROPERTY, cooldown);

			if (cooldown >= 250) {
				state.setValue(COOLDOWN_PROPERTY, cooldown - 250);

				Direction direction = state.getValue(SLIDING_DIRECTION_PROPERTY);
				Position destination = position.displace(direction);

				if (world.canBlockMove(position, state, direction)) {
					world.moveBlock(position, direction);
				} else {
					state.setValue(SLIDING_PROPERTY, false);
					state.setValue(SLIDING_DIRECTION_PROPERTY, Direction.NONE);
					state.setValue(COOLDOWN_PROPERTY, 0L);
				}
			}


			//TODO: Handle movement
		}
	}
}
