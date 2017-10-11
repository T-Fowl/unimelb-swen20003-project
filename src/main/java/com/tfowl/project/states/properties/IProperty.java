package com.tfowl.project.states.properties;

public interface IProperty<T> {

	public String getName();

	public Class<T> getValueClass();
}
