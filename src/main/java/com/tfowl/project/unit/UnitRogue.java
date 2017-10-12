package com.tfowl.project.unit;

import com.tfowl.project.player.Player;
import com.tfowl.project.reference.Resources;
import com.tfowl.project.states.properties.PropertyDirection;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * The rogue unit will move left and right every time the player moves.
 * <p>
 * Created by Thomas on 11.10.2017.
 */
public class UnitRogue extends Unit {

	/**
	 * Direction property representing the current direction of a rogue
	 */
	public static final PropertyDirection DIRECTION_PROPERTY = PropertyDirection.create("direction");


	public UnitRogue() {
		setName(Resources.Units.ROGUE_NAME);
		setCanPushBlocks(true); //can push blocks
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

		/* When the player moves, attempt to move in the current direction.
		 * If the unit is blocked, reverse directions for next time */

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

	@Override
	public void onPlayerTouch(World world, Player player, Position location, IUnitState state) {
		super.onPlayerTouch(world, player, location, state);
		world.levelFailed(); //If the rogue touches the player, the level is restarted
	}
}
