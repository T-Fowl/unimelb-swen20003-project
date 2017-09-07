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

	@Override
	public void draw(Graphics g, int gx, int gy) throws SlickException {
		g.drawImage(sprite, gx, gy);
	}
}
