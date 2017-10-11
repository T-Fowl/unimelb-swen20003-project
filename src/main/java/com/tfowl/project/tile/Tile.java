package com.tfowl.project.tile;


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
}
