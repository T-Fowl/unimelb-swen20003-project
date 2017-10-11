package com.tfowl.project.tile;


import com.tfowl.project.player.Player;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 6/09/2017.
 */
public class Tile {

	private String name;
	private boolean isWalkable;

	public Tile() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isWalkable() {
		return isWalkable;
	}

	public void setWalkable(boolean walkable) {
		isWalkable = walkable;
	}

	public void onBlockMovedOff(World world, Player player) {

	}

	public void onBlockMovedOn(World world, Player player) {
	}
}
