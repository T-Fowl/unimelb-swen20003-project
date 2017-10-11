package com.tfowl.project.states;

import com.tfowl.project.states.properties.IProperty;

public interface IState {

	public <T> T getValue(IProperty<T> property);

	public <T> void setValue(IProperty<T> property, T value);
}
