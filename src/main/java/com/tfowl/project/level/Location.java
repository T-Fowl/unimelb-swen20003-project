package com.tfowl.project.level;

import com.tfowl.project.tile.Tile;

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
}
