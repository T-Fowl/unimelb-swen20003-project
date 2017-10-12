package com.tfowl.project.level;

import com.tfowl.project.logging.Logger;
import com.tfowl.project.logging.LoggerFactory;
import com.tfowl.project.util.Position;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A level is a description of the placement of objects in a given level of the game.
 * It holds the position of all objects as well as the starting position of the player
 * <p>
 * Created by Thomas on 6/09/2017.
 */
public class Level {

	private static final Logger logger = LoggerFactory.getLogger(Level.class);

	/* Size of the level */
	private final int tileCountHorizontal;
	private final int tileCountVertical;

	/* All of the locations in the level */
	private final Location[][] locations;

	/* Player starting coordinates */
	private Position playerStartPosition;

	private Level(int tileCountHorizontal, int tileCountVertical) {
		this(tileCountHorizontal, tileCountVertical, 0, 0);
	}

	private Level(int tileCountHorizontal, int tileCountVertical, int playerStartX, int playerStartY) {
		this.playerStartPosition = new Position(playerStartX, playerStartY);
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

	public Position getPlayerStartPosition() {
		return playerStartPosition;
	}

	public void setPlayerStartPosition(Position position) {
		this.playerStartPosition = position;
	}

	public Location[][] getLocations() {
		return locations;
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
				String objectName = scanner.next();
				int objectX = scanner.nextInt();
				int objectY = scanner.nextInt();

				if (objectName.equalsIgnoreCase("player")) { //TODO
					building.setPlayerStartPosition(Position.at(objectX, objectY));
				} else {
					/* Get the referenced location (creating if needed) and add the object to it */
					Location location = building.locations[objectX][objectY];
					if (null == location)
						location = building.locations[objectX][objectY] = new Location();
					location.addObjectAtTop(objectName);
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
