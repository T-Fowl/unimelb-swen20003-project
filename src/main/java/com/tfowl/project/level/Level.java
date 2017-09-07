package com.tfowl.project.level;

import com.tfowl.project.entity.Player;
import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.reference.Graphical;
import com.tfowl.project.tile.Tile;
import com.tfowl.project.tile.TileManager;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Thomas on 6/09/2017.
 */
public class Level implements IRenderable {

	private int tileCountHorizontal;
	private int tileCountVertical;
	private Location[][] locations;

	private int playerStartX;
	private int playerStartY;

	public Level(int tileCountHorizontal, int tileCountVertical) {
		this(tileCountHorizontal, tileCountVertical, 0, 0);
	}

	public Level(int tileCountHorizontal, int tileCountVertical, int playerStartX, int playerStartY) {
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

	public boolean isBlockWalkable(int x, int y) {
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
		for (int x = 0; x < tileCountHorizontal; x++) {
			for (int y = 0; y < tileCountVertical; y++) {
				if (null != locations[x][y])
					locations[x][y].draw(g, gx + x * Graphical.TILE_SIDE_LENGTH, gy + y * Graphical.TILE_SIDE_LENGTH);
			}
		}
	}

	public static Level readFromStream(InputStream is) throws IOException {
		try (Scanner scanner = new Scanner(is)) {
			scanner.useDelimiter("\\p{javaWhitespace}+|,");

			int width = scanner.nextInt();
			int height = scanner.nextInt();

			Level building = new Level(width, height);

			while (scanner.hasNextLine()) {
				String tileName = scanner.next();
				int tileX = scanner.nextInt();
				int tileY = scanner.nextInt();

				if (tileName.equalsIgnoreCase(Player.PLAYER_TILE_NAME)) {
					building.playerStartX = tileX;
					building.playerStartY = tileY;
				} else {
					Location location = building.locations[tileX][tileY];
					if (null == location)
						location = building.locations[tileX][tileY] = new Location();
					TileManager.getTileFromName(tileName).ifPresent(location::addTileAtTop);
				}
			}

			return building;
		} catch (InputMismatchException e) {
			throw new IOException("Invalid level file.");
		} catch (NoSuchElementException e) {
			throw new IOException("Level file finished early.");
		}
	}
}
