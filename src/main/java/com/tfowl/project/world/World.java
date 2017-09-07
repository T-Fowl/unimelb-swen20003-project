package com.tfowl.project.world;

import com.tfowl.project.entity.Player;
import com.tfowl.project.level.Level;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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
		player.setX(level.getPlayerStartX());
		player.setY(level.getPlayerStartY());
	}

	public void draw(Graphics g, GameContainer gc) throws SlickException {
		level.drawCentered(g, gc.getWidth() / 2, gc.getHeight() / 2);
		player.draw(g,
				(gc.getWidth() - level.getRenderedWidth()) / 2 + 32 * player.getX(),
				(gc.getHeight() - level.getRenderedHeight()) / 2 + 32 * player.getY());
	}

	public void update(Input input, int delta) {
		boolean playerNotMoved = true;

		if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W)) {
			if (level.isBlockWalkable(player.getX(), player.getY() - 1)) {
				player.moveY(-1);
				playerNotMoved = false;
			}
		}
		if ((input.isKeyPressed(Input.KEY_RIGHT) || input.isKeyPressed(Input.KEY_D)) && playerNotMoved) {
			if (level.isBlockWalkable(player.getX() + 1, player.getY())) {
				player.moveX(1);
				playerNotMoved = false;
			}
		}
		if ((input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_S)) && playerNotMoved) {
			if (level.isBlockWalkable(player.getX(), player.getY() + 1)) {
				player.moveY(1);
				playerNotMoved = false;
			}
		}
		if ((input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_A)) && playerNotMoved) {
			if (level.isBlockWalkable(player.getX() - 1, player.getY())) {
				player.moveX(-1);
				playerNotMoved = false;
			}
		}
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
