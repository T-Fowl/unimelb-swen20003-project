package com.tfowl.project.init;

import com.tfowl.project.block.Block;
import com.tfowl.project.block.BlockIce;
import com.tfowl.project.block.BlockTnt;
import com.tfowl.project.reference.Resources;
import com.tfowl.project.registry.ObjectRegistry;

/**
 * Creates and registers all of the default blocks in the game
 */
public class Blocks {

	public static final Block ICE = new BlockIce();

	/* No additional class needed, does nothing special */
	public static final Block STONE = new Block().setName(Resources.Blocks.STONE_NAME).setPushable(true);

	public static final Block TNT = new BlockTnt();

	/**
	 * Register all of the default blocks.
	 */
	public static void init() {
		ObjectRegistry.register(ICE);
		ObjectRegistry.register(STONE);
		ObjectRegistry.register(TNT);
	}
}
