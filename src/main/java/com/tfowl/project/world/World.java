package com.tfowl.project.world;

import com.tfowl.project.entity.Player;
import com.tfowl.project.level.Level;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * A world is responsible to holding reference to a current {@link Player} as well as the current {@link Level}.
 * <p>
 * In the future the world will also hold reference to multiple levels (assumption) and switch between them.
 * <p>
 * Created by Thomas on 6/09/2017.
 */
public class World {

	private Level level;
	private Player player;

	/**
	 * Initialised the world. It is required that this method be called after the OpenGL context
	 * has been created.
	 *
	 * @throws SlickException If the underlying Slick library throws an exception.
	 */
	public void init() throws SlickException {
		player = new Player();
		player.init();
	}

	/**
	 * Loads the level and sets it as the current one.
	 *
	 * @param level The level to load.
	 */
	public void loadLevel(Level level) {
		this.level = level;
		player.setX(level.getPlayerStartX());
		player.setY(level.getPlayerStartY());
	}


	public void draw(Graphics g, GameContainer gc) throws SlickException {
		/* Draw the level centered, then the player at the correct location */
		level.drawCentered(g, gc.getWidth() / 2, gc.getHeight() / 2);
		player.draw(g,
				(gc.getWidth() - level.getRenderedWidth()) / 2 + 32 * player.getX(),
				(gc.getHeight() - level.getRenderedHeight()) / 2 + 32 * player.getY());
	}

	public void update(Input input, int delta) {
		/* One move per update. This stops diagonal movement. */
		boolean playerNotMoved = true;

		/* Go through the 4 cardinal directions and move the player if appropriate. Only one move per update. */
		if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W)) {
			if (level.isLocationWalkable(player.getX(), player.getY() - 1)) {
				player.moveY(-1);
				playerNotMoved = false;
			}
		}
		if ((input.isKeyPressed(Input.KEY_RIGHT) || input.isKeyPressed(Input.KEY_D)) && playerNotMoved) {
			if (level.isLocationWalkable(player.getX() + 1, player.getY())) {
				player.moveX(1);
				playerNotMoved = false;
			}
		}
		if ((input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_S)) && playerNotMoved) {
			if (level.isLocationWalkable(player.getX(), player.getY() + 1)) {
				player.moveY(1);
				playerNotMoved = false;
			}
		}
		if ((input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_A)) && playerNotMoved) {
			if (level.isLocationWalkable(player.getX() - 1, player.getY())) {
				player.moveX(-1);
				playerNotMoved = false;
			}
		}
	}
}
