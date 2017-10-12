package com.tfowl.project.unit;

import com.tfowl.project.player.Player;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 11.10.2017.
 */
public class UnitMage extends Unit {

	public UnitMage() {
		setName("mage");
	}

	@Override
	public void onPlayerMove(World world, Player player, Direction moveDirection, float moveDistance, Position unitPosition, IUnitState unitState) {
		super.onPlayerMove(world, player, moveDirection, moveDistance, unitPosition, unitState);

		//TODO: Check that this works. Seems buggy
		Position difference = unitPosition.difference(player.getPosition());
		int signX = difference.getX() < 0 ? -1 : 1;
		int signY = difference.getY() < 0 ? -1 : 1;
		Direction directionX = signX < 0 ? Direction.RIGHT : Direction.LEFT;
		Direction directionY = signY > 0 ? Direction.UP : Direction.DOWN;

		if (Math.abs(difference.getX()) > Math.abs(difference.getY())
				&& world.canUnitMove(unitPosition, unitState, directionX)) {
			world.moveUnit(unitPosition, unitState, directionX);
		} else if (world.canUnitMove(unitPosition, unitState, directionY)) {
			world.moveUnit(unitPosition, unitState, directionY);
		}
	}
}
