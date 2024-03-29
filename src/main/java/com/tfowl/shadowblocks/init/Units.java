package com.tfowl.shadowblocks.init;

import com.tfowl.shadowblocks.registry.ObjectRegistry;
import com.tfowl.shadowblocks.unit.*;

/**
 * Creates and registers all of the default units
 */
public class Units {

	public static final Unit MAGE = new UnitMage();
	public static final Unit PLAYER = new UnitPlayer();
	public static final Unit ROGUE = new UnitRogue();
	public static final Unit SKELETON = new UnitSkeleton();

	/**
	 * Registers all of the default units
	 */
	public static void init() {
		ObjectRegistry.register(MAGE);
		ObjectRegistry.register(PLAYER);
		ObjectRegistry.register(ROGUE);
		ObjectRegistry.register(SKELETON);
	}
}
