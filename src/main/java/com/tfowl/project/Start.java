package com.tfowl.project;

import com.tfowl.project.game.ShadowBlocksGame;
import com.tfowl.project.reference.Graphical;
import com.tfowl.project.util.JulLoggingSystem;
import com.tfowl.project.util.ResourceLoader;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Created by Thomas on 1/09/2017.
 */
public class Start {

	public static void main(String[] args) {

		try {
			LogManager.getLogManager().readConfiguration(
					ResourceLoader.getResourceAsStream("logging.properties")
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

		org.newdawn.slick.util.Log.setLogSystem(new JulLoggingSystem());


		ShadowBlocksGame game = new ShadowBlocksGame();
		try {
			AppGameContainer container = new AppGameContainer(game);
			container.setShowFPS(true);
			container.setVSync(true);
			container.setDisplayMode(Graphical.DEFAULT_SCREEN_WIDTH, Graphical.DEFAULT_SCREEN_HEIGHT, Graphical.DEFAULT_FULLSCREEN_FLAG);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
