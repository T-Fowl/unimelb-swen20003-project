package com.tfowl.project.unit;

import com.tfowl.project.registry.ObjectRegistry;

public class Units {

	public static final Unit MAGE = new UnitMage();
	public static final Unit PLAYER = new UnitPlayer();
	public static final Unit ROGUE = new UnitRogue();
	public static final Unit SKELETON = new UnitSkeleton();

	public static void init() {
		ObjectRegistry.register(MAGE);
		ObjectRegistry.register(PLAYER);
		ObjectRegistry.register(ROGUE);
		ObjectRegistry.register(SKELETON);
	}
}
