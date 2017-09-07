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
	DOWN(0, 1); //y is inverted

	/* Unit vector quantities */
	private int i, j;

	Direction(int i, int j) {
		this.i = i;
		this.j = j;
	}

	/* Get the unit direction moved in the x axis */
	public int getX() {
		return i;
	}

	/* Get the unit direction moved in the y axis */
	public int getY() {
		return j;
	}
}
