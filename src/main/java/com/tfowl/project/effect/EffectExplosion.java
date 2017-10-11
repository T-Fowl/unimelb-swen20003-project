package com.tfowl.project.effect;

/**
 * Created by Thomas on 11.10.2017.
 */
public class EffectExplosion extends Effect {

	private static final long DEFAULT_DURATION = 400;

	public EffectExplosion() {
		setName("explosion");
		setDuration(EffectExplosion.DEFAULT_DURATION);
	}
}
