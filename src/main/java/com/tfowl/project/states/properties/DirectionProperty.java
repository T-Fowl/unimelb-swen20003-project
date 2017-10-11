package com.tfowl.project.states.properties;

import com.tfowl.project.util.Direction;

public class DirectionProperty implements IProperty<Direction> {

	private String name;

	public DirectionProperty(String name) {
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

	public static DirectionProperty create(String name) {
		return new DirectionProperty(name);
	}
}
