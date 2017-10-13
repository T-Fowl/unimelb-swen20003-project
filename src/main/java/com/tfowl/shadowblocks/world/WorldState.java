package com.tfowl.shadowblocks.world;

import com.tfowl.shadowblocks.block.IBlockState;
import com.tfowl.shadowblocks.world.internal.BlockInstance;
import com.tfowl.shadowblocks.util.Position;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class WorldState {

	private Position playerPosition;
	private int playerMoveCount;
	private Map<BlockInstance, Map.Entry<Position, IBlockState>> blockStates;

	WorldState() {
		blockStates = new HashMap<>();
	}

	public int getPlayerMoveCount() {
		return playerMoveCount;
	}

	public void setPlayerMoveCount(int playerMoveCount) {
		this.playerMoveCount = playerMoveCount;
	}

	public Position getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(Position playerPosition) {
		this.playerPosition = playerPosition;
	}

	public Map<BlockInstance, Map.Entry<Position, IBlockState>> getBlockStates() {
		return blockStates;
	}

	public void withBlockState(BlockInstance instance, Position position, IBlockState state) {
		blockStates.put(instance, new AbstractMap.SimpleEntry<>(position, state));
	}

	public void restoreState(BlockInstance instance) {
		if (blockStates.containsKey(instance)) {
			Map.Entry<Position, IBlockState> pointInTime = blockStates.get(instance);
			instance.setPosition(pointInTime.getKey());
			instance.setState(pointInTime.getValue());
		}
	}
}
