package com.tfowl.project.level;

/**
 * Created by Thomas on 6/09/2017.
 */
public class Level {

	private int tileCountHorizontal;
	private int tileCountVertical;
	private Location[][] locations;

	private int playerStartX;
	private int playerStartY;

	public Level(int tileCountHorizontal, int tileCountVertical) {
		this.tileCountHorizontal = tileCountHorizontal;
		this.tileCountVertical = tileCountVertical;
		this.locations = new Location[tileCountHorizontal][tileCountVertical];
	}

	public int getTileCountHorizontal() {
		return tileCountHorizontal;
	}

	public int getTileCountVertical() {
		return tileCountVertical;
	}

	public int getPlayerStartX() {
		return playerStartX;
	}

	public void setPlayerStartX(int playerStartX) {
		this.playerStartX = playerStartX;
	}

	public int getPlayerStartY() {
		return playerStartY;
	}

	public void setPlayerStartY(int playerStartY) {
		this.playerStartY = playerStartY;
	}
}
