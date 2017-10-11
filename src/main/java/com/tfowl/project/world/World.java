package com.tfowl.project.world;

import com.tfowl.project.block.Block;
import com.tfowl.project.block.BlockInstance;
import com.tfowl.project.effect.EffectInstance;
import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.level.Level;
import com.tfowl.project.logging.Logger;
import com.tfowl.project.logging.LoggerFactory;
import com.tfowl.project.player.Player;
import com.tfowl.project.reference.Graphical;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.tile.Tile;
import com.tfowl.project.tile.TileInstance;
import com.tfowl.project.unit.Unit;
import com.tfowl.project.unit.UnitInstance;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.InputUtil;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 6/09/2017.
 */
public class World implements IRenderable {

	private static final Logger LOGGER = LoggerFactory.getLogger(World.class);

	private Level level;

	private Player player;

	private List<TileInstance> tiles;
	private List<BlockInstance> blocks;
	private List<EffectInstance> effects;
	private List<UnitInstance> units;

	/**
	 * Initialised the world. It is required that this method be called after the OpenGL context
	 * has been created.
	 *
	 * @throws SlickException If the underlying Slick library throws an exception.
	 */
	public void init() throws SlickException {
		player = new Player("Player 1");
		tiles = new ArrayList<>();
		blocks = new ArrayList<>();
		effects = new ArrayList<>();
		units = new ArrayList<>();
	}

	/**
	 * Loads the level and sets it as the current one.
	 *
	 * @param level The level to load.
	 */
	public void loadLevel(Level level) {
		this.level = level;
		player.getUnit().setPosition(new Position(level.getPlayerStartX(), level.getPlayerStartY()));

		tiles.clear();
		blocks.clear();
		effects.clear();
		units.clear();

		for (int x = 0; x < this.level.getLocations().length; x++) {
			for (int y = 0; y < this.level.getLocations()[x].length; y++) {
				if (this.level.getLocations()[x][y] != null) {
					for (String object : this.level.getLocations()[x][y].getObjects()) {
						Object registered = ObjectRegistry.get(object);

						if (registered instanceof Block) {
							BlockInstance instance = new BlockInstance(ObjectRegistry.getBlock(object));
							instance.setPosition(Position.at(x, y));
							blocks.add(instance);
						} else if (registered instanceof Tile) {
							TileInstance instance = new TileInstance(ObjectRegistry.getTile(object));
							instance.setPosition(Position.at(x, y));
							tiles.add(instance);
						} else if (registered instanceof Unit) {
							UnitInstance instance = new UnitInstance(ObjectRegistry.getUnit(object));
							instance.setPosition(Position.at(x, y));
							units.add(instance);
						} else {
							LOGGER.warn("Level referenced unregistered object: " + object);
						}
					}
				}
			}
		}
	}

	public boolean isTileWalkable(Position position) {
		for (TileInstance tile : tiles) {
			if (tile.getPosition().equals(position) && !tile.getTile().isWalkable())
				return false;
		}
		return true;
	}

	@Override
	public int getRenderedWidth() {
		return Graphical.TILE_SIDE_LENGTH * level.getTileCountHorizontal();
	}

	@Override
	public int getRenderedHeight() {
		return Graphical.TILE_SIDE_LENGTH * level.getTileCountVertical();
	}

	@Override
	public void draw(Graphics g, int gx, int gy) throws SlickException {
		for (TileInstance tile : tiles) {
			tile.draw(g, (int) (tile.getPosition().getX() * 32 + gx), (int) (tile.getPosition().getY() * 32 + gy));
		}

		for (BlockInstance block : blocks) {
			block.draw(g, (int) (block.getPosition().getX() * 32 + gx), (int) (block.getPosition().getY() * 32 + gy));
		}

		for (UnitInstance unit : units) {
			unit.draw(g, (int) (unit.getPosition().getY() * 32 + gx), (int) (unit.getPosition().getY() * 32 + gy));
		}

		player.draw(g, (int) (player.getUnit().getPosition().getX() * 32 + gx), (int) (player.getUnit().getPosition().getY() * 32 + gy));
	}

	public void update(Input input, long delta) {

		Direction direction = InputUtil.getDirection(input);
		if (direction != Direction.NONE) {
			Position newPosition = player.getUnit().getPosition().displace(direction, Graphical.PLAYER_MOVEMENT_UNITS);
			if (isTileWalkable(newPosition))
				player.getUnit().setPosition(newPosition);
		}
	}
}
