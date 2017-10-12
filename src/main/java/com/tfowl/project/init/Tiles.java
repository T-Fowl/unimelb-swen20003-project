package com.tfowl.project.init;

import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.tile.Tile;
import com.tfowl.project.tile.TileDoor;
import com.tfowl.project.tile.TileSwitch;
import com.tfowl.project.tile.TileTarget;

public class Tiles {

	public static final Tile CRACKED_WALL = new Tile().setName("cracked").setWalkable(false);
	public static final Tile DOOR = new TileDoor();
	public static final Tile FLOOR = new Tile().setName("floor").setWalkable(true);
	public static final Tile SWITCH = new TileSwitch();
	public static final Tile TARGET = new TileTarget();
	public static final Tile WALL = new Tile().setName("wall").setWalkable(false);

	public static void init() {
		ObjectRegistry.register(CRACKED_WALL);
		ObjectRegistry.register(DOOR);
		ObjectRegistry.register(FLOOR);
		ObjectRegistry.register(SWITCH);
		ObjectRegistry.register(TARGET);
		ObjectRegistry.register(WALL);
	}
}
