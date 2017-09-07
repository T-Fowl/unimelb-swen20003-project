package com.tfowl.project.tile;

import com.tfowl.project.util.ResourceLoader;
import org.newdawn.slick.Image;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Thomas on 7/09/2017.
 */
public class TileManager {

	private static ConcurrentMap<String, Tile> loadedTiles;

	static {
		loadedTiles = new ConcurrentHashMap<>();
	}

	public static Optional<Tile> getTileFromName(String name) {
		return Optional.ofNullable(
				loadedTiles.computeIfAbsent(name.toLowerCase().trim(), tileName -> {
					Image tileImage = ResourceLoader.getImageResource(tileName);
					return null == tileImage ? null : new Tile(tileName, tileImage);
				})
		);
	}
}
