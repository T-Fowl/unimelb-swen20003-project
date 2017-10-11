package com.tfowl.project.unit;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class UnitInstance implements IRenderable {

	private Unit unit;
	private Position position;

	public UnitInstance(Unit unit) {
		this.unit = unit;
		this.position = new Position();
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public void draw(Graphics g, int gx, int gy) throws SlickException {
		ObjectRegistry.getImage(unit.getName()).draw(gx, gy);
	}
}
