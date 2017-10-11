package com.tfowl.project.states.properties;

public class BooleanProperty implements IProperty<Boolean> {

	private String name;

	public BooleanProperty(String name) {
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

	public static BooleanProperty create(String name) {
		return new BooleanProperty(name);
	}
}
