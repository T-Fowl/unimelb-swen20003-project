package com.tfowl.project.util;

/**
 * Position class used for holding both a position in space and as a vector between two positions.
 * Created by Thomas on 11.10.2017.
 */
public class Position {

	public static final float DEFAULT_DISPLACEMENT = 1.f;

	//Slightly less than half so a block part way over 2 tiles counts as being on both
	private static final float EQUALITY_EPS = 0.4f;

	private float x;
	private float y;

	public Position() {
		this(0, 0);
	}

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The x component of this position.
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return The y component of this position.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Returns the difference between this point and the passed one.
	 *
	 * @param from Other position.
	 * @return A vector version of {@link Position} representing the difference between the two points
	 */
	public Position difference(Position from) {
		return new Position(x - from.x, y - from.y);
	}

	/**
	 * @param direction Direction to move in
	 * @return A new position that is equivalent to this one being moved in the given direction
	 */
	public Position displace(Direction direction) {
		return displace(direction, DEFAULT_DISPLACEMENT);
	}

	/**
	 * @param direction Direction to move in
	 * @param distance  distance to move along the given direction
	 * @return A new position that is equivalent to this one being moved in the given direction
	 */
	public Position displace(Direction direction, float distance) {
		return new Position(getX() + direction.getX() * distance,
				getY() + direction.getY() * distance);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Position)) return false;
		Position other = (Position) o;
		return Math.abs(other.x - x) < EQUALITY_EPS && Math.abs(other.y - y) < EQUALITY_EPS;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	public static Position at(float x, float y) {
		return new Position(x, y);
	}
}
