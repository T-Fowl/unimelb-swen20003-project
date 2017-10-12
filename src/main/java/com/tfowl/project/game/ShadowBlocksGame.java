package com.tfowl.project.game;

import com.tfowl.project.init.Blocks;
import com.tfowl.project.init.Effects;
import com.tfowl.project.init.Tiles;
import com.tfowl.project.init.Units;
import com.tfowl.project.logging.Logger;
import com.tfowl.project.logging.LoggerFactory;
import com.tfowl.project.reference.Strings;
import com.tfowl.project.registry.ObjectRegistry;
import com.tfowl.project.util.ResourceLoader;
import com.tfowl.project.world.World;
import org.newdawn.slick.*;

import java.io.IOException;

/**
 * The {@link org.newdawn.slick.Game} implementation for this project.
 * <p>
 * Created by Thomas on 1/09/2017.
 */
public class ShadowBlocksGame extends BasicGame {

	private static final Logger logger = LoggerFactory.getLogger(ShadowBlocksGame.class);

	public ShadowBlocksGame() {
		super(Strings.DEFAULT_WINDOW_TITLE);
	}

	/* The word in which the player, plays */
	private final World world = new World();

	@Override
	public void init(GameContainer container) throws SlickException {
		logger.info("Initializing game");

		Blocks.init();
		Tiles.init();
		Effects.init();
		Units.init();
		ObjectRegistry.registerAllImages();

		/* Initialise the world and then load a level */
		world.init();
		try {
			world.loadLevel(ResourceLoader.getLevelResource("test"));
		} catch (IOException e) {
			logger.error("Loading level 0", e);
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		/* Update the world */
		world.update(container.getInput(), delta);

		/* Check for escape being pressed, exit the game */
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
			container.exit();
		else if (container.getInput().isKeyPressed(Input.KEY_R))
			world.restartLevel();
		else if (container.getInput().isKeyPressed(Input.KEY_Z))
			world.undo();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		/* Draw the world. By default the current level will be drawn centered. */
		world.drawCentered(g, container.getWidth() / 2, container.getHeight() / 2);
	}

}
