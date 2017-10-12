package com.tfowl.project.block;

import com.tfowl.project.init.Effects;
import com.tfowl.project.init.Tiles;
import com.tfowl.project.player.Player;
import com.tfowl.project.tile.ITileState;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.Position;
import com.tfowl.project.world.World;

/**
 * Created by Thomas on 11.10.2017.
 */
public class BlockTnt extends Block {

	public BlockTnt() {
		setName("tnt");
	}

	@Override
	public void onPush(World world, Player pushingPlayer, Direction directionPushed, Position oldPosition, Position position, IBlockState state) {
		super.onPush(world, pushingPlayer, directionPushed, oldPosition, position, state);

		ITileState crackedWallState = world.getTileState(position, Tiles.CRACKED_WALL);
		if (null != crackedWallState) {
			world.createEffectAt(Effects.EXPLOSION, position);
			world.destroyTile(position, Tiles.CRACKED_WALL);
			world.destroyBlock(position);
		}
	}

	@Override
	public boolean canDoPush(World world, Direction directionOfPush, Position position, IBlockState state) {
		Position destination = position.displace(directionOfPush, 1); //TODO

		ITileState crackedWallState = world.getTileState(destination, Tiles.CRACKED_WALL);
		if (null != crackedWallState) {
			//Not null means it is a cracked wall TODO
			return true;
		} else {
			return super.canDoPush(world, directionOfPush, position, state);
		}
	}
}
