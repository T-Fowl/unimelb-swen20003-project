package com.tfowl.shadowblocks.game;

import com.tfowl.shadowblocks.Start;
import com.tfowl.shadowblocks.init.Blocks;
import com.tfowl.shadowblocks.init.Effects;
import com.tfowl.shadowblocks.init.Tiles;
import com.tfowl.shadowblocks.init.Units;
import com.tfowl.shadowblocks.level.Level;
import com.tfowl.shadowblocks.logging.Logger;
import com.tfowl.shadowblocks.logging.LoggerFactory;
import com.tfowl.shadowblocks.reference.Graphical;
import com.tfowl.shadowblocks.registry.ObjectRegistry;
import com.tfowl.shadowblocks.util.InputUtil;
import com.tfowl.shadowblocks.util.ResourceLoader;
import com.tfowl.shadowblocks.world.IWorldCallbackListener;
import com.tfowl.shadowblocks.world.World;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.IOException;

public class GameStateMultiPlayer extends BasicGameState implements IWorldCallbackListener {
	private static final Logger logger = LoggerFactory.getLogger(GameStateSinglePlayer.class);

	public static final int STATE_ID = 999;

	/* The word in which the player, plays */
	private final World world1 = new World(this, InputUtil.KeyMap.WASD);
	private final World world2 = new World(this, InputUtil.KeyMap.ARROWS);

	private int winningPlayer = -1;
	private boolean player1Finished = false;
	private boolean player2Finished = false;

	private long timeSinceWin = 0;

	@Override
	public int getID() {
		return STATE_ID;
	}

	private StateBasedGame game;


	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		logger.info("Initializing game");

		this.game = stateBasedGame;

		Blocks.init();
		Tiles.init();
		Effects.init();
		Units.init();
		ObjectRegistry.registerAllImages();

		/* Initialise the world1 and then load the levels */
		world1.init();
		world2.init();
		try {
			for (int i = 0; i <= 5; i++) {
				Level level = ResourceLoader.getLevelResource(Integer.toString(i));
				world1.getLevelProvider().addLevel(i, level);
				world2.getLevelProvider().addLevel(i, level);
			}
			world1.loadFirstLevel();
			world2.loadFirstLevel();
		} catch (IOException e) {
			logger.error("Loading level", e);
			e.printStackTrace();
		}
	}


	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics g) throws SlickException {

		if (winningPlayer < 0) {
			/* Draw the world1. By default the current level will be drawn centered. */
			world1.drawCentered(g, container.getWidth() / 4, container.getHeight() / 2);
			world2.drawCentered(g, container.getWidth() / 2 + container.getWidth() / 4, container.getHeight() / 2);
		} else {
			g.drawString("Player " + winningPlayer + " wins!", container.getWidth() / 2 - 30, container.getHeight() / 2);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateBasedGame, int delta) throws SlickException {

		if (player1Finished && !player2Finished)
			winningPlayer = 1;
		else if (player2Finished && !player1Finished)
			winningPlayer = 2;

		if (winningPlayer > 0) {
			timeSinceWin += delta;

			if(timeSinceWin > 2000) {
				stateBasedGame.enterState(GameStateMenu.STATE_ID, new FadeOutTransition(), new FadeInTransition());
			}

			return;
		}


		/* Update the worlds */
		world1.update(container.getInput(), delta);
		world2.update(container.getInput(), delta);

		/* Check for escape being pressed, exit the game */
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
			container.exit();
		else if (container.getInput().isKeyPressed(Input.KEY_F) && Start.DEBUG)
			world1.nextLevel();
		else if (container.getInput().isKeyPressed(Input.KEY_G) && Start.DEBUG)
			world2.nextLevel();
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		world1.loadFirstLevel();
		world2.loadFirstLevel();

		if (container instanceof AppGameContainer) {
			((AppGameContainer) container).setDisplayMode(Graphical.DEFAULT_SCREEN_WIDTH * 2, Graphical.DEFAULT_SCREEN_HEIGHT, false);
		} else {
			container.exit();
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		super.leave(container, game);

		if (container instanceof AppGameContainer) {
			((AppGameContainer) container).setDisplayMode(Graphical.DEFAULT_SCREEN_WIDTH, Graphical.DEFAULT_SCREEN_HEIGHT, false);
		} else {
			container.exit();
		}
	}

	@Override
	public void onAllLevelsFinished(World world) {
		if (world == world1)
			player1Finished = true;
		if (world == world2)
			player2Finished = true;
	}
}
