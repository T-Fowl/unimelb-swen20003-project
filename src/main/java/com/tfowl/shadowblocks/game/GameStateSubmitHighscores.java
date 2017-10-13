package com.tfowl.shadowblocks.game;

import com.tfowl.shadowblocks.logging.Logger;
import com.tfowl.shadowblocks.logging.LoggerFactory;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameStateSubmitHighscores extends BasicGameState {

	private static final Logger logger = LoggerFactory.getLogger(GameStateSubmitHighscores.class);

	public static final int STATE_ID = 101;

	private TextField nameInput;

	@Override
	public int getID() {
		return STATE_ID;
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		gameContainer.setShowFPS(false);

		nameInput = new TextField(gameContainer, gameContainer.getDefaultFont(), 0, 100, 200, 50, new ComponentListener() {
			@Override
			public void componentActivated(AbstractComponent abstractComponent) {
				System.out.println("Component activated: " + abstractComponent);
			}
		});
		nameInput.setBackgroundColor(Color.white);
		nameInput.setFocus(true);
		nameInput.setBorderColor(Color.gray);
		nameInput.setTextColor(Color.black);
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		nameInput.render(gameContainer, graphics);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
//		if (gameContainer.getInput().isKeyPressed(Input.KEY_N)) {
//			stateBasedGame.enterState(GameStateSinglePlayer.STATE_ID);
//		}
	}
}
