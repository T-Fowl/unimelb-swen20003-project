package com.tfowl.project.states.properties;

public class LongProperty implements IProperty<Long> {

	private String name;

	public LongProperty(String name) {
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

	public static LongProperty create(String name) {
		return new LongProperty(name);
	}
}
