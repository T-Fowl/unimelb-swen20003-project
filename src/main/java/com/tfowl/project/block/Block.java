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

	// @formatter:off

	//Resource loading
	private String name;

	private boolean isPushable = true;

	private boolean isSlidingBlock = false;
	private float   slidingSpeed   = 0; //Tiles / sec

	// @formatter:on

	public Block() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSlidingBlock() {
		return isSlidingBlock;
	}

	public void setSlidingBlock(boolean slidingBlock) {
		isSlidingBlock = slidingBlock;
	}

	public float getSlidingSpeed() {
		return slidingSpeed;
	}

	public void setSlidingSpeed(float slidingSpeed) {
		this.slidingSpeed = slidingSpeed;
	}

	public boolean isPushable() {
		return isPushable;
	}

	public void setPushable(boolean pushable) {
		isPushable = pushable;
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
		return world.isSpaceEmpty(position.displace(directionOfPush, 1)); //TODO
	}
}
