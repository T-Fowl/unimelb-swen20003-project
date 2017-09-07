package com.tfowl.project.entity;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.util.ResourceLoader;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by Thomas on 6/09/2017.
 */
public class Player implements Entity, IRenderable {

	public static final String PLAYER_TILE_NAME = "player";

	private int xCoordinate;
	private int yCoordinate;
	private Image sprite;

	public Player() {
		this(0, 0);
	}

	public Player(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public void init() throws SlickException {
		sprite = ResourceLoader.getImageResource(PLAYER_TILE_NAME);
	}

	public int getX() {
		return xCoordinate;
	}

	public void setX(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public void moveX(int delta) {
		setX(getX() + delta);
	}

	public int getY() {
		return yCoordinate;
	}

	public void setY(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public void moveY(int delta) {
		setY(getY() + delta);
	}

	@Override
	public void draw(Graphics g, int gx, int gy) throws SlickException {
		g.drawImage(sprite, gx, gy);
	}
}
