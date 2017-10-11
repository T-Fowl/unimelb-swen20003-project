package com.tfowl.project.impl;

import com.tfowl.project.states.IState;
import com.tfowl.project.states.properties.IProperty;

import java.util.HashMap;
import java.util.Map;

public class ImplState<O> implements IState {

	private O block;
	private Map<IProperty, Object> properties;

	public ImplState(O block) {
		this.block = block;
		this.properties = new HashMap<>();
	}

	public <T> ImplState withProperty(IProperty<T> property, T defaultValue) {
		properties.put(property, defaultValue);
		return this;
	}

	public O getObject() {
		return block;
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
}
