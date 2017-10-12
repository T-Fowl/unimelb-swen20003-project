package com.tfowl.shadowblocks.init;

import com.tfowl.shadowblocks.effect.Effect;
import com.tfowl.shadowblocks.effect.EffectExplosion;
import com.tfowl.shadowblocks.registry.ObjectRegistry;

/**
 * Creates and registers all of the default Effects.
 */
public class Effects {

	public static final Effect EXPLOSION = new EffectExplosion();

	/**
	 * Register all of the default effects
	 */
	public static void init() {
		ObjectRegistry.register(EXPLOSION);
	}
}
