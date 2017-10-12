package com.tfowl.project.unit;

import com.tfowl.project.impl.ImplUnitState;
import com.tfowl.project.player.Player;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * An entity is a game object with dynamic properties, such as location.
 * <p>
 * Created by Thomas on 6/09/2017.
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

	public IUnitState getDefaultState() {
		return new ImplUnitState(this);
	}

	public void onPlayerMove(World world, Player player,
							 Direction moveDirection, float moveDistance,
							 Position unitPosition, IUnitState unitState) {

	}

	public void onTick(World world, long delta,
					   Position unitPosition, IUnitState unitState) {

	}

	public void onPlayerTouch(World world, Player player,
							  Position location,
							  IUnitState state) {

	}
}
