package com.tfowl.project.level;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.tile.Tile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * A location is a part of a level with some {@link Tile Tiles}. This class allows for multiple {@link Tile Tiles} to be
 * at one position in the level.
 * <p>
 * Later on, this class will either be removed as the duplicate {@link Tile Tiles} will be comes entities or they are stored
 * in the {@link Level} class.
 * <p>
 * Created by Thomas on 6/09/2017.
 */
public class Location implements IRenderable {

	private List<Tile> tiles;

	Location() {
		tiles = new ArrayList<>();
	}

	int getTileCount() {
		return tiles.size();
	}

	void addTileAtTop(Tile tile) {
		addTile(tile, getTileCount());
	}

	private void addTile(Tile tile, int index) {
		tiles.add(index, tile);
	}

	List<Tile> getTiles() {
		return tiles;
	}

	@Override
	public void draw(Graphics g, int x, int y) throws SlickException {
		//Should we go through and draw all of them? In case the tiles
		//on top have transparency?
		if (tiles.size() > 0) {
			tiles.get(tiles.size() - 1).draw(g, x, y);
		}
	}
}
