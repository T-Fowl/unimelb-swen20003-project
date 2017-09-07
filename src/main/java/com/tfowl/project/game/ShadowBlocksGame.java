package com.tfowl.project.game;

import com.tfowl.project.level.Level;
import com.tfowl.project.reference.Strings;
import com.tfowl.project.util.ResourceLoader;
import com.tfowl.project.world.World;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.io.IOException;

/**
 * Created by Thomas on 1/09/2017.
 */
public class ShadowBlocksGame extends BasicGame {

	public ShadowBlocksGame() {
		this(Strings.DEFAULT_WINDOW_TITLE);
	}

	public ShadowBlocksGame(String title) {
		super(title);
	}

	private World world = new World();

	@Override
	public void init(GameContainer container) throws SlickException {
		world.init();
		try {
			world.loadLevel(Level.readFromStream(ResourceLoader.getResourceAsStream("levels/0.lvl")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		world.update(container.getInput(), delta);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		world.draw(g, container);
	}
}
