package com.tfowl.project.world;

import com.tfowl.project.entity.Player;
import com.tfowl.project.level.Level;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by Thomas on 6/09/2017.
 */
public class World {

	private Level level;
	private Player player;

	public World() {

	}

	public void init() throws SlickException {
		player = new Player();
		player.init();
	}

	public void loadLevel(Level level) {
		this.level = level;
		player.setxCoordinate(level.getPlayerStartX());
		player.setyCoordinate(level.getPlayerStartY());
	}

	public void draw(Graphics g, GameContainer gc) throws SlickException {
		level.drawCentered(g, gc.getWidth() / 2, gc.getHeight() / 2);
		player.draw(g,
				(gc.getWidth() - level.getRenderedWidth()) / 2 + 32 * player.getxCoordinate(),
				(gc.getHeight() - level.getRenderedHeight()) / 2 + 32 * player.getyCoordinate());
	}

	public Level getLevel() {
		return level;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
