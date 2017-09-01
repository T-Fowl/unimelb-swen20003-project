package com.tfowl.project;

import com.tfowl.project.game.ShadowBlocksGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Thomas on 1/09/2017.
 */
public class Start {

	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 600;

	public static void main(String[] args) {

		ShadowBlocksGame game = new ShadowBlocksGame();
		try {
			AppGameContainer container = new AppGameContainer(game);
			container.setShowFPS(true);
			container.setVSync(true);
			container.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
