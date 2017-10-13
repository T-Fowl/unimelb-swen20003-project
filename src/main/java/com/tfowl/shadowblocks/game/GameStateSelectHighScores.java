package com.tfowl.shadowblocks.game;

import com.tfowl.shadowblocks.reference.Resources;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameStateSelectHighScores extends BasicGameState implements ComponentListener {

	public static final int STATE_ID = 123;

	private MouseOverArea yesBtn;
	private MouseOverArea noBtn;

	@Override
	public int getID() {
		return STATE_ID;
	}

	private StateBasedGame game;

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		this.game = stateBasedGame;
		yesBtn = new MouseOverArea(gameContainer, new Image(Resources.IMAGES_DIRECTORY + "/btnyes.png"), 50, gameContainer.getHeight() - 200, this);
		noBtn = new MouseOverArea(gameContainer, new Image(Resources.IMAGES_DIRECTORY + "/btnno.png"), gameContainer.getWidth() - 150, gameContainer.getHeight() - 200, this);
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		graphics.drawString("Do you want to share your score?", 50, 50);
		yesBtn.render(gameContainer, graphics);
		noBtn.render(gameContainer, graphics);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

	}

	@Override
	public void componentActivated(AbstractComponent abstractComponent) {
		if (abstractComponent == yesBtn) {
			game.enterState(GameStateSubmitHighScores.STATE_ID, new FadeOutTransition(), new FadeInTransition());
		} else if (abstractComponent == noBtn) {
			game.enterState(GameStateMenu.STATE_ID, new FadeOutTransition(), new FadeInTransition());
		}
	}
}
