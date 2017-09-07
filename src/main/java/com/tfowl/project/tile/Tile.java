package com.tfowl.project.tile;


import com.tfowl.project.graphics.IRenderable;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Represents a renderable, square-area of the world.
 * <p>
 * Created by Thomas on 6/09/2017.
 */
public class Tile implements IRenderable {

	/* The names of all tiles that block the player */
	private static final String[] NON_WALKABLE_TILES = {
			"wall"
	};

	/* The name / id of the tile */
	private final String name;

	/* Image to draw for this Tile */
	private final Image sprite;

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

	/**
	 * Determine is this tile blocks the player.
	 *
	 * @param t Tile to test.
	 * @return True if the tile blocks the player, false otherwise.
	 */
	public static boolean isTileBlocking(Tile t) {
		for (String nonWalkable : NON_WALKABLE_TILES)
			if (nonWalkable.equalsIgnoreCase(t.getName()))
				return true;
		return false;
	}
}
