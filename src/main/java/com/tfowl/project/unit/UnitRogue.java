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
	public void onPlayerMove(World world, Player player, Direction direction, float distance, Position position, IUnitState state) {
		super.onPlayerMove(world, player, direction, distance, position, state);

		Direction move = state.getValue(DIRECTION_PROPERTY);
		Position newPosition = position.displace(move, MOVE_PER_UPDATE);
	}
}
