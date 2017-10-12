package com.tfowl.project.player;

import com.tfowl.project.graphics.IRenderable;
import com.tfowl.project.impl.UnitInstance;
import com.tfowl.project.registry.ObjectRegistry;

public class Player extends UnitInstance implements IRenderable {

	private String playerName;

	public Player(String name) {
		super(ObjectRegistry.getUnit("player"));
		this.playerName = name;
	}

	public String getPlayerName() {
		return playerName;
	}
}
