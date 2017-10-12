package com.tfowl.project.impl;

import com.tfowl.project.states.properties.IProperty;
import com.tfowl.project.tile.ITileState;
import com.tfowl.project.tile.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of an {@link ITileState}
 */
public class ImplTileState implements ITileState {

	private Tile object;
	private Map<IProperty, Object> properties;

	public ImplTileState(Tile object) {
		this(object, new HashMap<>());
	}

	private ImplTileState(Tile object, Map<IProperty, Object> properties) {
		this.object = object;
		this.properties = properties;
	}

	public Tile getTile() {
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
	public ITileState deepCopy() {
		Map<IProperty, Object> copyProperties = new HashMap<>(properties.size());
		copyProperties.putAll(properties);
		return new ImplTileState(object, copyProperties);
	}
}
