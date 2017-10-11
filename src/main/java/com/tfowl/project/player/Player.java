package com.tfowl.project.player;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.unit.UnitInstance;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Player implements IRenderable {

	private String name;
	private UnitInstance unit;

	public Player(String name) {
		this.name = name;
		this.unit = new UnitInstance(ObjectRegistry.getUnit("player")); //TODO
	}

	public String getName() {
		return name;
	}

	public Position getPosition() {
		return unit.getPosition();
	}

	public void setPosition(Position position) {
		unit.setPosition(position);
	}

	@Override
	public void draw(Graphics g, int gx, int gy) throws SlickException {
		unit.draw(g, gx, gy);
	}
}
