package com.tfowl.project.states.properties;

import com.tfowl.project.util.Direction;

public class PropertyDirection implements IProperty<Direction> {

	private String name;

	private PropertyDirection(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<Direction> getValueClass() {
		return Direction.class;
	}

	public static PropertyDirection create(String name) {
		return new PropertyDirection(name);
	}
}
