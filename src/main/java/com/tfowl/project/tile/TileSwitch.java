package com.tfowl.project.tile;

import com.tfowl.project.player.Player;
import com.tfowl.project.states.properties.BooleanProperty;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

import java.util.List;

/**
 * Created by Thomas on 11.10.2017.
 */
public class TileSwitch extends Tile {

	public static final BooleanProperty ACTIVATED_PROPERTY = BooleanProperty.create("activated");

	public TileSwitch() {
		setName("switch");
		setWalkable(true);
	}

	@Override
	public ITileState getDefaultState() {
		ITileState state = super.getDefaultState();
		state.setValue(ACTIVATED_PROPERTY, false);
		return state;
	}

	private void toggleDoors(World world, boolean open) {
		List<TileInstance> doors = world.getTilesOfType(Tiles.DOOR);
		for (TileInstance door : doors) {
			door.getState().setValue(TileDoor.OPEN_PROPERTY, open);
		}
	}

	@Override
	public void onBlockMovedOff(World world, Player player, Position position, ITileState state) {
		super.onBlockMovedOff(world, player, position, state);
		state.setValue(ACTIVATED_PROPERTY, false);
		toggleDoors(world, false);
	}

	@Override
	public void onBlockMovedOn(World world, Player player, Position position, ITileState state) {
		super.onBlockMovedOn(world, player, position, state);
		state.setValue(ACTIVATED_PROPERTY, true);
		toggleDoors(world, true);
	}
}
