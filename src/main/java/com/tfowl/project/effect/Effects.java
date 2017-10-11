package com.tfowl.project.effect;

import com.tfowl.project.registry.ObjectRegistry;

public class Effects {

	public static final Effect EXPLOSION = new EffectExplosion();

	public static void init() {
		ObjectRegistry.register(EXPLOSION);
	}
}
