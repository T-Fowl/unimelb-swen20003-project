package com.tfowl.shadowblocks.effect;

/**
 * Represents an effect in the world with a given duration.
 * <p>
 * Created by Thomas on 11.10.2017.
 */
public class Effect {

	private String name;
	private long duration; //in ms

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getDuration() {
		return duration;
	}
}
