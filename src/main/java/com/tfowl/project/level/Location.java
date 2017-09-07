package com.tfowl.project.level;

import com.tfowl.project.tile.Tile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 6/09/2017.
 */
public class Location {

	private List<Tile> tiles;

	public Location() {
		tiles = new ArrayList<>();
	}

	public int getTileCount() {
		return tiles.size();
	}

	public void addTileAtTop(Tile tile) {
		addTile(tile, getTileCount());
	}

	public void addTile(Tile tile, int index) {
		tiles.add(index, tile);
	}

	public void draw(Graphics g, int x, int y) throws SlickException {
		//Should we go through and draw all of them? In case the tiles
		//on top have transparency?
		if (tiles.size() > 0) {
			tiles.get(tiles.size() - 1).draw(g, x, y);
		}
	}
}
