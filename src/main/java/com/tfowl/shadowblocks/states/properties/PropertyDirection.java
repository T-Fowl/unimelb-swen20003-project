package com.tfowl.shadowblocks.states.properties;

import com.tfowl.shadowblocks.util.Direction;

/**
 * Convenience implementation of a {@link IProperty} for {@link Direction}s.
 */
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
