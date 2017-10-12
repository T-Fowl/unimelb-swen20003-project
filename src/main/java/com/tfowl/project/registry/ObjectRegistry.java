package com.tfowl.project.registry;

import com.tfowl.project.block.Block;
import com.tfowl.project.effect.Effect;
import com.tfowl.project.tile.Tile;
import com.tfowl.project.unit.Unit;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

public class ObjectRegistry {

	private static Map<String, Image> images = new HashMap<>();

	private static Map<String, Block> registeredBlocks = new HashMap<>();
	private static Map<String, Effect> registeredEffects = new HashMap<>();
	private static Map<String, Tile> registeredTiles = new HashMap<>();
	private static Map<String, Unit> registeredUnits = new HashMap<>();

	public static void registerAllImages() throws SlickException {
		for (String name : registeredBlocks.keySet()) {
			loadSprite("blocks", name);
		}
		for (String name : registeredEffects.keySet()) {
			loadSprite("effects", name);
		}
		for (String name : registeredTiles.keySet()) {
			loadSprite("tiles", name);
		}
		for (String name : registeredUnits.keySet()) {
			loadSprite("units", name);
		}
	}

	private static void loadSprite(String folder, String name) throws SlickException {
		Image image = new Image("images/" + folder + "/" + name + ".png");
		images.put(name, image);
	}

	public static Image getImage(String name) {
		return images.get(name);
	}

	public static Object get(String name) {
		if (registeredBlocks.containsKey(name)) return registeredBlocks.get(name);
		if (registeredTiles.containsKey(name)) return registeredTiles.get(name);
		if (registeredUnits.containsKey(name)) return registeredUnits.get(name);
		if (registeredEffects.containsKey(name)) return registeredEffects.get(name);
		return null;
	}

	public static Class<?> getType(String name) {
		if (registeredBlocks.containsKey(name))
			return Block.class;
		if (registeredEffects.containsKey(name))
			return Effect.class;
		if (registeredTiles.containsKey(name))
			return Tile.class;
		if (registeredUnits.containsKey(name))
			return Unit.class;
		return null;
	}

	public static void register(Block block) {
		registeredBlocks.put(block.getName(), block);
	}

	public static boolean isBlockRegistered(String name) {
		return registeredBlocks.containsKey(name);
	}

	public static Block getBlock(String name) {
		return registeredBlocks.get(name);
	}

	public static void register(Effect effect) {
		registeredEffects.put(effect.getName(), effect);
	}

	public static boolean isEffectRegistered(String name) {
		return registeredEffects.containsKey(name);
	}

	public static Effect getEffect(String name) {
		return registeredEffects.get(name);
	}

	public static void register(Tile tile) {
		registeredTiles.put(tile.getName(), tile);
	}

	public static boolean isTileRegistered(String name) {
		return registeredTiles.containsKey(name);
	}

	public static Tile getTile(String name) {
		return registeredTiles.get(name);
	}

	public static void register(Unit unit) {
		registeredUnits.put(unit.getName(), unit);
	}

	public static boolean isUnitRegistered(String name) {
		return registeredUnits.containsKey(name);
	}

	public static Unit getUnit(String name) {
		return registeredUnits.get(name);
	}
}
