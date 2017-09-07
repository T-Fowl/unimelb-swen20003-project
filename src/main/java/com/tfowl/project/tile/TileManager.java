package com.tfowl.project.tile;

import com.tfowl.project.reference.Resources;
import com.tfowl.project.util.ResourceLoader;
import org.newdawn.slick.Image;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Manager class used for loading and retrieval of {@link Tile Tiles}.
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public class TileManager {

	/* A map of all loaded tiles. key = id(name) */
	private static final ConcurrentMap<String, Tile> loadedTiles;

	static {
		loadedTiles = new ConcurrentHashMap<>();
	}

	/**
	 * Attempt to retrieve or load a {@link Tile} from its name.
	 *
	 * @param name The name of the {@link Tile}.
	 * @return The {@link Tile} matching the given name, or not.
	 */
	public static Optional<Tile> getTileFromName(String name) {
		return Optional.ofNullable(
				loadedTiles.computeIfAbsent(name.toLowerCase().trim(), tileName -> {
					try {
						Image tileImage = ResourceLoader.getImageResource(
								Resources.TILES_DIRECTORY_NAME + "/" + tileName);
						return new Tile(tileName, tileImage);
					} catch (Throwable t) {
						return null; //Causes the map to not be updated, and the optional to be empty
					}
				})
		);
	}
}
