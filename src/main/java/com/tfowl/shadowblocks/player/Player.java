package com.tfowl.shadowblocks.player;

import com.tfowl.shadowblocks.graphics.IRenderable;
import com.tfowl.shadowblocks.world.internal.UnitInstance;
import com.tfowl.shadowblocks.init.Units;

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
