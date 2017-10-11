package com.tfowl.project.tile;

import com.tfowl.project.player.Player;
import com.tfowl.project.world.World;

import java.util.List;

/**
 * Created by Thomas on 11.10.2017.
 */
public class TileTarget extends Tile {

	public TileTarget() {
		setName("target");
		setWalkable(true);
	}

	@Override
	public void onBlockMovedOn(World world, Player player) {
		super.onBlockMovedOn(world, player);
		//Check if all other targets have a block over them
		//If yes, signal to the world that the level should end

		System.out.println("Block moved over");

		List<TileInstance> targets = world.getTilesOfType(this);

		System.out.println("Targets: " + targets);

		boolean allCovered = true;
		for (TileInstance instance : targets) {
			if (world.blockAt(instance.getPosition()) == null) {
				allCovered = false;
				break;
			}
		}

		if (allCovered) {
			System.out.println("All are covered!");
		} else {

		}
	}
}
