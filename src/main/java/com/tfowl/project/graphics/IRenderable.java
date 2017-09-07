package com.tfowl.project.graphics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by Thomas on 7/09/2017.
 */
public interface IRenderable {

	public int getRenderedWidth() throws SlickException;

	public int getRenderedHeight() throws SlickException;

	public void draw(Graphics g, int gx, int gy) throws SlickException;

	public default void drawCentered(Graphics g, int gx, int gy) throws SlickException {
		draw(g, gx - getRenderedWidth() / 2, gy - getRenderedHeight() / 2);
	}
}
