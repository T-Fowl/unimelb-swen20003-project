package com.tfowl.project.util;

/**
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

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Position difference(Position from) {
		return new Position(x - from.x, y - from.y);
	}

	public Position displace(Direction direction) {
		return displace(direction, DEFAULT_DISPLACEMENT);
	}

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
