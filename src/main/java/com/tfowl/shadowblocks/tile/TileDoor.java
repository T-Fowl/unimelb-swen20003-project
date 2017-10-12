package com.tfowl.shadowblocks.tile;

import com.tfowl.shadowblocks.reference.Resources;
import com.tfowl.shadowblocks.states.properties.PropertyBoolean;
import com.tfowl.shadowblocks.util.Position;
import com.tfowl.shadowblocks.world.World;

/**
 * Represents a door tile. This tile is walkable when unlocked and not when locked.
 * <p>
 * Created by Thomas on 11.10.2017.
 */
public class TileDoor extends Tile {

	/**
	 * Property of the open status of this door.
	 */
	public static final PropertyBoolean OPEN_PROPERTY = PropertyBoolean.create("open");

	public TileDoor() {
		setName(Resources.Tiles.DOOR_NAME);
		setWalkable(false);
	}

	@Override
	public ITileState getDefaultState() {
		ITileState state = super.getDefaultState();
		/* Default to closed */
		state.setValue(OPEN_PROPERTY, false);
		return state;
	}

	@Override
	public boolean isTileWalkable(World world, Position position, ITileState state) {
		/* Door is walkable when it is unlocked */
		return state.getValue(OPEN_PROPERTY);
	}

	@Override
	public boolean shouldRenderTile(World world, Position position, ITileState state) {
		/* Door should not be rendered when it is unlocked */
		return !state.getValue(OPEN_PROPERTY);
	}
}
