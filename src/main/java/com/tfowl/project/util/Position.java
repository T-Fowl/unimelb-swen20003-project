package com.tfowl.project.util;

/**
 * Created by Thomas on 11.10.2017.
 */
public class Position {

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

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
