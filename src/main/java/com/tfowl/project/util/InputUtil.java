package com.tfowl.project.util;

import org.newdawn.slick.Input;

/**
 * Utilities for input detection and computation.
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public class InputUtil {

	/* Listen for arrow-key movements*/
	private static final boolean LISTEN_ARROWS = true;
	/* Listen for WASD-key movements */
	private static final boolean LISTEN_WASD = true;

	/* Returns true if either of the keys refering to a move in a specific direction are pressed */
	private static boolean isDirectionKeyPressed(Input in, int arrowKeyCode, int wasdKeyCode) {
		return (LISTEN_ARROWS && in.isKeyPressed(arrowKeyCode)) || (LISTEN_WASD && in.isKeyPressed(wasdKeyCode));
	}

	public static Direction getDirection(Input in) {
		if (isUp(in)) return Direction.UP;
		if (isDown(in)) return Direction.DOWN;
		if (isRight(in)) return Direction.RIGHT;
		if (isLeft(in)) return Direction.LEFT;
		return Direction.NONE;
	}

	/**
	 * @param in Input object
	 * @return True if a up-movement key has been pressed (e.g. up-arrow or w)
	 */
	public static boolean isUp(Input in) {
		return isDirectionKeyPressed(in, Input.KEY_UP, Input.KEY_W);
	}

	/**
	 * @param in Input object
	 * @return True if a down-movement key has been pressed (e.g. down-arrow or s)
	 */
	public static boolean isDown(Input in) {
		return isDirectionKeyPressed(in, Input.KEY_DOWN, Input.KEY_S);
	}

	/**
	 * @param in Input object
	 * @return True if a left-movement key has been pressed (e.g. left-arrow or a)
	 */
	public static boolean isLeft(Input in) {
		return isDirectionKeyPressed(in, Input.KEY_LEFT, Input.KEY_A);
	}

	/**
	 * @param in Input object
	 * @return True if a right-movement key has been pressed (e.g. right-arrow or d)
	 */
	public static boolean isRight(Input in) {
		return isDirectionKeyPressed(in, Input.KEY_RIGHT, Input.KEY_D);
	}
}
