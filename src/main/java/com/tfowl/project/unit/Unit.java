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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IUnitState getDefaultState() {
		return new ImplUnitState(this);
	}

	public void onPlayerMove(World world, Player player,
							 Direction direction, float distance,
							 Position position, IUnitState state) {

	}

	public void onTick(World world, long delta,
					   Position position, IUnitState state) {

	}
}
