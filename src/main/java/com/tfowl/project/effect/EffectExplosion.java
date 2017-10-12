package com.tfowl.project.effect;

import com.tfowl.project.reference.Resources;

/**
 * An explosion effect that lasts for 400 milliseconds.
 * <p>
 * Created by Thomas on 11.10.2017.
 */
public class EffectExplosion extends Effect {

	private static final long DEFAULT_DURATION = 400; // in ms

	public EffectExplosion() {
		setName(Resources.Effects.EXPLOSION_NAME);
		setDuration(EffectExplosion.DEFAULT_DURATION);
	}
}
