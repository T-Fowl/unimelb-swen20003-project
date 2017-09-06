package com.tfowl.project.level;

/**
 * Created by Thomas on 6/09/2017.
 */
public class Level {

	private int tileCountHorizontal;
	private int tileCountVertical;
	private LevelLocation[][] locations;

	private int playerStartX;
	private int playerStartY;

	public Level(int tileCountHorizontal, int tileCountVertical) {
		this.tileCountHorizontal = tileCountHorizontal;
		this.tileCountVertical = tileCountVertical;
		this.locations = new LevelLocation[tileCountHorizontal][tileCountVertical];
	}

	public int getTileCountHorizontal() {
		return tileCountHorizontal;
	}

	public int getTileCountVertical() {
		return tileCountVertical;
	}
}
