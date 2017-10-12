package com.tfowl.project.states.properties;

/**
 * Convenience implementation of a {@link IProperty} for booleans.
 */
public class PropertyBoolean implements IProperty<Boolean> {

	private String name;

	private PropertyBoolean(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<Boolean> getValueClass() {
		return Boolean.class;
	}

	public static PropertyBoolean create(String name) {
		return new PropertyBoolean(name);
	}
}
