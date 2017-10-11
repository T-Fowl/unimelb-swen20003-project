package com.tfowl.project.effect;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EffectInstance implements IRenderable {

	private Effect effect;
	private Position position;

	public EffectInstance(Effect unit) {
		this.effect = unit;
		this.position = new Position();
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

	}
}
