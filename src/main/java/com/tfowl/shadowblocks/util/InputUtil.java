package com.tfowl.shadowblocks.util;

import org.newdawn.slick.Input;

/**
 * Utilities for input detection and computation.
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public class InputUtil {

	public static class KeyMap {

		private int leftKey, rightKey, upKey, downKey, undoKey, restartKey;

		public KeyMap(int leftKey, int rightKey, int upKey, int downKey, int undoKey, int restartKey) {
			this.leftKey = leftKey;
			this.rightKey = rightKey;
			this.upKey = upKey;
			this.downKey = downKey;
			this.undoKey = undoKey;
			this.restartKey = restartKey;
		}

		public int getLeftKey() {
			return leftKey;
		}

		public int getRightKey() {
			return rightKey;
		}

		public int getUpKey() {
			return upKey;
		}

		public int getDownKey() {
			return downKey;
		}

		public int getUndoKey() {
			return undoKey;
		}

		public int getRestartKey() {
			return restartKey;
		}

		public static final KeyMap WASD = new KeyMap(Input.KEY_A, Input.KEY_D, Input.KEY_W, Input.KEY_S, Input.KEY_Z, Input.KEY_R);

		public static final KeyMap ARROWS = new KeyMap(Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_UP, Input.KEY_DOWN, Input.KEY_NUMPAD0, Input.KEY_NUMPAD1);
	}


	/**
	 * @param in      Input keys
	 * @param mapping Key mapping to check for
	 * @return A {@link Direction} representation of the current pressed keys configuration.
	 */
	public static Direction getDirection(Input in, KeyMap mapping) {
		if (in.isKeyPressed(mapping.getUpKey())) return Direction.UP;
		if (in.isKeyPressed(mapping.getDownKey())) return Direction.DOWN;
		if (in.isKeyPressed(mapping.getRightKey())) return Direction.RIGHT;
		if (in.isKeyPressed(mapping.getLeftKey())) return Direction.LEFT;
		return Direction.NONE;
	}
}
