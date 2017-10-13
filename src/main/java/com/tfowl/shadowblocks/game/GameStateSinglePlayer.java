package com.tfowl.shadowblocks.game;

import com.tfowl.shadowblocks.Start;
import com.tfowl.shadowblocks.init.Blocks;
import com.tfowl.shadowblocks.init.Effects;
import com.tfowl.shadowblocks.init.Tiles;
import com.tfowl.shadowblocks.init.Units;
import com.tfowl.shadowblocks.logging.Logger;
import com.tfowl.shadowblocks.logging.LoggerFactory;
import com.tfowl.shadowblocks.registry.ObjectRegistry;
import com.tfowl.shadowblocks.util.ResourceLoader;
import com.tfowl.shadowblocks.world.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class GameStateSinglePlayer extends BasicGameState {

	private static final Logger logger = LoggerFactory.getLogger(GameStateSinglePlayer.class);

	public static final int STATE_ID = 100;

	private AtomicLong sharedCompletionTime;

	public GameStateSinglePlayer(AtomicLong sharedCompletionTime) {
		this.sharedCompletionTime = sharedCompletionTime;
	}

	/* The word in which the player, plays */
	private final World world = new World(this);

	@Override
	public int getID() {
		return STATE_ID;
	}

	private StateBasedGame game;

	public void adviceLastLevelReached() {
		sharedCompletionTime.set(world.getFinishTime());
		game.enterState(GameStateSelectHighScores.STATE_ID, new FadeOutTransition(), new FadeInTransition());
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		logger.info("Initializing game");

		this.game = stateBasedGame;

		Blocks.init();
		Tiles.init();
		Effects.init();
		Units.init();
		ObjectRegistry.registerAllImages();

		/* Initialise the world and then load the levels */
		world.init();
		try {
			world.getLevelProvider().addLevel(0, ResourceLoader.getLevelResource("0"));
			world.getLevelProvider().addLevel(1, ResourceLoader.getLevelResource("1"));
			world.getLevelProvider().addLevel(2, ResourceLoader.getLevelResource("2"));
			world.getLevelProvider().addLevel(3, ResourceLoader.getLevelResource("3"));
			world.getLevelProvider().addLevel(4, ResourceLoader.getLevelResource("4"));
			world.getLevelProvider().addLevel(5, ResourceLoader.getLevelResource("5"));
			world.loadFirstLevel();
		} catch (IOException e) {
			logger.error("Loading level", e);
			e.printStackTrace();
		}
	}


	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
		/* Draw the world. By default the current level will be drawn centered. */
		world.drawCentered(g, container.getWidth() / 2, container.getHeight() / 2);
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateBasedGame, int delta) throws SlickException {
		/* Update the world */
		world.update(container.getInput(), delta);

		/* Check for escape being pressed, exit the game */
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
			container.exit();
		else if (container.getInput().isKeyPressed(Input.KEY_R))
			world.restartLevel();
		else if (container.getInput().isKeyPressed(Input.KEY_Z))
			world.undo();
		else if (container.getInput().isKeyPressed(Input.KEY_F) && Start.DEBUG)
			world.nextLevel();
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		world.loadFirstLevel();
		sharedCompletionTime.set(0);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		super.leave(container, game);
	}
}
