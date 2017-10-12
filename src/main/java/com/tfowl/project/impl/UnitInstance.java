package com.tfowl.project.impl;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.unit.IUnitState;
import com.tfowl.project.unit.Unit;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Instance of a block in the {@link com.tfowl.project.world.World}. A {@link Unit} instance is
 * uniquely identified by its {@link IUnitState} and {@link Position}
 */
public class UnitInstance implements IRenderable {

	private IUnitState state;
	private Position position;

	public UnitInstance(Unit unit) {
		this.state = unit.getDefaultState();
		this.position = new Position();
	}

	public Unit getUnit() {
		return getState().getUnit();
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public IUnitState getState() {
		return state;
	}

	public void setState(IUnitState state) {
		this.state = state;
	}

	@Override
	public void draw(Graphics g, float gx, float gy) throws SlickException {
		//lookup the units image and draw
		ObjectRegistry.getImage(state.getUnit().getName()).draw(gx, gy);
	}
}
