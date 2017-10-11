package com.tfowl.project.util;

/**
 * Created by Thomas on 11.10.2017.
 */
public class Position {

	private static final float EQUALITY_EPS = 1e-3f;

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

	public static Position at(float x, float y) {
		return new Position(x, y);
	}
}
