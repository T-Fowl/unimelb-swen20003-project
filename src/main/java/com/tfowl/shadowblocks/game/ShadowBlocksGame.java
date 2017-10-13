package com.tfowl.shadowblocks.game;

import com.tfowl.shadowblocks.logging.Logger;
import com.tfowl.shadowblocks.logging.LoggerFactory;
import com.tfowl.shadowblocks.reference.Strings;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The {@link org.newdawn.slick.Game} implementation for this project.
 * <p>
 * Created by Thomas on 1/09/2017.
 */
public class ShadowBlocksGame extends StateBasedGame {

	private static final Logger logger = LoggerFactory.getLogger(ShadowBlocksGame.class);

	public static final String SERVER_HOST = "localhost";
	public static final int SERVER_PORT = 6066;

	private AtomicLong sharedCompletionTime = new AtomicLong(0);

	public ShadowBlocksGame() {
		super(Strings.DEFAULT_WINDOW_TITLE);
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		addState(new GameStateMenu());
		addState(new GameStateSelectHighScores());
		addState(new GameStateSinglePlayer(sharedCompletionTime));
		addState(new GameStateSubmitHighScores(sharedCompletionTime));
		addState(new GameStateMultiPlayer());
		enterState(GameStateMenu.STATE_ID);
	}
}
