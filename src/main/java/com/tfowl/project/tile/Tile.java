package com.tfowl.project.tile;


import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.util.ResourceLoader;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Thomas on 6/09/2017.
 */
public class Tile implements IRenderable {

	private static ConcurrentMap<String, Tile> loadedTiles;

	static {
		loadedTiles = new ConcurrentHashMap<>();
	}

	private String name;
	private Image sprite;

	public Tile(String name, Image sprite) {
		this.name = name;
		this.sprite = sprite;
	}

	public String getName() {
		return name;
	}

	@Override
	public void draw(Graphics g, int x, int y) throws SlickException {
		g.drawImage(sprite, x, y);
	}

	public static Optional<Tile> getTileFromName(String name) {
		return Optional.ofNullable(
				loadedTiles.computeIfAbsent(name.toLowerCase().trim(), tileName -> {
					Image tileImage = ResourceLoader.getImageResource(tileName);
					return null == tileImage ? null : new Tile(tileName, tileImage);
				})
		);
	}
}
