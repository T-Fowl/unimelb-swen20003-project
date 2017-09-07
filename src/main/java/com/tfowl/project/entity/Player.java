package com.tfowl.project.entity;

/**
 * Created by Thomas on 6/09/2017.
 */
public class Player implements Entity {

	public static final String PLAYER_TILE_NAME = "player";

	private int xCoordinate;
	private int yCoordinate;

	public Player() {
		this(0, 0);
	}

	public Player(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
}
