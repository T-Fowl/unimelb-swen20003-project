package com.tfowl.project.impl;

import com.tfowl.project.effect.Effect;
import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Instance of a block in the {@link com.tfowl.project.world.World}. A {@link Effect} instance is
 * uniquely identified by its {@link Position} and type.
 */
public class EffectInstance implements IRenderable {

	private Effect effect;
	private Position position;
	private long totalElapsedTime = 0;

	public EffectInstance(Effect unit, Position position) {
		this.effect = unit;
		this.position = position;
	}

	public long getTotalElapsedTime() {
		return totalElapsedTime;
	}

	public void incrementTime(long delta) {
		totalElapsedTime += delta;
	}

	public Effect getEffect() {
		return effect;
	}

	public void setEffect(Effect effect) {
		this.effect = effect;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public void draw(Graphics g, float gx, float gy) throws SlickException {
		ObjectRegistry.getImage(effect.getName()).draw(gx, gy);
	}

	/* We override the size queries as the only effect current in effect has a non-standard size  */

	@Override
	public int getRenderedWidth() {
		return ObjectRegistry.getImage(effect.getName()).getWidth();
	}

	@Override
	public int getRenderedHeight() {
		return ObjectRegistry.getImage(effect.getName()).getHeight();
	}
}
