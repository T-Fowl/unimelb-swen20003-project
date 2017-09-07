package com.tfowl.project;

import com.tfowl.project.game.ShadowBlocksGame;
import com.tfowl.project.logging.Logger;
import com.tfowl.project.logging.LoggerFactory;
import com.tfowl.project.reference.Graphical;
import com.tfowl.project.reference.Resources;
import com.tfowl.project.util.JulLoggingSystem;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Launching class for the {@link ShadowBlocksGame}.
 * <p>
 * Created by Thomas on 1/09/2017.
 */
public class Start {

	private static final Logger logger;

	static {
		try {
			/* Configure the JUL logging system. */
			LogManager.getLogManager().readConfiguration(
					Thread.currentThread().getContextClassLoader()
							.getResourceAsStream(Resources.DEFAULT_LOGGING_CONFIG)
			);
		} catch (IOException e) {
			/* The logger of this class is not initialised yet, fall back to System.err
			* If this does happen, all loggers will not be configured correctly. */
			System.err.println("Error configuring the logging system: " + e.getLocalizedMessage());
		}
		logger = LoggerFactory.getLogger(Start.class);
	}


	public static void main(String[] args) {
		logger.info("Starting application.");

		/* Tie in the Slick logging system with the JUL one */
		org.newdawn.slick.util.Log.setLogSystem(new JulLoggingSystem());


		/* Launch the game */
		ShadowBlocksGame game = new ShadowBlocksGame();
		try {
			AppGameContainer container = new AppGameContainer(game);
			container.setShowFPS(true);
			container.setVSync(true);
			container.setDisplayMode(Graphical.DEFAULT_SCREEN_WIDTH, Graphical.DEFAULT_SCREEN_HEIGHT, Graphical.DEFAULT_FULLSCREEN_FLAG);
			logger.info("Game container start.");
			container.start();
		} catch (SlickException e) {
			logger.error("Creating game", e);
			e.printStackTrace();
		}
	}
}
