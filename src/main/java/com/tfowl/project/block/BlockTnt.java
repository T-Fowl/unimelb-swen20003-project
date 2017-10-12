package com.tfowl.project.block;

import com.tfowl.project.init.Effects;
import com.tfowl.project.init.Tiles;
import com.tfowl.project.reference.Resources;
import com.tfowl.project.tile.ITileState;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Represents a block of TNT that can explode on contact with a cracked wall
 * <p>
 * Created by Thomas on 11.10.2017.
 */
public class BlockTnt extends Block {

	public BlockTnt() {
		setName(Resources.Blocks.TNT_NAME);
	}

	@Override
	public void onPush(World world, Direction directionPushed, Position oldPosition, Position position, IBlockState state) {
		super.onPush(world, directionPushed, oldPosition, position, state);

		/* Check to see if the tile we're being pushed into is a cracked tile.
		 * If so, create an explosion while also telling the world to destroy both the tnt and wall */
		ITileState crackedWallState = world.getTileState(position, Tiles.CRACKED_WALL);
		if (null != crackedWallState) {
			world.createEffectAt(Effects.EXPLOSION, position);
			world.destroyTileAt(position, Tiles.CRACKED_WALL);
			world.destroyBlockAt(position);
		}
	}

	@Override
	public boolean canBePushed(World world, Position position, IBlockState state, Direction directionOfPush) {
		Position destination = position.displace(directionOfPush);

		ITileState crackedWallState = world.getTileState(destination, Tiles.CRACKED_WALL);
		if (null != crackedWallState) {
			/* If the state is not null, that means that the world found a cracked wall at the given position
			 * and we should be able to be pushed into it */
			return true;
		} else {
			/* Otherwise just do the default */
			return super.canBePushed(world, position, state, directionOfPush);
		}
	}
}
