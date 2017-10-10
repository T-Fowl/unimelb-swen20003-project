package com.tfowl.project.unit;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.reference.Resources;
import com.tfowl.project.util.ResourceLoader;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents a controllable entity.
 * <p>
 * Created by Thomas on 6/09/2017.
 */
public class Player extends Unit implements IRenderable {

	public static final String PLAYER_NAME = "player";

	private Image sprite;

	public Player() {
		super(PLAYER_NAME);
	}

	public void init() throws SlickException {
		sprite = ResourceLoader.getImageResource(Resources.PLAYER_IMAGE_DIRECTORY_NAME + "/" + PLAYER_NAME);
	}

	@Override
	public void draw(Graphics g, int gx, int gy) throws SlickException {
		g.drawImage(sprite, gx, gy);
	}
}
