package com.tfowl.project.effect;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

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
	public void draw(Graphics g, int gx, int gy) throws SlickException {
		ObjectRegistry.getImage(effect.getName()).draw(gx, gy);
	}
}
