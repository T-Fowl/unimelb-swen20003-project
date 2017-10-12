package com.tfowl.shadowblocks.unit;

import com.tfowl.shadowblocks.impl.ImplUnitState;
import com.tfowl.shadowblocks.player.Player;
import com.tfowl.shadowblocks.util.Direction;
import com.tfowl.shadowblocks.util.Position;
import com.tfowl.shadowblocks.world.World;

/**
 * Represents the definition of units in the game world.
 */
public class Unit {

	private String name;
	private boolean canPushBlocks = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean canPushBlocks() {
		return canPushBlocks;
	}

	public void setCanPushBlocks(boolean canPushBlocks) {
		this.canPushBlocks = canPushBlocks;
	}

	/**
	 * @return The default state of an {@link Unit}
	 */
	public IUnitState getDefaultState() {
		return new ImplUnitState(this);
	}

	/**
	 * Called whenever a player in the world moves.
	 *
	 * @param world         The current world
	 * @param player        The player who moved
	 * @param moveDirection The direction the player moved
	 * @param moveDistance  The distance the player moved
	 * @param unitPosition  The position of a unit in the world
	 * @param unitState     The state of said unit in the world
	 */
	public void onPlayerMove(World world, Player player,
							 Direction moveDirection, float moveDistance,
							 Position unitPosition, IUnitState unitState) {
	}

	/**
	 * Called on each world tick
	 *
	 * @param world        The current world
	 * @param delta        The time (in milliseconds) since the last tick
	 * @param unitPosition The position of a unit in the world
	 * @param unitState    The state of a unit in the world
	 */
	public void onWorldTick(World world, long delta,
							Position unitPosition, IUnitState unitState) {
	}

	/**
	 * Called when a player occupies the same tile as this unit
	 *
	 * @param world    The current world
	 * @param player   The player who touched a unit
	 * @param location The position of said unit in the world
	 * @param state    The state of said unit in the world
	 */
	public void onPlayerTouch(World world, Player player,
							  Position location,
							  IUnitState state) {
	}
}
