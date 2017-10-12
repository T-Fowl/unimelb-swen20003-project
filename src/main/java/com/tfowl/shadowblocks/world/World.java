package com.tfowl.shadowblocks.world;

import com.tfowl.shadowblocks.block.Block;
import com.tfowl.shadowblocks.block.IBlockState;
import com.tfowl.shadowblocks.effect.Effect;
import com.tfowl.shadowblocks.graphics.IRenderable;
import com.tfowl.shadowblocks.impl.BlockInstance;
import com.tfowl.shadowblocks.impl.EffectInstance;
import com.tfowl.shadowblocks.impl.TileInstance;
import com.tfowl.shadowblocks.impl.UnitInstance;
import com.tfowl.shadowblocks.level.Level;
import com.tfowl.shadowblocks.logging.Logger;
import com.tfowl.shadowblocks.logging.LoggerFactory;
import com.tfowl.shadowblocks.player.Player;
import com.tfowl.shadowblocks.reference.Graphical;
import com.tfowl.shadowblocks.reference.Strings;
import com.tfowl.shadowblocks.registry.ObjectRegistry;
import com.tfowl.shadowblocks.tile.ITileState;
import com.tfowl.shadowblocks.tile.Tile;
import com.tfowl.shadowblocks.unit.IUnitState;
import com.tfowl.shadowblocks.unit.Unit;
import com.tfowl.shadowblocks.util.Direction;
import com.tfowl.shadowblocks.util.InputUtil;
import com.tfowl.shadowblocks.util.Position;
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

	private static final Logger logger = LoggerFactory.getLogger(World.class);

	/* Flags used for performing certain tasks at the next update cycle */
	private static final int UPDATE_FLAG_NEW_LEVEL = 0x01;
	private static final int UPDATE_FLAG_LEVEL_FAILED = 0x02;
	private static final int UPDATE_FLAG_UNDO = 0x04;

	/* Flags for the next update */
	private int updateFlags = 0;

	/* Provides all of the loaded levels */
	private WorldLevelProvider levelProvider;

	/* Controlled unit */
	private Player player;

	/* The geometry of the world */
	private List<TileInstance> tiles;
	private List<BlockInstance> blocks;
	private List<EffectInstance> effects;
	private List<UnitInstance> units;

	/* Current move count and past history */
	private int playerMoveCount = 0;
	private Stack<WorldState> history;


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

	/**
	 * Get the level provider of this world.
	 *
	 * @return The level provider of this world.
	 */
	public WorldLevelProvider getLevelProvider() {
		return levelProvider;
	}

	/**
	 * Specify that the world should move onto the next level.
	 */
	public void nextLevel() {
		updateFlags |= UPDATE_FLAG_NEW_LEVEL;
	}

	/**
	 * Specify that the level has been failed and should be restarted
	 */
	public void levelFailed() {
		updateFlags |= UPDATE_FLAG_LEVEL_FAILED;
	}

	/**
	 * Specify that an undo should be performed
	 */
	public void undo() {
		updateFlags |= UPDATE_FLAG_UNDO;
	}

	/**
	 * Specify that the current level should be restarted
	 */
	public void restartLevel() {
		levelFailed(); //Same thing
	}

	/**
	 * Load the first registered level
	 */
	public void loadFirstLevel() {
		//TODO
		loadLevel(levelProvider.nextLevel());
	}

	/**
	 * Restores the state captures in the given state object.
	 * This will not restore blocks that have been destroyed.
	 *
	 * @param state State in time to restore.
	 */
	private void restoreState(WorldState state) {
		player.setPosition(state.getPlayerPosition());
		playerMoveCount = state.getPlayerMoveCount();

		for (BlockInstance instance : blocks) {
			state.restoreState(instance);
		}
	}

	/**
	 * Captures all of the current block states and positions along with the player position and
	 * move count.
	 *
	 * @return A representation of this point in time.
	 */
	private WorldState captureCurrentState() {
		WorldState state = new WorldState();
		state.setPlayerPosition(player.getPosition());
		state.setPlayerMoveCount(playerMoveCount);

		for (BlockInstance instance : blocks) {
			state.withBlockState(instance, instance.getPosition(), (IBlockState) instance.getState().deepCopy());
		}

		return state;
	}

	/**
	 * Loads all of the data from the passed level
	 *
	 * @param level The level to load.
	 */
	private void loadLevel(Level level) {
		player.setPosition(level.getPlayerStartPosition());
		playerMoveCount = 0;

		tiles.clear();
		blocks.clear();
		effects.clear();
		units.clear();
		history.clear();

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

	/* Creating and Destroying Objects */

	/**
	 * Create an effect at the given position.
	 *
	 * @param effect   Type of effect to create.
	 * @param position Position of the effect.
	 */
	public void createEffectAt(Effect effect, Position position) {
		EffectInstance instance = new EffectInstance(effect, position);
		effects.add(instance);
	}

	/**
	 * Destroys any effects at the given position.
	 *
	 * @param position Position to destroy effects at.
	 * @param effect   Effect type to destroy. As there could be multiple.
	 */
	public void destroyEffectAt(Position position, Effect effect) {
		effects.removeIf(instance -> instance.getPosition().equals(position) && instance.getEffect().equals(effect));
	}

	/**
	 * Creates a unit at the given position.
	 *
	 * @param unit     Type of unit to create.
	 * @param position Position to create the unit at.
	 */
	public void createUnitAt(Unit unit, Position position) {
		UnitInstance instance = new UnitInstance(unit);
		instance.setPosition(position);
		units.add(instance);
	}

	/**
	 * Destoyes all units at the given position.
	 *
	 * @param position Position to destroy at.
	 * @param type     Types of units to destroy. As there could be multiple.
	 */
	public void destroyUnitAt(Position position, Unit type) {
		units.removeIf(instance -> instance.getUnit().equals(type) && instance.getPosition().equals(position));
	}

	/**
	 * Creates a tile at the given position.
	 *
	 * @param tile     Type of tile to create.
	 * @param position Position to create the tile at.
	 */
	public void createTileAt(Tile tile, Position position) {
		TileInstance instance = new TileInstance(tile);
		instance.setPosition(position);
		tiles.add(instance);
	}

	/**
	 * Destroyes all tiles at the given position.
	 *
	 * @param position Position to destroy the tiles at.
	 * @param type     Type of tile to destroy. As there could be multiple.
	 */
	public void destroyTileAt(Position position, Tile type) {
		tiles.removeIf(instance -> instance.getTile().equals(type) && instance.getPosition().equals(position));
	}

	/**
	 * Creates a block at the given position.
	 *
	 * @param block    Type of block to create.
	 * @param position Position to create at.
	 */
	public void createBlockAt(Block block, Position position) {
		if (blockInstanceAt(position) == null) {
			BlockInstance instance = new BlockInstance(block);
			instance.setPosition(position);
			blocks.add(instance);
		}
	}

	/**
	 * Destoyes all blocks at the given position.
	 *
	 * @param position Position of the blocks to destroy.
	 */
	public void destroyBlockAt(Position position) {
		blocks.removeIf(instance -> instance.getPosition().equals(position));
	}


	/* Internal Looking up Objects */

	/**
	 * Internal method. Gets the {@link BlockInstance} at the given position.
	 *
	 * @param position Position of the {@link Block} represented by this {@link BlockInstance}
	 * @return The {@link BlockInstance} at this position.
	 */
	private BlockInstance blockInstanceAt(Position position) {
		for (BlockInstance block : blocks)
			if (block.getPosition().equals(position))
				return block;
		return null;
	}

	/**
	 * Internal method. Gets the {@link TileInstance} at the given position.
	 *
	 * @param position Position of the {@link Tile} represented by this {@link TileInstance}
	 * @param type     Type of {@link Tile} to look for, as there could be multiple.l
	 * @return The {@link TileInstance} at this position. First one of the type that matches.
	 */
	private TileInstance tileInstanceAt(Position position, Tile type) {
		for (TileInstance tile : tiles) {
			if (tile.getTile().equals(type))
				return tile;
		}
		return null;
	}

	/**
	 * Tests if the {@link Tile} at the given position is walkable.
	 * Note that this method only checks that the {@link Tile}s themselves are walkable, not if any
	 * blocks are on that tile.
	 *
	 * @param position Position to check.
	 * @return true if all of the {@link Tile}s at the given position are walkable.
	 */
	public boolean isTileWalkable(Position position) {
		for (TileInstance instance : tiles) {
			if (instance.getPosition().equals(position) && !instance.getTile().isTileWalkable(
					this, position, instance.getState())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the {@link Block} at the given position can be moved in the given direction.
	 *
	 * @param position  Position of the {@link Block}.
	 * @param state     State of the {@link Block}.
	 * @param direction Direction to move in.
	 * @return true if the block can be moved in that direction. false otherwise.
	 */
	public boolean canBlockMove(Position position, IBlockState state, Direction direction) {
		return state.getBlock().canBePushed(this, position, state, direction);
	}

	/**
	 * Moves the {@link Block} at the given position in the given direction. Alerting all {@link Tile}s that the
	 * {@link Block} has either landed on them or moved off them. This method does no checks. Make sure to check with
	 * {@link World#canBlockMove(Position, IBlockState, Direction)} first.
	 *
	 * @param position  Position of the {@link Block}
	 * @param direction Direction to move in.
	 * @see World#canBlockMove(Position, IBlockState, Direction)
	 */
	public void moveBlock(Position position, Direction direction) {
		Position destination = position.displace(direction);
		BlockInstance instance = blockInstanceAt(position);
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

	/**
	 * Checks if the {@link Unit} at the given position can move in the given direction.
	 *
	 * @param position  Position of the {@link Unit} to check.
	 * @param state     State of the {@link Unit}
	 * @param direction Direction to check for valid movement in.
	 * @return true if the unit can perform the move. false otherwise.
	 */
	public boolean canUnitMove(Position position, IUnitState state, Direction direction) {
		Position destination = position.displace(direction);
		if (!isTileWalkable(destination))
			return false;
		BlockInstance blockAt = blockInstanceAt(destination);
		if (null == blockAt)
			return true;
		if (!state.getUnit().canPushBlocks())
			return false;
		if (!canBlockMove(blockAt.getPosition(), blockAt.getState(), direction))
			return false;
		return true;
	}

	/**
	 * Moves the {@link Unit} at the given position in the given direction. Pushing any {@link Block}s in its way.
	 * This method does no checks, make sure to check {@link World#canUnitMove(Position, IUnitState, Direction)} first.
	 *
	 * @param position  Position of the {@link Unit} to move.
	 * @param state     State of the {@link Unit}.
	 * @param direction Direction to move in
	 * @see World#canUnitMove(Position, IUnitState, Direction)
	 */
	public void moveUnit(Position position, IUnitState state, Direction direction) {
		Position destination = position.displace(direction);
		BlockInstance blockAt = blockInstanceAt(destination);

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

	/**
	 * Gets a list of all positions in which a {@link Tile} of the given type is placed.
	 *
	 * @param tile Type of {@link Tile} to look for.
	 * @return A list of all positions with the specific Tile.
	 */
	public List<Position> getPositionsOfTiles(Tile tile) {
		List<Position> positions = new ArrayList<>();
		for (TileInstance instance : tiles)
			if (instance.getTile().equals(tile))
				positions.add(instance.getPosition());
		return positions;
	}

	/**
	 * Gets a list of all positions in which a {@link Block} of the given type is placed.
	 *
	 * @param block Type of {@link Block} to look for.
	 * @return A list of all positions with the specific Block.
	 */
	public List<Position> getPositionsOfBlocks(Block block) {
		List<Position> positions = new ArrayList<>();
		for (BlockInstance instance : blocks) {
			if (instance.getBlock().equals(block))
				positions.add(instance.getPosition());
		}
		return positions;
	}

	/**
	 * Get the state of a {@link Tile} at the given position.
	 *
	 * @param position Position to look for {@link Tile}s.
	 * @return The first {@link ITileState} coupled with a {@link Tile} at the given position. Or null if none are found.
	 */
	public ITileState getTileState(Position position) {
		for (TileInstance instance : tiles)
			if (instance.getPosition().equals(position))
				return instance.getState();
		return null;
	}

	/**
	 * Get the state of a {@link Tile} at the given position.
	 *
	 * @param position Position to look for {@link Tile}s.
	 * @param type     Type of {@link Tile}. In case there are multiple at the same location.
	 * @return The first {@link ITileState} coupled with a {@link Tile} at the given position. Or null if none are found.
	 */
	public ITileState getTileState(Position position, Tile type) {
		for (TileInstance instance : tiles)
			if (instance.getPosition().equals(position) && instance.getTile().equals(type))
				return instance.getState();
		return null;
	}

	/**
	 * Get the state of a {@link Block} at the given position.
	 *
	 * @param position Position to look for {@link Block}s.
	 * @return The first {@link IBlockState} coupled with a {@link Block} at the given position. Or null if none are found.
	 */
	public IBlockState getBlockState(Position position) {
		for (BlockInstance instance : blocks)
			if (instance.getPosition().equals(position))
				return instance.getState();
		return null;
	}

	/**
	 * Get the state of a {@link Unit} at the given position.
	 *
	 * @param position Position to look for {@link Unit}s.
	 * @return The first {@link IUnitState} coupled with a {@link Unit} at the given position. Or null if none are found.
	 */
	public IUnitState getUnitState(Position position) {
		for (UnitInstance instance : units)
			if (instance.getPosition().equals(position))
				return instance.getState();
		return null;
	}

	/**
	 * Get the state of a {@link Unit} at the given position with the given type.
	 *
	 * @param position Position to look for {@link Unit}s.
	 * @param type     Type of {@link Unit}. In case there are multiple at the same location.
	 * @return The first {@link IUnitState} coupled with a {@link Unit} at the given position. Or null if none are found.
	 */
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

	/**
	 * Determines if the given position is a walkable {@link Tile} and has no {@link Block}s in it.
	 *
	 * @param position Position to test for.
	 * @return true if the tile is walkable and there is no {@link Block} there. false otherwise.
	 */
	public boolean isPositionWalkableAndBlockEmpty(Position position) {
		return isTileWalkable(position) && null == blockInstanceAt(position);
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
	public void draw(Graphics g, float gx, float gy) throws SlickException {

		/* Draw all Tiles at their place in the world */
		for (TileInstance tile : tiles) {
			if (tile.getTile().shouldRenderTile(this, tile.getPosition(), tile.getState()))
				tile.draw(g,
						tile.getPosition().getX() * Graphical.TILE_SIDE_LENGTH + gx,
						tile.getPosition().getY() * Graphical.TILE_SIDE_LENGTH + gy);
		}

		/* Draw all Blocks at their place in the world, on top of Tiles. */
		for (BlockInstance block : blocks) {
			block.draw(g,
					block.getPosition().getX() * Graphical.TILE_SIDE_LENGTH + gx,
					block.getPosition().getY() * Graphical.TILE_SIDE_LENGTH + gy);
		}

		/* Draw all Units at their place in the world, on top of Tiles and Blocks */
		for (UnitInstance unit : units) {
			unit.draw(g,
					unit.getPosition().getX() * Graphical.TILE_SIDE_LENGTH + gx,
					unit.getPosition().getY() * Graphical.TILE_SIDE_LENGTH + gy);
		}

		/* Draw all Effects at their place in the world, on top of everything else */
		for (EffectInstance effect : effects) {
			effect.drawCentered(g,
					/* Effects should be centered at the center of a Tile */
					effect.getPosition().getX() * Graphical.TILE_SIDE_LENGTH + gx + Graphical.TILE_SIDE_LENGTH / 2.f,
					effect.getPosition().getY() * Graphical.TILE_SIDE_LENGTH + gy + Graphical.TILE_SIDE_LENGTH / 2.f);
		}

		/* Finally, draw the player */
		player.draw(g,
				player.getPosition().getX() * Graphical.TILE_SIDE_LENGTH + gx,
				player.getPosition().getY() * Graphical.TILE_SIDE_LENGTH + gy);

		g.drawString(String.format(Strings.DISPLAY_MOVES_STRING, playerMoveCount),
				Graphical.DISPLAY_MOVES_X, Graphical.DISPLAY_MOVES_Y);
	}

	/**
	 * Performs and broadcasts the player movement. Also checks for collisions with other {@link Unit}s and
	 * performs any on-touch actions.
	 *
	 * @param destination Destination to move to.
	 * @param direction   Direction the player moved.
	 */
	private void doMovePlayer(Position destination, Direction direction) {
		player.setPosition(destination);

		/* Check for units touching the player */
		for (UnitInstance instance : units) {
			if (instance.getPosition().equals(destination))
				instance.getUnit().onPlayerTouch(this, player, instance.getPosition(), instance.getState());
		}

		/* Alert all units that the player has moved */
		for (UnitInstance instance : units) {
			instance.getUnit().onPlayerMove(this, player, direction, 1, instance.getPosition(), instance.getState());
		}
	}

	/**
	 * Checks if it is possible for a player to make a move in the given direction, if so performs that move.
	 *
	 * @param direction Direction to move in
	 */
	private void handlePlayerMovement(Direction direction) {
		Position destination = player.getPosition().displace(direction);

		/* Check if we can actually walk there */
		if (isTileWalkable(destination)) {
			/* Check if we're going to push a block */
			BlockInstance block = blockInstanceAt(destination);
			if (null == block) {
				doMovePlayer(destination, direction);
			} else if (block.getBlock().isPushable()) {
				Position blockMoveTo = block.getPosition().displace(direction);
				if (canBlockMove(destination, block.getState(), direction)) {
					playerMoveBlock(block, blockMoveTo, direction);
					doMovePlayer(destination, direction);
				}
			}
		}


		playerMoveCount++;
	}

	public void update(Input input, long delta) {

		if ((updateFlags & UPDATE_FLAG_LEVEL_FAILED) != 0) {
			loadLevel(levelProvider.currentLevel());
			updateFlags &= ~UPDATE_FLAG_LEVEL_FAILED;
			return;
		}

		if ((updateFlags & UPDATE_FLAG_NEW_LEVEL) != 0) {
			if (levelProvider.hasNextLevel())
				loadLevel(levelProvider.nextLevel());
			updateFlags &= ~UPDATE_FLAG_NEW_LEVEL;
			return;
		}

		if ((updateFlags & UPDATE_FLAG_UNDO) != 0) {
			updateFlags &= ~UPDATE_FLAG_UNDO;
			if (history.size() > 0) {
				WorldState state = history.pop();
				restoreState(state);
				return;
			}
		}

		Direction playerMoveDirection = InputUtil.getDirection(input);
		if (playerMoveDirection != Direction.NONE) {
			/* Player is making a move, capture this point in time */
			history.push(captureCurrentState());
			/* Handle the player movement */
			handlePlayerMovement(playerMoveDirection);
		}


		/* Remove all effects that have outlasted their configured duration */
		for (Iterator<EffectInstance> iterator = effects.iterator(); iterator.hasNext(); ) {
			EffectInstance effectInstance = iterator.next();
			effectInstance.incrementTime(delta);
			if (effectInstance.getTotalElapsedTime() >= effectInstance.getEffect().getDuration())
				iterator.remove();
		}

		/* Alert all Blocks and Units that world tick has occurred */
		for (BlockInstance blockInstance : blocks) {
			blockInstance.getBlock().onWorldTick(this, delta, blockInstance.getPosition(), blockInstance.getState());
		}

		for (UnitInstance unitInstance : units) {
			unitInstance.getUnit().onWorldTick(this, delta, unitInstance.getPosition(), unitInstance.getState());
		}
	}
}
