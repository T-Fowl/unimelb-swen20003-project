package com.tfowl.project.init;

import com.tfowl.project.effect.Effect;
import com.tfowl.project.effect.EffectExplosion;
import com.tfowl.project.registry.ObjectRegistry;

public class Effects {

	public static final Effect EXPLOSION = new EffectExplosion();

	public static void init() {
		ObjectRegistry.register(EXPLOSION);
	}
}
