package com.tfowl.project.world;

import com.tfowl.project.entity.Player;
import com.tfowl.project.level.Level;

/**
 * Created by Thomas on 6/09/2017.
 */
public class World {

	private Level level;
	private Player player;

	public World() {

	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
