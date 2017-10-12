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
import com.tfowl.project.unit.IUnitState;
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
 * World class
 * Created by Thomas on 6/09/2017.
 */
public class World implements IRenderable {

	private static final Logger LOGGER = LoggerFactory.getLogger(World.class);

	private List<Level> levels;
	private int currentLevelIndex = 0;
	private Level currentLevel;
	private boolean shouldLoadNewLevel = false;
	private boolean levelIsFailed = false;

	private Player player;
	private int playerMoveCount = 0;

	private List<TileInstance> tiles;
	private List<BlockInstance> blocks;
	private List<EffectInstance> effects;
	private List<UnitInstance> units;

	/**
	 * Initialised the world. It is required that this method be called after the OpenGL context
	 * has been created.
	 */
	public void init() {
		player = new Player("Player 1");
		tiles = new ArrayList<>();
		blocks = new ArrayList<>();
		effects = new ArrayList<>();
		units = new ArrayList<>();
		levels = new ArrayList<>();
	}

	public void addLevel(int index, Level level) {
		levels.add(index, level);
	}

	public void nextLevel() {
		currentLevelIndex++;
		shouldLoadNewLevel = true;
	}

	public void loadFirstLevel() {
		loadLevel(0);
	}

	private void loadLevel(int index) {
		if (index < levels.size() && index >= 0) {
			loadLevel(levels.get(index));
		}
	}

	public void levelFailed() {
		levelIsFailed = true;
	}

	/**
	 * Loads the currentLevel and sets it as the current one.
	 *
	 * @param level The currentLevel to load.
	 */
	public void loadLevel(Level level) {
		this.currentLevel = level;
		player.setPosition(new Position(level.getPlayerStartX(), level.getPlayerStartY()));

		playerMoveCount = 0;
		tiles.clear();
		blocks.clear();
		effects.clear();
		units.clear();

		for (int x = 0; x < this.currentLevel.getLocations().length; x++) {
			for (int y = 0; y < this.currentLevel.getLocations()[x].length; y++) {
				if (this.currentLevel.getLocations()[x][y] != null) {
					for (String object : this.currentLevel.getLocations()[x][y].getObjects()) {
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

	private BlockInstance blockAt(Position position) {
		for (BlockInstance block : blocks)
			if (block.getPosition().equals(position))
				return block;
		return null;
	}

	private boolean isTileWalkable(Position position) {
		for (TileInstance tile : tiles) {
			if (tile.getPosition().equals(position) && !tile.getTile().isWalkable(
					this, player, position, tile.getState()
			)) {
				return false;
			}
		}
		return true;
	}

	public boolean canBlockMove(Position position, IBlockState state, Direction direction) {
		return state.getBlock().canDoPush(this, direction, position, state);
	}

	public void moveBlock(Position position, IBlockState state, Direction direction) {
		Position destination = position.displace(direction, 1); //TODO
		BlockInstance instance = blockAt(position);
		instance.setPosition(destination);
	}

	public boolean canUnitMove(Position position, IUnitState state, Direction direction) {
		Position destination = position.displace(direction, 1); //TODO
		if (!isTileWalkable(destination))
			return false;
		BlockInstance blockAt = blockAt(destination);
		if (null == blockAt)
			return true;
		if (!state.getUnit().canPushBlocks())
			return false;
		if (!canBlockMove(blockAt.getPosition(), blockAt.getState(), direction))
			return false;
		return true;
	}

	public void moveUnit(Position position, IUnitState state, Direction direction) {
		Position destination = position.displace(direction, 1); //TODO
		BlockInstance blockAt = blockAt(destination);

		if (null != blockAt) {
			Position blockDestination = blockAt.getPosition().displace(direction, 1); //TODO
			blockAt.setPosition(blockDestination);
			blockAt.getBlock().onPush(this, player, direction, destination, blockDestination, blockAt.getState());
		}

		for (UnitInstance instance : units) {
			if (instance.getPosition().equals(position) && instance.getState().equals(state)) {
				instance.setPosition(destination);
			}
			if (instance.getPosition().equals(player.getPosition()))
				instance.getUnit().onPlayerTouch(this, player, instance.getPosition(), instance.getState());
		}
	}

	public List<Position> getPositionsOfTiles(Tile tile) {
		List<Position> positions = new ArrayList<>();
		for (TileInstance instance : tiles)
			if (instance.getTile().equals(tile))
				positions.add(instance.getPosition());
		return positions;
	}

	public List<Position> getPositionsOfBlocks(Block block) {
		List<Position> positions = new ArrayList<>();
		for (BlockInstance instance : blocks) {
			if (instance.getBlock().equals(block))
				positions.add(instance.getPosition());
		}
		return positions;
	}

	public ITileState getTileState(Position position) {
		for (TileInstance instance : tiles)
			if (instance.getPosition().equals(position))
				return instance.getState();
		return null;
	}

	public ITileState getTileState(Position position, Tile type) {
		for (TileInstance instance : tiles)
			if (instance.getPosition().equals(position) && instance.getTile().equals(type))
				return instance.getState();
		return null;
	}

	public IBlockState getBlockState(Position position) {
		for (BlockInstance instance : blocks)
			if (instance.getPosition().equals(position))
				return instance.getState();
		return null;
	}

	public IUnitState getUnitState(Position position) {
		for (UnitInstance instance : units)
			if (instance.getPosition().equals(position))
				return instance.getState();
		return null;
	}

	public IUnitState getUnitState(Position position, Unit type) {
		for (UnitInstance instance : units)
			if (instance.getPosition().equals(position) && instance.getUnit().equals(type))
				return instance.getState();
		return null;
	}

	private void playerMoveBlock(BlockInstance instance, Position newPosition, Direction dir) {
		Position oldPosition = instance.getPosition();
		instance.setPosition(newPosition);

		instance.getBlock().onPush(this, player, dir, oldPosition, newPosition, instance.getState());

		for (TileInstance tile : tiles)
			if (tile.getPosition().equals(oldPosition))
				tile.getTile().onBlockMovedOff(this, oldPosition, tile.getState());
		for (TileInstance tile : tiles)
			if (tile.getPosition().equals(newPosition))
				tile.getTile().onBlockMovedOn(this, newPosition, tile.getState());
	}

	public boolean isSpaceEmpty(Position position) {
		return isTileWalkable(position) && null == blockAt(position);
	}

	@Override
	public int getRenderedWidth() {
		return Graphical.TILE_SIDE_LENGTH * currentLevel.getTileCountHorizontal();
	}

	@Override
	public int getRenderedHeight() {
		return Graphical.TILE_SIDE_LENGTH * currentLevel.getTileCountVertical();
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
			effect.drawCentered(g, (int) (effect.getPosition().getX() * 32 + gx + 16), (int) (effect.getPosition().getY() * 32 + gy + 16));
		}

		player.draw(g, (int) (player.getPosition().getX() * 32 + gx), (int) (player.getPosition().getY() * 32 + gy));

		g.drawString(String.format("Moves: %d", playerMoveCount), 0, 0);
	}

	private void movePlayer(Position position, Direction dir) {
		player.setPosition(position);
		playerMoveCount++;

		//Check for units touching the player
		for (UnitInstance instance : units) {
			if (instance.getPosition().equals(position))
				instance.getUnit().onPlayerTouch(this, player, position, instance.getState());
		}

		//Alert all units that the player has moved
		for (UnitInstance instance : units) {
			instance.getUnit().onPlayerMove(this, player, dir, 1, instance.getPosition(), instance.getState());
		}
	}

	public void destroyTile(Position position, Tile type) {
		tiles.removeIf(instance -> instance.getTile().equals(type) && instance.getPosition().equals(position));
	}

	public void destroyBlock(Position position) {
		blocks.removeIf(instance -> instance.getPosition().equals(position));
	}

	private void handlePlayerMovement(Direction dir) {
		Position moveTo = player.getPosition().displace(dir, Graphical.PLAYER_MOVEMENT_UNITS);

		//Check if we can actually walk there
		if (isTileWalkable(moveTo)) {
			//Check if we're going to push a block
			BlockInstance block = blockAt(moveTo);
			if (null == block) {
				movePlayer(moveTo, dir);
			} else if (block.getBlock().isPushable()) {
				Position blockMoveTo = moveTo.displace(dir, 1);
				if (canBlockMove(moveTo, block.getState(), dir)) {
					playerMoveBlock(block, blockMoveTo, dir);
					movePlayer(moveTo, dir);
				}
			}
		}
	}

	public void restartLevel() {
		loadLevel(currentLevel);
	}

	public void undo() {
		//TODO
		System.out.println("Not implemented");
	}

	public void update(Input input, long delta) {

		if (levelIsFailed) {
			levelIsFailed = false;
			restartLevel();
		}

		if (shouldLoadNewLevel) {
			loadLevel(currentLevelIndex);
			shouldLoadNewLevel = false;
			return;
		}

		Direction direction = InputUtil.getDirection(input);
		if (direction != Direction.NONE) {
			handlePlayerMovement(direction);
		}


		for (Iterator<EffectInstance> iterator = effects.iterator(); iterator.hasNext(); ) {
			EffectInstance effectInstance = iterator.next();
			effectInstance.incrementTime(delta);
			if (effectInstance.getTotalElapsedTime() >= effectInstance.getEffect().getDuration())
				iterator.remove();
		}

		for (BlockInstance blockInstance : blocks) {
			blockInstance.getBlock().onTick(this, delta, blockInstance.getPosition(), blockInstance.getState());
		}

		for (UnitInstance unitInstance : units) {
			unitInstance.getUnit().onTick(this, delta, unitInstance.getPosition(), unitInstance.getState());
		}
	}
}
