package com.tfowl.project.level;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.logging.Logger;
import com.tfowl.project.logging.LoggerFactory;
import com.tfowl.project.reference.Graphical;
import com.tfowl.project.tile.Tile;
import com.tfowl.project.tile.TileManager;
import com.tfowl.project.unit.Player;
import com.tfowl.project.unit.Unit;
import com.tfowl.project.util.Direction;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A level is responsible for holding reference to the location of all {@link Tile Tiles} and eventually {@link Unit Entities}.
 * Also has the starting location of the player.
 * <p>
 * Created by Thomas on 6/09/2017.
 */
public class Level implements IRenderable {

	private static final Logger logger = LoggerFactory.getLogger(Level.class);

	/* Size of the level */
	private final int tileCountHorizontal;
	private final int tileCountVertical;

	/* All of the locations in the level */
	private final Location[][] locations;

	/* Player starting coordinates */
	private int playerStartX;
	private int playerStartY;

	private Level(int tileCountHorizontal, int tileCountVertical) {
		this(tileCountHorizontal, tileCountVertical, 0, 0);
	}

	private Level(int tileCountHorizontal, int tileCountVertical, int playerStartX, int playerStartY) {
		this.playerStartX = playerStartX;
		this.playerStartY = playerStartY;
		this.tileCountHorizontal = tileCountHorizontal;
		this.tileCountVertical = tileCountVertical;
		this.locations = new Location[tileCountHorizontal][tileCountVertical];
	}

	public int getTileCountHorizontal() {
		return tileCountHorizontal;
	}

	public int getTileCountVertical() {
		return tileCountVertical;
	}

	public int getPlayerStartX() {
		return playerStartX;
	}

	public void setPlayerStartX(int playerStartX) {
		this.playerStartX = playerStartX;
	}

	public int getPlayerStartY() {
		return playerStartY;
	}

	public void setPlayerStartY(int playerStartY) {
		this.playerStartY = playerStartY;
	}

	/**
	 * Determines if a player at position (x,y) can walk in direction dir.
	 *
	 * @param x The x coordinate of the player.
	 * @param y The y coordinate of the player.
	 * @return True if the player can walk on this location, false otherwise.
	 */
	public boolean canWalkInDirection(int x, int y, Direction dir) {
		x += dir.getX();
		y += dir.getY();
		if (null == locations[x][y])
			return false;
		Location location = locations[x][y];
		if (0 == location.getTileCount())
			return false;
		for (Tile tile : location.getTiles()) {
			if (Tile.isTileBlocking(tile))
				return false;
		}
		return true;
	}

	@Override
	public int getRenderedWidth() throws SlickException {
		return Graphical.TILE_SIDE_LENGTH * tileCountHorizontal;
	}

	@Override
	public int getRenderedHeight() throws SlickException {
		return Graphical.TILE_SIDE_LENGTH * tileCountVertical;
	}

	@Override
	public void draw(org.newdawn.slick.Graphics g, int gx, int gy) throws SlickException {
		/* Iterate through all locations and draw them */
		for (int x = 0; x < tileCountHorizontal; x++) {
			for (int y = 0; y < tileCountVertical; y++) {
				/* Some locations will be null as they are empty space */
				if (null != locations[x][y])
					locations[x][y].draw(g, gx + x * Graphical.TILE_SIDE_LENGTH, gy + y * Graphical.TILE_SIDE_LENGTH);
			}
		}
	}

	/**
	 * Reads and parses the {@link InputStream} into a {@link Level} instance.
	 * <p>
	 * The format of the stream is meant to be CSV. The first line should have 2 attributes (dimensions
	 * of the level), with all remaining lines having 3 attributes (tile name, tile x, tile y).
	 *
	 * @param is Stream to read and parse.
	 * @return The {@link Level} parsed from the stream.
	 * @throws IOException If there is an error in reading.
	 */
	public static Level readFromStream(InputStream is) throws IOException {
		try (Scanner scanner = new Scanner(is)) {
			/* This means we don't have to split on every line, we can just read 2/3 terms at a time. */
			scanner.useDelimiter("\\p{javaWhitespace}+|,");

			int width = scanner.nextInt();
			int height = scanner.nextInt();

			/* Make a new level instance to construct */
			Level building = new Level(width, height);

			while (scanner.hasNextLine()) {
				/* The 3 attributes separated by a comma */
				if (!scanner.hasNext()) {
					break; // Trailing new-line
				}
				String tileName = scanner.next();
				int tileX = scanner.nextInt();
				int tileY = scanner.nextInt();

				if (tileName.equalsIgnoreCase(Player.PLAYER_NAME)) {
					building.playerStartX = tileX;
					building.playerStartY = tileY;
				} else {
					/* Get the referenced location (creating if needed) and add the tile to it */
					Location location = building.locations[tileX][tileY];
					if (null == location)
						location = building.locations[tileX][tileY] = new Location();
					TileManager.getTileFromName(tileName).ifPresent(location::addTileAtTop);
				}
			}
			return building;
		} catch (InputMismatchException e) {
			logger.error("Reading from stream", e);
			throw new IOException("Invalid level file.");
		} catch (NoSuchElementException e) {
			logger.error("Reading from stream", e);
			throw new IOException("Level file finished early.");
		}
	}
}
