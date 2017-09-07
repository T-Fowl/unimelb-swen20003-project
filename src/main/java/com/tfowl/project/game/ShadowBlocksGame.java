package com.tfowl.project.game;

import com.tfowl.project.level.Level;
import com.tfowl.project.util.ResourceLoader;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.io.IOException;

/**
 * Created by Thomas on 1/09/2017.
 */
public class ShadowBlocksGame extends BasicGame {


	private static final String SCREEN_TITLE = "Shadow Blocks";

	public ShadowBlocksGame() {
		super(SCREEN_TITLE);
	}

	Level level0 = null;

	@Override
	public void init(GameContainer container) throws SlickException {
		try {
			level0 = Level.readFromStream(ResourceLoader.getResourceAsStream("levels/0.lvl"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		level0.drawCentered(g, container.getWidth() / 2, container.getHeight() / 2);
	}
}
