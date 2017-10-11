package com.tfowl.project.tile;

import com.tfowl.project.registry.ObjectRegistry;

public class Tiles {

	public static final Tile CRACKED_WALL = new TileCrackedWall();
	public static final Tile DOOR = new TileDoor();
	public static final Tile FLOOR = new TileFloor();
	public static final Tile SWITCH = new TileSwitch();
	public static final Tile TARGET = new TileTarget();
	public static final Tile WALL = new TileWall();

	public static void init() {
		ObjectRegistry.register(CRACKED_WALL);
		ObjectRegistry.register(DOOR);
		ObjectRegistry.register(FLOOR);
		ObjectRegistry.register(SWITCH);
		ObjectRegistry.register(TARGET);
		ObjectRegistry.register(WALL);
	}
}
