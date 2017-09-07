package com.tfowl.project.graphics;

import com.tfowl.project.reference.Graphical;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by Thomas on 7/09/2017.
 */
public interface IRenderable {

	public default int getRenderedWidth() throws SlickException {
		return Graphical.TILE_SIDE_LENGTH;
	}

	public default int getRenderedHeight() throws SlickException {
		return Graphical.TILE_SIDE_LENGTH;
	}

	public void draw(Graphics g, int gx, int gy) throws SlickException;

	public default void drawCentered(Graphics g, int gx, int gy) throws SlickException {
		draw(g, gx - getRenderedWidth() / 2, gy - getRenderedHeight() / 2);
	}
}
