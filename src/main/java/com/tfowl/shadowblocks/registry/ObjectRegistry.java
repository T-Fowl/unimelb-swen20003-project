package com.tfowl.shadowblocks.registry;

import com.tfowl.shadowblocks.block.Block;
import com.tfowl.shadowblocks.effect.Effect;
import com.tfowl.shadowblocks.reference.Resources;
import com.tfowl.shadowblocks.tile.Tile;
import com.tfowl.shadowblocks.unit.Unit;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

/**
 * Central point for registering all Blocks, Effects, Tiles, Units and Images
 * <p>
 * Can be queried later on for access
 */
public class ObjectRegistry {

	private static Map<String, Image> images = new HashMap<>();

	private static Map<String, Block> registeredBlocks = new HashMap<>();
	private static Map<String, Effect> registeredEffects = new HashMap<>();
	private static Map<String, Tile> registeredTiles = new HashMap<>();
	private static Map<String, Unit> registeredUnits = new HashMap<>();

	/**
	 * Goes through all registered items and loads their image(s)
	 *
	 * @throws SlickException If the underlying Slick library throws an exception
	 */
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

	/**/
	private static void loadSprite(String folder, String name) throws SlickException {
		Image image = new Image(Resources.IMAGES_DIRECTORY + "/" + folder + "/" + name + "." + Resources.DEFAULT_IMAGE_EXTENSION);
		images.put(name, image);
	}

	/**
	 * Get an image for the given item name
	 *
	 * @param name Name of the item to lookup the image for
	 * @return The image for the given items name
	 */
	public static Image getImage(String name) {
		return images.get(name);
	}

	/**
	 * @param name Name to lookup
	 * @return Any kind of item that has the given name
	 */
	public static Object get(String name) {
		if (registeredBlocks.containsKey(name)) return registeredBlocks.get(name);
		if (registeredTiles.containsKey(name)) return registeredTiles.get(name);
		if (registeredUnits.containsKey(name)) return registeredUnits.get(name);
		if (registeredEffects.containsKey(name)) return registeredEffects.get(name);
		return null;
	}

	/* All methods below just register, check and retrieve items of different kinds */

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
