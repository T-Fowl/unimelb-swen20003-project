package com.tfowl.project.unit;

import com.tfowl.project.player.Player;
import com.tfowl.project.reference.Resources;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * The mage is a unit that tries to proactively track down players.
 * <p>
 * Created by Thomas on 11.10.2017.
 */
public class UnitMage extends Unit {

	public UnitMage() {
		setName(Resources.Units.MAGE_NAME);
	}

	@Override
	public void onPlayerMove(World world, Player player, Direction moveDirection, float moveDistance, Position unitPosition, IUnitState unitState) {
		super.onPlayerMove(world, player, moveDirection, moveDistance, unitPosition, unitState);

		/* Run the supplied algorithm in the specification */

		//TODO: Check that this works. Seems buggy
		Position difference = unitPosition.difference(player.getPosition());
		Direction directionX = player.getPosition().getX() >= unitPosition.getX() ? Direction.RIGHT : Direction.LEFT;
		Direction directionY = player.getPosition().getY() >= unitPosition.getY() ? Direction.DOWN : Direction.UP;

		if (Math.abs(difference.getX()) > Math.abs(difference.getY())
				&& world.canUnitMove(unitPosition, unitState, directionX)) {
			world.moveUnit(unitPosition, unitState, directionX);
		} else if (world.canUnitMove(unitPosition, unitState, directionY)) {
			world.moveUnit(unitPosition, unitState, directionY);
		}
	}

	@Override
	public void onPlayerTouch(World world, Player player, Position location, IUnitState state) {
		super.onPlayerTouch(world, player, location, state);
		world.levelFailed(); //If the mage touches the player, the level restarts
	}
}
