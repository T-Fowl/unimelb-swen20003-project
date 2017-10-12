package com.tfowl.project.states;

import com.tfowl.project.states.properties.IProperty;

public interface IState<S extends IState<S>> {

	public <T> T getValue(IProperty<T> property);

	public <T> void setValue(IProperty<T> property, T value);

	public IState<S> deepCopy();
}
