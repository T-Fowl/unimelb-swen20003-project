package com.tfowl.project.impl;

import com.tfowl.project.block.Block;
import com.tfowl.project.block.IBlockState;
import com.tfowl.project.states.properties.IProperty;

import java.util.HashMap;
import java.util.Map;

public class ImplBlockState implements IBlockState {

	private Block object;
	private Map<IProperty, Object> properties;

	public ImplBlockState(Block object) {
		this(object, new HashMap<>());
	}

	private ImplBlockState(Block object, Map<IProperty, Object> properties) {
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
		return new ImplBlockState(object, copyProperties);
	}
}
