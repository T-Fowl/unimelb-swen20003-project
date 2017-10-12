package com.tfowl.project.world;

import com.tfowl.project.block.IBlockState;
import com.tfowl.project.impl.BlockInstance;
import com.tfowl.project.util.Position;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class WorldState {

	private Position playerPosition;
	private Map<BlockInstance, Map.Entry<Position, IBlockState>> blockStates;

	public WorldState() {
		blockStates = new HashMap<>();
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
