package com.tfowl.project.tile;

import com.tfowl.project.player.Player;
import com.tfowl.project.states.properties.PropertyBoolean;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 11.10.2017.
 */
public class TileDoor extends Tile {

	public static final PropertyBoolean OPEN_PROPERTY = PropertyBoolean.create("open");

	public TileDoor() {
		setName("door");
		setWalkable(false);
	}

	@Override
	public ITileState getDefaultState() {
		ITileState state = super.getDefaultState();
		state.setValue(OPEN_PROPERTY, false);
		return state;
	}

	@Override
	public boolean isWalkable(World world, Player player, Position position, ITileState state) {
		return state.getValue(OPEN_PROPERTY);
	}

	@Override
	public boolean shouldRender(World world, Position position, ITileState state) {
		return !state.getValue(OPEN_PROPERTY);
	}
}
