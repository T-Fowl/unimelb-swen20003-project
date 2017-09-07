package com.tfowl.project.tile;


import com.tfowl.project.graphics.IRenderable;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by Thomas on 6/09/2017.
 */
public class Tile implements IRenderable {

	private String name;
	private Image sprite;

	public Tile(String name, Image sprite) {
		this.name = name;
		this.sprite = sprite;
	}

	public String getName() {
		return name;
	}

	@Override
	public void draw(Graphics g, int x, int y) throws SlickException {
		g.drawImage(sprite, x, y);
	}

	public static boolean isTileBlocking(Tile t) {
		return t.name.equalsIgnoreCase("wall") || t.name.equalsIgnoreCase("stone");
	}
}
