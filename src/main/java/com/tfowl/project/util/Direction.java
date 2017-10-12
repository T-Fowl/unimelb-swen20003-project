package com.tfowl.project.util;

/**
 * Enumeration used to represent cardinal directions.
 * Used to avoid accidental forgetting of y being inverted when
 * rendering.
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public enum Direction {
	LEFT(-1, 0),
	RIGHT(1, 0),
	UP(0, -1), //y is inverted
	DOWN(0, 1), //y is inverted
	NONE(0, 0);

	/* Unit vector quantities */
	private float i;
	private float j;

	Direction(float i, float j) {
		this.i = i;
		this.j = j;
	}

	/* Get the unit direction moved in the x axis */
	public float getX() {
		return i;
	}

	/* Get the unit direction moved in the y axis */
	public float getY() {
		return j;
	}

	/**
	 * @param direction Direction to find the reverse of
	 * @return The reverse of the given direction, or {@link Direction#NONE} if NONE is passed.
	 */
	public static Direction reverse(Direction direction) {
		switch (direction) {
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			default:
				return NONE;
		}
	}
}
