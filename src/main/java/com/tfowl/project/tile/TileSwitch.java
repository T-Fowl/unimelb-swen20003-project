package com.tfowl.project.tile;

import com.tfowl.project.player.Player;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 11.10.2017.
 */
public class TileSwitch extends Tile {

	public TileSwitch() {
		setName("switch");
		setWalkable(true);
	}

	@Override
	public void onBlockMovedOff(World world, Player player) {
		super.onBlockMovedOff(world, player);

		//Set state to off
		//Close all doors
	}

	@Override
	public void onBlockMovedOn(World world, Player player) {
		super.onBlockMovedOn(world, player);

		//Set state to activated
		//Open all doors
	}
}
