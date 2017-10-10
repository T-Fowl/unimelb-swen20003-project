package com.tfowl.project.world;

import com.tfowl.project.level.Level;
import com.tfowl.project.reference.Graphical;
import com.tfowl.project.unit.Player;
import com.tfowl.project.util.Direction;
import com.tfowl.project.util.InputUtil;
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
				(gc.getWidth() - level.getRenderedWidth()) / 2 + Graphical.TILE_SIDE_LENGTH * player.getX(),
				(gc.getHeight() - level.getRenderedHeight()) / 2 + Graphical.TILE_SIDE_LENGTH * player.getY());
	}

	public void update(Input input) {
		/* One move per update. This stops diagonal movement. */
		boolean hasPlayerMoved = false;

		/* Go through the 4 cardinal directions and move the player if appropriate. Only one move per update. */
		if (InputUtil.isUp(input)) {
			if (level.canWalkInDirection(player.getX(), player.getY(), Direction.UP)) {
				player.move(Direction.UP, Graphical.PLAYER_MOVEMENT_UNITS);
				hasPlayerMoved = true;
			}
		}
		if (InputUtil.isRight(input) && !hasPlayerMoved) {
			if (level.canWalkInDirection(player.getX(), player.getY(), Direction.RIGHT)) {
				player.move(Direction.RIGHT, Graphical.PLAYER_MOVEMENT_UNITS);
				hasPlayerMoved = true;
			}
		}
		if (InputUtil.isDown(input) && !hasPlayerMoved) {
			if (level.canWalkInDirection(player.getX(), player.getY(), Direction.DOWN)) {
				player.move(Direction.DOWN, Graphical.PLAYER_MOVEMENT_UNITS);
				hasPlayerMoved = true;
			}
		}
		if (InputUtil.isLeft(input) && !hasPlayerMoved) {
			if (level.canWalkInDirection(player.getX(), player.getY(), Direction.LEFT)) {
				player.move(Direction.LEFT, Graphical.PLAYER_MOVEMENT_UNITS);
				hasPlayerMoved = true; //Not needed but kept for future expansion of this method
			}
		}
	}
}
