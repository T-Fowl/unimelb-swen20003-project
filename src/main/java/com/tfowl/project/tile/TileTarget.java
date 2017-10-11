package com.tfowl.project.tile;

import com.tfowl.project.player.Player;
import com.tfowl.project.states.properties.BooleanProperty;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

import java.util.List;

/**
 * Created by Thomas on 11.10.2017.
 */
public class TileTarget extends Tile {

	public static final BooleanProperty COVERED_PROPERTY = BooleanProperty.create("covered");

	public TileTarget() {
		setName("target");
		setWalkable(true);
	}

	@Override
	public ITileState getDefaultState() {
		ITileState state = super.getDefaultState();
		state.setValue(COVERED_PROPERTY, false);
		return state;
	}

	@Override
	public void onBlockMovedOn(World world, Player player, Position position, ITileState state) {
		super.onBlockMovedOn(world, player, position, state);
		state.setValue(COVERED_PROPERTY, true);

		//Check if all others are covered
		List<TileInstance> targets = world.getTilesOfType(this);

		System.out.println("Targets: " + targets);

		boolean allCovered = true;
		for (TileInstance instance : targets) {
			allCovered = instance.getState().getValue(COVERED_PROPERTY);
			if (!allCovered)
				break;
		}

		if (allCovered) {
			System.out.println("All are covered!");
		} else {

		}
	}

	@Override
	public void onBlockMovedOff(World world, Player player, Position position, ITileState state) {
		super.onBlockMovedOff(world, player, position, state);
		state.setValue(COVERED_PROPERTY, false);
	}
}
