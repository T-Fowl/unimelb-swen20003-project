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
import java.util.Stack;

/**
 * World class
 * Created by Thomas on 6/09/2017.
 */
public class World implements IRenderable {

	private static final int UPDATE_FLAG_NEW_LEVEL = 0x01;
	private static final int UPDATE_FLAG_LEVEL_FAILED = 0x02;
	private static final int UPDATE_FLAG_UNDO = 0x04;

	private static final Logger logger = LoggerFactory.getLogger(World.class);

	private WorldLevelProvider levelProvider;

	private Player player;

	private List<TileInstance> tiles;
	private List<BlockInstance> blocks;
	private List<EffectInstance> effects;
	private List<UnitInstance> units;

	private int playerMoveCount = 0;
	private Stack<WorldState> history;

	private int updateFlags = 0;

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
		levelProvider = new WorldLevelProvider();
		history = new Stack<>();
	}

	public WorldLevelProvider getLevelProvider() {
		return levelProvider;
	}

	public void nextLevel() {
		updateFlags |= UPDATE_FLAG_NEW_LEVEL;
	}

	public void levelFailed() {
		updateFlags |= UPDATE_FLAG_LEVEL_FAILED;
	}

	public void undo() {
		updateFlags |= UPDATE_FLAG_UNDO;
	}

	public void restartLevel() {
		levelFailed(); //Same thing
	}

	public void loadFirstLevel() {
		//TODO
		loadLevel(levelProvider.nextLevel());
	}

	private void restoreState(WorldState state) {
		player.setPosition(state.getPlayerPosition());

		for (BlockInstance instance : blocks) {
			state.restoreState(instance);
		}
	}

	private WorldState captureCurrentState() {
		WorldState state = new WorldState();
		state.setPlayerPosition(player.getPosition());

		for (BlockInstance instance : blocks) {
			state.withBlockState(instance, instance.getPosition(), (IBlockState) instance.getState().deepCopy());
		}

		return state;
	}

	/**
	 * Loads the currentLevel and sets it as the current one.
	 *
	 * @param level The currentLevel to load.
	 */
	private void loadLevel(Level level) {
		player.setPosition(new Position(level.getPlayerStartX(), level.getPlayerStartY()));

		playerMoveCount = 0;
		tiles.clear();
		blocks.clear();
		effects.clear();
		units.clear();

		for (int x = 0; x < level.getLocations().length; x++) {
			for (int y = 0; y < level.getLocations()[x].length; y++) {
				if (level.getLocations()[x][y] != null) {
					for (String object : level.getLocations()[x][y].getObjects()) {
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
						} else if (registered instanceof Effect) {
							EffectInstance instance = new EffectInstance(ObjectRegistry.getEffect(object), Position.at(x, y));
							effects.add(instance);
						} else {
							logger.warn("Level referenced unregistered object: " + object);
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
			if (tile.getPosition().equals(position) && !tile.getTile().isTileWalkable(
					this, position, tile.getState()
			)) {
				return false;
			}
		}
		return true;
	}

	public boolean canBlockMove(Position position, IBlockState state, Direction direction) {
		return state.getBlock().canDoPush(this, direction, position, state);
	}

	public void moveBlock(Position position, Direction direction) {
		Position destination = position.displace(direction);
		BlockInstance instance = blockAt(position);
		if (instance != null) {
			instance.setPosition(destination);

			for (TileInstance tile : tiles) {
				if (tile.getPosition().equals(position))
					tile.getTile().onBlockMovedOff(this, tile.getPosition(), tile.getState(), instance.getPosition(), instance.getState());
			}
			for (TileInstance tile : tiles) {
				if (tile.getPosition().equals(destination))
					tile.getTile().onBlockMovedOver(this, tile.getPosition(), tile.getState(), position, instance.getState());
			}
		}
	}

	public boolean canUnitMove(Position position, IUnitState state, Direction direction) {
		Position destination = position.displace(direction);
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
		Position destination = position.displace(direction);
		BlockInstance blockAt = blockAt(destination);

		if (null != blockAt) {
			Position blockDestination = blockAt.getPosition().displace(direction);
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
				tile.getTile().onBlockMovedOff(this, tile.getPosition(), tile.getState(), instance.getPosition(), instance.getState());
		for (TileInstance tile : tiles)
			if (tile.getPosition().equals(newPosition))
				tile.getTile().onBlockMovedOver(this, tile.getPosition(), tile.getState(), oldPosition, instance.getState());
	}

	public boolean isSpaceEmpty(Position position) {
		return isTileWalkable(position) && null == blockAt(position);
	}

	@Override
	public int getRenderedWidth() {
		return Graphical.TILE_SIDE_LENGTH * levelProvider.currentLevel().getTileCountHorizontal();
	}

	@Override
	public int getRenderedHeight() {
		return Graphical.TILE_SIDE_LENGTH * levelProvider.currentLevel().getTileCountVertical();
	}

	@Override
	public void draw(Graphics g, int gx, int gy) throws SlickException {
		for (TileInstance tile : tiles) {
			if (tile.getTile().shouldRenderTile(this, tile.getPosition(), tile.getState()))
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
				Position blockMoveTo = moveTo.displace(dir);
				if (canBlockMove(moveTo, block.getState(), dir)) {
					playerMoveBlock(block, blockMoveTo, dir);
					movePlayer(moveTo, dir);
				}
			}
		}
	}

	public void update(Input input, long delta) {

		if ((updateFlags & UPDATE_FLAG_LEVEL_FAILED) != 0) {
			loadLevel(levelProvider.currentLevel());
			updateFlags &= ~UPDATE_FLAG_LEVEL_FAILED;
			return;
		}

		if ((updateFlags & UPDATE_FLAG_NEW_LEVEL) != 0) {
			loadLevel(levelProvider.nextLevel());
			updateFlags &= ~UPDATE_FLAG_NEW_LEVEL;
			return;
		}

		if ((updateFlags & UPDATE_FLAG_UNDO) != 0) {
			updateFlags &= ~UPDATE_FLAG_UNDO;
			if (history.size() > 0) {
				WorldState state = history.pop();
				restoreState(state);
				playerMoveCount--;
				return;
			}
		}

		Direction direction = InputUtil.getDirection(input);
		if (direction != Direction.NONE) {
			history.push(captureCurrentState());
			handlePlayerMovement(direction);
			playerMoveCount++;
		}


		for (Iterator<EffectInstance> iterator = effects.iterator(); iterator.hasNext(); ) {
			EffectInstance effectInstance = iterator.next();
			effectInstance.incrementTime(delta);
			if (effectInstance.getTotalElapsedTime() >= effectInstance.getEffect().getDuration())
				iterator.remove();
		}

		for (BlockInstance blockInstance : blocks) {
			blockInstance.getBlock().onWorldTick(this, delta, blockInstance.getPosition(), blockInstance.getState());
		}

		for (UnitInstance unitInstance : units) {
			unitInstance.getUnit().onWorldTick(this, delta, unitInstance.getPosition(), unitInstance.getState());
		}
	}
}
