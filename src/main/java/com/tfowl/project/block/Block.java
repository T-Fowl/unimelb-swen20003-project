package com.tfowl.project.block;

import com.tfowl.project.impl.ImplBlockState;
import com.tfowl.project.player.Player;
import com.tfowl.project.unit.IUnitState;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
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

	public IBlockState getDefaultState() {
		return new ImplBlockState(this);
	}

	public void onTick(World world, long delta,
					   Position position, IBlockState state) {

	}

	public void onPush(World world,
					   IUnitState pushingUnitState, Direction directionPushed, Position oldPosition,
					   Position position, IBlockState state) {

	}

	public void onPush(World world,
					   Player pushingPlayer, Direction directionPushed, Position oldPosition,
					   Position position, IBlockState state) {

	}

	public boolean canDoPush(World world,
							 Direction directionOfPush,
							 Position position, IBlockState state) {
		return world.isSpaceEmpty(position.displace(directionOfPush));
	}
}
