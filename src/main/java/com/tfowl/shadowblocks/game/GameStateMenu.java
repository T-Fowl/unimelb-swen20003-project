package com.tfowl.shadowblocks.game;

import com.tfowl.shadowblocks.reference.Resources;
import com.tfowl.shadowblocks.util.ResourceLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.FontUtils;

import java.awt.*;
import java.io.IOException;


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

	private Font font;
	private float fontSize = 30;

	private Color btnNormalColour = Color.blue;
	private Color btnMouseOverColour = Color.cyan;
	private Color btnMouseDownColour = Color.orange;


	@Override
	public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		this.game = stateBasedGame;

		try {
			java.awt.Font awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("fonts/opensans/OpenSans-Regular.ttf"))
					.deriveFont(fontSize);
			font = new TrueTypeFont(awtFont, true);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			font = new TrueTypeFont(java.awt.Font.getFont(java.awt.Font.SANS_SERIF)
					.deriveFont(fontSize), true);
		}

		btnSinglePlayer = new MouseOverArea(container, new Image(Resources.IMAGES_DIRECTORY + "/btnSinglePlayer.png"),
				container.getWidth() / 2 - 200 / 2, 150, this);
		btnSinglePlayer.setNormalColor(btnNormalColour);
		btnSinglePlayer.setMouseOverColor(btnMouseOverColour);
		btnSinglePlayer.setMouseDownColor(btnMouseDownColour);
		btnHighScores = new MouseOverArea(container, new Image(Resources.IMAGES_DIRECTORY + "/btnHighscores.png"),
				container.getWidth() / 2 - 200 / 2, 250, this);
		btnHighScores.setNormalColor(btnNormalColour);
		btnHighScores.setMouseOverColor(btnMouseOverColour);
		btnHighScores.setMouseDownColor(btnMouseDownColour);
		btnQuit = new MouseOverArea(container, new Image(Resources.IMAGES_DIRECTORY + "/btnQuit.png"),
				container.getWidth() / 2 - 200 / 2, 350, this);
		btnQuit.setNormalColor(btnNormalColour);
		btnQuit.setMouseOverColor(btnMouseOverColour);
		btnQuit.setMouseDownColor(btnMouseDownColour);
		btnMultiplayer = new MouseOverArea(container, new Image(Resources.IMAGES_DIRECTORY + "/btnMultiplayer.png"),
				container.getWidth() / 2 - 200 / 2, 450, this);
		btnMultiplayer.setNormalColor(btnNormalColour);
		btnMultiplayer.setMouseOverColor(btnMouseOverColour);
		btnMultiplayer.setMouseDownColor(btnMouseDownColour);
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		FontUtils.drawCenter(font, "Choose An Option", 0, 50, container.getWidth());
		btnSinglePlayer.render(container, graphics);
		btnHighScores.render(container, graphics);
		btnQuit.render(container, graphics);
		btnMultiplayer.render(container, graphics);
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
