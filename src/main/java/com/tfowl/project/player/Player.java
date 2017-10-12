package com.tfowl.project.player;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.impl.UnitInstance;
import com.tfowl.project.init.Units;

/**
 * A player is a special kind of {@link UnitInstance}.
 */
public class Player extends UnitInstance implements IRenderable {

	private String playerDisplayName;

	public Player(String name) {
		super(Units.PLAYER);
		this.playerDisplayName = name;
	}

	public String getPlayerDisplayName() {
		return playerDisplayName;
	}
}
