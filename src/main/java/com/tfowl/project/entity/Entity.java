package com.tfowl.project.entity;

/**
 * Created by Thomas on 6/09/2017.
 */
public class Entity {

	private String name;

	private int x;
	private int y;

	public Entity(String name) {
		this(name, 0, 0);
	}

	public Entity(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int moveX(int delta) {
		int newX = getX() + delta;
		setX(newX);
		return newX;
	}

	public int moveY(int delta) {
		int newY = getY() + delta;
		setY(newY);
		return newY;
	}
}
