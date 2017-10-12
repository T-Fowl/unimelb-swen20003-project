package com.tfowl.project.impl;

import com.tfowl.project.states.IState;
import com.tfowl.project.states.properties.IProperty;

import java.util.HashMap;
import java.util.Map;

public class ImplState<O, S extends IState<S>> implements IState<S> {

	private O object;
	private Map<IProperty, Object> properties;

	public ImplState(O object) {
		this(object, new HashMap<>());
	}

	private ImplState(O object, Map<IProperty, Object> properties) {
		this.object = object;
		this.properties = properties;
	}

	public <T> ImplState withProperty(IProperty<T> property, T defaultValue) {
		properties.put(property, defaultValue);
		return this;
	}

	public O getObject() {
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
	public IState<S> deepCopy() {
		Map<IProperty, Object> copyProperties = new HashMap<>(properties.size());
		copyProperties.putAll(properties);
		return new ImplState<O, S>(object, copyProperties);
	}
}
