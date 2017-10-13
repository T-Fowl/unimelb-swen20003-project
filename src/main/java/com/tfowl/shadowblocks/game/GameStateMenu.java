package com.tfowl.shadowblocks.game;

import com.tfowl.shadowblocks.reference.Resources;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class GameStateMenu extends BasicGameState implements ComponentListener {

	public static final int STATE_ID = 66;

	@Override
	public int getID() {
		return STATE_ID;
	}

	private StateBasedGame game;

	private MouseOverArea btnSinglePlayer;
	private MouseOverArea btnMultiplayer;
	private MouseOverArea btnHighScores;
	private MouseOverArea btnQuit;


	@Override
	public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		this.game = stateBasedGame;

		btnSinglePlayer = new MouseOverArea(container, new Image(Resources.IMAGES_DIRECTORY + "/btnSinglePlayer.png"), 100, 100, this);
		btnHighScores = new MouseOverArea(container, new Image(Resources.IMAGES_DIRECTORY + "/btnHighscores.png"), 100, 200, this);
		btnQuit = new MouseOverArea(container, new Image(Resources.IMAGES_DIRECTORY + "/btnQuit.png"), 100, 300, this);
		btnMultiplayer = new MouseOverArea(container, new Image(Resources.IMAGES_DIRECTORY + "/btnMultiplayer.png"), 100, 400, this);
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		graphics.drawString("Choose an option", 100, 50);
		btnSinglePlayer.render(gameContainer, graphics);
		btnHighScores.render(gameContainer, graphics);
		btnQuit.render(gameContainer, graphics);
		btnMultiplayer.render(gameContainer, graphics);
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateBasedGame, int i) throws SlickException {
		/* Check for escape being pressed, exit the game */
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
			container.exit();
	}

	@Override
	public void componentActivated(AbstractComponent abstractComponent) {
		if (abstractComponent == btnQuit) {
			this.game.getContainer().exit();
		}
		if (abstractComponent == btnHighScores) {
			this.game.enterState(GameStateSubmitHighScores.STATE_ID, new FadeOutTransition(), new FadeInTransition());
		}
		if (abstractComponent == btnSinglePlayer) {
			this.game.enterState(GameStateSinglePlayer.STATE_ID, new FadeOutTransition(), new FadeInTransition());
		}
		if (abstractComponent == btnMultiplayer) {
			this.game.enterState(GameStateMultiPlayer.STATE_ID, new FadeOutTransition(), new FadeInTransition());
		}
	}
}
