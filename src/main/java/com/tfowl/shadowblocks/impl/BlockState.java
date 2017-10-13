package com.tfowl.shadowblocks.impl;

import com.tfowl.shadowblocks.block.Block;
import com.tfowl.shadowblocks.block.IBlockState;
import com.tfowl.shadowblocks.states.properties.IProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of a {@link IBlockState}
 */
public class BlockState implements IBlockState {

	private Block object;
	private Map<IProperty, Object> properties;

	public BlockState(Block object) {
		this(object, new HashMap<>());
	}

	private BlockState(Block object, Map<IProperty, Object> properties) {
		this.object = object;
		this.properties = properties;
	}

	public Block getBlock() {
		return object;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getValue(IProperty<T> property) {
		return (T) properties.get(property);
	}

	@Override
	public <T> void setValue(IProperty<T> property, T value) {
		properties.put(property, value);
	}

	@Override
	public IBlockState deepCopy() {
		Map<IProperty, Object> copyProperties = new HashMap<>(properties.size());
		copyProperties.putAll(properties);
		return new BlockState(object, copyProperties);
	}
}
