package com.tfowl.project.block;

import com.tfowl.project.impl.ImplBlockState;
import com.tfowl.project.player.Player;
import com.tfowl.project.unit.IUnitState;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Represents the definition of the block in game.
 * <p>
 * Created by Thomas on 11.10.2017.
 */
public class Block {

	private String name;
	private boolean isPushable = true;

	public Block() {
	}

	public String getName() {
		return name;
	}

	public Block setName(String name) {
		this.name = name;
		return this;
	}

	public boolean isPushable() {
		return isPushable;
	}

	public Block setPushable(boolean pushable) {
		isPushable = pushable;
		return this;
	}

	/**
	 * @return The default state of this block
	 */
	public IBlockState getDefaultState() {
		return new ImplBlockState(this);
	}


	/**
	 * Called on a {@link World} tick.
	 *
	 * @param world    The current world
	 * @param delta    Time difference between this tick and the last tick
	 * @param position Position of the referenced block in the world
	 * @param state    State of the referenced block in the world
	 */
	public void onWorldTick(World world, long delta,
							Position position, IBlockState state) {

	}

	/**
	 * Called when this block is pushed
	 *
	 * @param world           The current world
	 * @param directionPushed Direction pushed in
	 * @param oldPosition     The old position
	 * @param position        The current position of the referenced block in the world
	 * @param state           The state of the referenced block in the world
	 */
	public void onPush(World world, Direction directionPushed, Position oldPosition,
					   Position position, IBlockState state) {

	}

	/**
	 * Called when this block is pushed.
	 *
	 * @param world            The current world
	 * @param pushingUnitState State of the {@link com.tfowl.project.unit.Unit} that pushed the block
	 * @param directionPushed  Direction pushed in
	 * @param oldPosition      The old position
	 * @param position         The current position of the referenced block in the world
	 * @param state            The state of the referenced block in the world
	 */
	public void onPush(World world,
					   IUnitState pushingUnitState, Direction directionPushed, Position oldPosition,
					   Position position, IBlockState state) {
		onPush(world, directionPushed, oldPosition, position, state);
	}

	/**
	 * Called when this block is pushed.
	 *
	 * @param world           The current world
	 * @param pushingPlayer   {@link Player} who pushed the block
	 * @param directionPushed Direction pushed in
	 * @param oldPosition     The old position
	 * @param position        The current position of the referenced block in the world
	 * @param state           The state of the referenced block in the world
	 */
	public void onPush(World world,
					   Player pushingPlayer, Direction directionPushed, Position oldPosition,
					   Position position, IBlockState state) {
		onPush(world, directionPushed, oldPosition, position, state);
	}

	/**
	 * Determine if a block can be pushed
	 *
	 * @param world           The current world
	 * @param position        The position of the referenced block
	 * @param state           The state of the referenced block
	 * @param directionOfPush The direction to be pushed in
	 * @return true if the referenced block can be pushed, false otherwise
	 */
	public boolean canBePushed(World world,
							   Position position, IBlockState state,
							   Direction directionOfPush) {
		return world.isPositionWalkableAndBlockEmpty(position.displace(directionOfPush));
	}
}
