package com.tfowl.project.world;

import com.tfowl.project.block.Block;
import com.tfowl.project.block.IBlockState;
import com.tfowl.project.effect.Effect;
import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.impl.BlockInstance;
import com.tfowl.project.impl.EffectInstance;
import com.tfowl.project.impl.TileInstance;
import com.tfowl.project.impl.UnitInstance;
import com.tfowl.project.level.Level;
import com.tfowl.project.logging.Logger;
import com.tfowl.project.logging.LoggerFactory;
import com.tfowl.project.player.Player;
import com.tfowl.project.reference.Graphical;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.tile.ITileState;
import com.tfowl.project.tile.Tile;
import com.tfowl.project.unit.Unit;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.InputUtil;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Iterator;
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
		player.setPosition(new Position(level.getPlayerStartX(), level.getPlayerStartY()));

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

	public void createEffectAt(Effect effect, Position position) {
		EffectInstance instance = new EffectInstance(effect, position);
		effects.add(instance);
	}

	public BlockInstance blockAt(Position position) {
		for (BlockInstance block : blocks)
			if (block.getPosition().equals(position))
				return block;
		return null;
	}

	public boolean isTileWalkable(Position position) {
		for (TileInstance tile : tiles) {
			if (tile.getPosition().equals(position) && !tile.getTile().isWalkable(
					this, player, position, tile.getState()
			)) {
				return false;
			}
		}
		return true;
	}

	public List<Position> getPosititionsOfTiles(Tile tile) {
		List<Position> positions = new ArrayList<>();
		for (TileInstance instance : tiles)
			if (instance.getTile().equals(tile))
				positions.add(instance.getPosition());
		return positions;
	}

	public ITileState getTileState(Position position) {
		for (TileInstance instance : tiles)
			if (instance.getPosition().equals(position))
				return instance.getState();
		return null;
	}

	public IBlockState getBlockState(Position position) {
		for (BlockInstance instance : blocks)
			if (instance.getPosition().equals(position))
				return instance.getState();
		return null;
	}

	private void moveBlock(BlockInstance instance, Position newPosition) {
		Position oldPosition = instance.getPosition();
		instance.setPosition(newPosition);

		//TODO: Direction
		instance.getBlock().onPush(this, player, Direction.NONE, oldPosition, newPosition, instance.getState());

		for (TileInstance tile : tiles)
			if (tile.getPosition().equals(newPosition))
				tile.getTile().onBlockMovedOn(this, player, newPosition, tile.getState());
			else if (tile.getPosition().equals(oldPosition))
				tile.getTile().onBlockMovedOff(this, player, oldPosition, tile.getState());
	}

	public boolean isSpaceEmpty(Position position) {
		return isTileWalkable(position) && null == blockAt(position);
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
			unit.draw(g, (int) (unit.getPosition().getX() * 32 + gx), (int) (unit.getPosition().getY() * 32 + gy));
		}

		for (EffectInstance effect : effects) {
			effect.draw(g, (int) (effect.getPosition().getX() * 32 + gx), (int) (effect.getPosition().getY() * 32 + gy));
		}

		player.draw(g, (int) (player.getPosition().getX() * 32 + gx), (int) (player.getPosition().getY() * 32 + gy));
	}

	private void handlePlayerMovement(Direction dir) {
		Position moveTo = player.getPosition().displace(dir, Graphical.PLAYER_MOVEMENT_UNITS);

		//Check if we can actually walk there
		if (isTileWalkable(moveTo)) {
			//Check if we're going to push a block
			BlockInstance block = blockAt(moveTo);
			if (null == block) {
				player.setPosition(moveTo);
			} else if (block.getBlock().isPushable()) {
				Position blockMoveTo = moveTo.displace(dir, 1);
				if (isSpaceEmpty(blockMoveTo)) {
					moveBlock(block, blockMoveTo);
					player.setPosition(moveTo);
				}
			}
		}
	}

	public void restartLevel() {
		loadLevel(level);
	}

	public void update(Input input, long delta) {

		Direction direction = InputUtil.getDirection(input);
		if (direction != Direction.NONE) {
			handlePlayerMovement(direction);
		}

		Iterator<EffectInstance> effectIterator = effects.iterator();
		while (effectIterator.hasNext()) {
			EffectInstance effect = effectIterator.next();
			effect.incrementTime(delta);
			if (effect.getTotalElapsedTime() >= effect.getEffect().getDuration())
				effectIterator.remove();
		}

		for (BlockInstance blockInstance : blocks) {
			blockInstance.getBlock().onTick(this, delta, blockInstance.getPosition(), blockInstance.getState());
		}

		for (UnitInstance unitInstance : units) {
			unitInstance.getUnit().onTick(this, delta, unitInstance.getPosition(), unitInstance.getState());
		}
	}
}
