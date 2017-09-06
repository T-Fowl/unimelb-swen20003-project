package com.tfowl.project.world;

import com.tfowl.project.entity.Entity;
import com.tfowl.project.entity.Player;
import com.tfowl.project.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 6/09/2017.
 */
public class World {

	private Level level;
	private List<Entity> entities;
	private Player player;

	public World() {
		entities = new ArrayList<>();
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
