package com.tfowl.project.states.properties;

public class PropertyLong implements IProperty<Long> {

	private String name;

	private PropertyLong(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<Long> getValueClass() {
		return Long.class;
	}

	public static PropertyLong create(String name) {
		return new PropertyLong(name);
	}
}
