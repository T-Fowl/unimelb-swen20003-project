package com.tfowl.project.block;

import com.tfowl.project.states.properties.BooleanProperty;
import com.tfowl.project.states.properties.DirectionProperty;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 11.10.2017.
 */
public class BlockIce extends Block {

	public static final BooleanProperty SLIDING_PROPERTY = BooleanProperty.create("sliding");
	public static final DirectionProperty SLIDING_DIRECTION_PROPERTY = DirectionProperty.create("sliding_direction");

	private static final float SLIDING_SPEED = 4; //4 blocks per
	private static final float SLIDING_PER = 1; //1 second

	public BlockIce() {
		super();
		setName("ice");
		setSlidingBlock(true);
		setSlidingSpeed(SLIDING_SPEED);
	}

	@Override
	public IBlockState getDefaultState() {
		IBlockState state = super.getDefaultState();
		state.setValue(SLIDING_PROPERTY, false);
		state.setValue(SLIDING_DIRECTION_PROPERTY, Direction.NONE);
		return state;
	}

	@Override
	public void onTick(World world, long delta, Position position, IBlockState state) {
		super.onTick(world, delta, position, state);

		if (state.getValue(SLIDING_PROPERTY)) {
			Direction direction = state.getValue(SLIDING_DIRECTION_PROPERTY);

			//TODO: Handle movement
		}
	}
}
