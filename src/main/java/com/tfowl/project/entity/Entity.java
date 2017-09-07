package com.tfowl.project.entity;

import com.tfowl.project.util.Direction;

/**
 * An entity is a game object with dynamic properties, such as location.
 * <p>
 * Created by Thomas on 6/09/2017.
 */
public class Entity {

	/* name of the entity - analogous to an id */
	private final String name;

	/* position in the world */
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

	/**
	 * @return The name / identification of this entity.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The entity's x coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x coordinate to assign to this entity.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return The entity's y coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y coordinate to assign to this entity.
	 */
	public void setY(int y) {
		this.y = y;
	}


	/**
	 * Moves the entity by the specified about along the x axis.
	 *
	 * @param delta Amount to move. Positive is right, negative is left.
	 */
	public void moveX(int delta) {
		setX(getX() + delta);
	}

	/**
	 * Moves the entity by the specified about along the y axis.
	 *
	 * @param delta Amount to move. Positive is down, negative is up.
	 */
	public void moveY(int delta) {
		setY(getY() + delta);
	}

	/**
	 * Moves the entity by the specified amount in some direction.
	 *
	 * @param direction The direction to move.
	 * @param units     Amount to move by.
	 */
	public void move(Direction direction, int units) {
		moveX(direction.getX() * units);
		moveY(direction.getY() * units);
	}
}
