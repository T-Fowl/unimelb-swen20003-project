package com.tfowl.project.world;

import com.tfowl.project.block.BlockInstance;
import com.tfowl.project.effect.EffectInstance;
import com.tfowl.project.level.Level;
import com.tfowl.project.logging.Logger;
import com.tfowl.project.logging.LoggerFactory;
import com.tfowl.project.player.Player;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.tile.TileInstance;
import com.tfowl.project.unit.UnitInstance;
import com.tfowl.project.util.Position;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 6/09/2017.
 */
public class World {

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
	}

	/**
	 * Loads the level and sets it as the current one.
	 *
	 * @param level The level to load.
	 */
	public void loadLevel(Level level) {
		this.level = level;
		player.getUnit().setPosition(new Position(level.getPlayerStartX(), level.getPlayerStartY()));

		tiles = new ArrayList<>();
		blocks = new ArrayList<>();
		effects = new ArrayList<>();
		units = new ArrayList<>();

		for (int x = 0; x < this.level.getLocations().length; x++) {
			for (int y = 0; y < this.level.getLocations()[x].length; y++) {
				if (this.level.getLocations()[x][y] != null) {
					for (String object : this.level.getLocations()[x][y].getObjects()) {
						if (ObjectRegistry.isBlockRegistered(object)) {
							BlockInstance instance = new BlockInstance(ObjectRegistry.getBlock(object));
							instance.setPosition(new Position(x, y));
							blocks.add(instance);
						} else if (ObjectRegistry.isTileRegistered(object)) {
							TileInstance instance = new TileInstance(ObjectRegistry.getTile(object));
							instance.setPosition(new Position(x, y));
							tiles.add(instance);
						} else if (ObjectRegistry.isUnitRegistered(object)) {
							UnitInstance instance = new UnitInstance(ObjectRegistry.getUnit(object));
							instance.setPosition(new Position(x, y));
							units.add(instance);
						} else {
							LOGGER.warn("Level referenced unregistered object: " + object);
						}
					}
				}
			}
		}
	}


	public void draw(Graphics g, GameContainer gc) throws SlickException {

		for (TileInstance tile : tiles) {
			tile.draw(g, (int) (tile.getPosition().getX() * 32), (int) (tile.getPosition().getY() * 32));
		}

		for (BlockInstance block : blocks) {
			block.draw(g, (int) (block.getPosition().getX() * 32), (int) (block.getPosition().getY() * 32));
		}

		for (UnitInstance unit : units) {
			unit.draw(g, (int) (unit.getPosition().getY() * 32), (int) (unit.getPosition().getY() * 32));
		}

		player.draw(g, (int) (player.getUnit().getPosition().getX() * 32), (int) (player.getUnit().getPosition().getY() * 32));

		/* Draw the level centered, then the player at the correct location */
		//level.drawCentered(g, gc.getWidth() / 2, gc.getHeight() / 2);
		//player.draw(g,
		//		(gc.getWidth() - level.getRenderedWidth()) / 2 + Graphical.TILE_SIDE_LENGTH * player.getX(),
		//		(gc.getHeight() - level.getRenderedHeight()) / 2 + Graphical.TILE_SIDE_LENGTH * player.getY());
	}

	public void update(Input input) {
		/* One move per update. This stops diagonal movement. */
//		boolean hasPlayerMoved = false;
//
//		/* Go through the 4 cardinal directions and move the player if appropriate. Only one move per update. */
//		if (InputUtil.isUp(input)) {
//			if (level.canWalkInDirection(player.getX(), player.getY(), Direction.UP)) {
//				player.move(Direction.UP, Graphical.PLAYER_MOVEMENT_UNITS);
//				hasPlayerMoved = true;
//			}
//		}
//		if (InputUtil.isRight(input) && !hasPlayerMoved) {
//			if (level.canWalkInDirection(player.getX(), player.getY(), Direction.RIGHT)) {
//				player.move(Direction.RIGHT, Graphical.PLAYER_MOVEMENT_UNITS);
//				hasPlayerMoved = true;
//			}
//		}
//		if (InputUtil.isDown(input) && !hasPlayerMoved) {
//			if (level.canWalkInDirection(player.getX(), player.getY(), Direction.DOWN)) {
//				player.move(Direction.DOWN, Graphical.PLAYER_MOVEMENT_UNITS);
//				hasPlayerMoved = true;
//			}
//		}
//		if (InputUtil.isLeft(input) && !hasPlayerMoved) {
//			if (level.canWalkInDirection(player.getX(), player.getY(), Direction.LEFT)) {
//				player.move(Direction.LEFT, Graphical.PLAYER_MOVEMENT_UNITS);
//				hasPlayerMoved = true; //Not needed but kept for future expansion of this method
//			}
//		}
	}
}
