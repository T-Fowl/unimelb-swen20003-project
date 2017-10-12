package com.tfowl.project.init;

import com.tfowl.project.effect.Effect;
import com.tfowl.project.effect.EffectExplosion;
import com.tfowl.project.registry.ObjectRegistry;

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
