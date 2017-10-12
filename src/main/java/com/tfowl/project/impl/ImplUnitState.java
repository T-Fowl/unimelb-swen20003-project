package com.tfowl.project.impl;

import com.tfowl.project.states.properties.IProperty;
import com.tfowl.project.unit.IUnitState;
import com.tfowl.project.unit.Unit;

import java.util.HashMap;
import java.util.Map;

public class ImplUnitState implements IUnitState {

	private Unit object;
	private Map<IProperty, Object> properties;

	public ImplUnitState(Unit object) {
		this(object, new HashMap<>());
	}

	private ImplUnitState(Unit object, Map<IProperty, Object> properties) {
		this.object = object;
		this.properties = properties;
	}

	public Unit getUnit() {
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
	public IUnitState deepCopy() {
		Map<IProperty, Object> copyProperties = new HashMap<>(properties.size());
		copyProperties.putAll(properties);
		return new ImplUnitState(object, copyProperties);
	}
}
