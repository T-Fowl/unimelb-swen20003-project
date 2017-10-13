package com.tfowl.shadowblocks.reference;

/**
 * A set of graphical-related constants.
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public class Graphical {

	/* The square-side-length of a tile in this game. */
	public static final int TILE_SIDE_LENGTH = 32;

	/* Default game-window values */
	public static final int DEFAULT_SCREEN_WIDTH = 800;
	public static final int DEFAULT_SCREEN_HEIGHT = 600;
	public static final boolean DEFAULT_FULLSCREEN_FLAG = false;

	/* How many units a player should move in a single update */
	public static final int PLAYER_MOVEMENT_UNITS = 1;

	public static final String ICON_NAME = "icon.png";

	public static final int DISPLAY_MOVES_X = 10;
	public static final int DISPLAY_MOVES_Y = 25;

	public static final int DISPLAY_TIME_X = 10;
	public static final int DISPLAY_TIME_Y = 40;

	/* No instancing */
	private Graphical() {
	}
}
