package com.tfowl.project.init;

import com.tfowl.project.reference.Resources;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.tile.Tile;
import com.tfowl.project.tile.TileDoor;
import com.tfowl.project.tile.TileSwitch;
import com.tfowl.project.tile.TileTarget;

/**
 * Creates and registers all of the default tiles
 */
public class Tiles {

	/* Simple tile, does nothing. No need for a custom class */
	public static final Tile CRACKED_WALL = new Tile().setName(Resources.Tiles.CRACKED_NAME).setWalkable(false);

	public static final Tile DOOR = new TileDoor();

	/* Simple tile, does nothing. No need for a custom class */
	public static final Tile FLOOR = new Tile().setName(Resources.Tiles.FLOOR_NAME).setWalkable(true);

	public static final Tile SWITCH = new TileSwitch();

	public static final Tile TARGET = new TileTarget();

	/* Simple tile, does nothing. No need for a custom class */
	public static final Tile WALL = new Tile().setName(Resources.Tiles.WALL_NAME).setWalkable(false);

	/**
	 * Register all of the default tiles
	 */
	public static void init() {
		ObjectRegistry.register(CRACKED_WALL);
		ObjectRegistry.register(DOOR);
		ObjectRegistry.register(FLOOR);
		ObjectRegistry.register(SWITCH);
		ObjectRegistry.register(TARGET);
		ObjectRegistry.register(WALL);
	}
}
