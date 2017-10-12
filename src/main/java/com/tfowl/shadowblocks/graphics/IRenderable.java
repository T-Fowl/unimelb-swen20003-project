package com.tfowl.shadowblocks.graphics;

import com.tfowl.shadowblocks.reference.Graphical;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Represents something that can be rendered. The primary purpose of this class is to handle
 * drawing something that is centered without said component or its parent having to worry
 * about the necessary offsets.
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public interface IRenderable {

	/**
	 * Get or compute the rendered width of this {@link IRenderable}.
	 * Defaults to {@link Graphical#TILE_SIDE_LENGTH}.
	 *
	 * @return The rendered width of this {@link IRenderable}
	 */
	public default int getRenderedWidth() {
		return Graphical.TILE_SIDE_LENGTH;
	}

	/**
	 * Get or compute the rendered height of this {@link IRenderable}.
	 * Defaults to {@link Graphical#TILE_SIDE_LENGTH}.
	 *
	 * @return The rendered height of this {@link IRenderable}
	 */
	public default int getRenderedHeight() {
		return Graphical.TILE_SIDE_LENGTH;
	}

	/**
	 * Draw this {@link IRenderable} with its origin at (gx, gy).
	 *
	 * @param g  Graphics context.
	 * @param gx Origin x.
	 * @param gy Origin y.
	 * @throws SlickException If the underlying slick library throws an exception.
	 */
	public void draw(Graphics g, float gx, float gy) throws SlickException;

	/**
	 * Draw this {@link IRenderable} with its center at (gx, gy). Default implementation is to
	 * draw with the origin translated away from the origin by half the width and height in
	 * their respective axis.
	 *
	 * @param g  Graphics context.
	 * @param gx Center x.
	 * @param gy Center y.
	 * @throws SlickException If the underlying slick library throws an exception.
	 */
	public default void drawCentered(Graphics g, float gx, float gy) throws SlickException {
		draw(g, gx - getRenderedWidth() / 2, gy - getRenderedHeight() / 2);
	}
}
