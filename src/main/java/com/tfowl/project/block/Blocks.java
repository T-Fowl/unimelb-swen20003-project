package com.tfowl.project.block;

import com.tfowl.project.registry.ObjectRegistry;

public class Blocks {

	public static final Block ICE = new BlockIce();
	public static final Block STONE = new BlockStone();
	public static final Block TNT = new BlockTnt();

	public static void init() {
		ObjectRegistry.register(ICE);
		ObjectRegistry.register(STONE);
		ObjectRegistry.register(TNT);
	}
}
