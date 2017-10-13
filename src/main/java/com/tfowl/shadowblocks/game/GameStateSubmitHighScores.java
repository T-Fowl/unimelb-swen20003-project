package com.tfowl.shadowblocks.game;

import com.tfowl.shadowblocks.client.HighScoresClient;
import com.tfowl.shadowblocks.common.ConnectingState;
import com.tfowl.shadowblocks.common.HighScore;
import com.tfowl.shadowblocks.common.HighScoreList;
import com.tfowl.shadowblocks.logging.Logger;
import com.tfowl.shadowblocks.logging.LoggerFactory;
import com.tfowl.shadowblocks.reference.Resources;
import com.tfowl.shadowblocks.util.TimeUtils;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

public class GameStateSubmitHighScores extends BasicGameState implements ComponentListener {

	private static final Logger logger = LoggerFactory.getLogger(GameStateSubmitHighScores.class);

	public static final int STATE_ID = 101;

	private TextField nameInput;
	private HighScoresClient client;

	private HighScoreList highscores;
	private CompletableFuture<HighScoreList> futureHighscores;
	private int rank = -1;
	private CompletableFuture<Integer> futureRank;

	private boolean wasConnectionError = false;
	private String connectionError;

	private AtomicLong sharedCompletionTime;

	private StateBasedGame game;
	private MouseOverArea btnMainMenu;

	public GameStateSubmitHighScores(AtomicLong sharedCompletionTime) {
		this.sharedCompletionTime = sharedCompletionTime;
	}

	@Override
	public int getID() {
		return STATE_ID;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);

		try {
			client.connect();
		} catch (Throwable t) {
			t.printStackTrace();
			connectionError = t.getClass().getSimpleName() + " - " + t.getMessage();
			wasConnectionError = true;
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		super.leave(container, game);

		highscores = null;
		futureHighscores = null;

		rank = -1;
		futureRank = null;

		sharedCompletionTime.set(0);

		try {
			client.disconnect();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		container.setShowFPS(false);
		this.game = stateBasedGame;

		final int width = container.getDefaultFont().getWidth("xxxxxxxxxxxx");
		final int height = container.getDefaultFont().getLineHeight();

		btnMainMenu = new MouseOverArea(container, new Image(Resources.IMAGES_DIRECTORY + "/btnMainMenu.png"), container.getWidth() - 200, container.getHeight() - 200, this);

		nameInput = new TextField(container, container.getDefaultFont(),
				(container.getWidth() - width) / 2, 500,
				width,
				height, abstractComponent -> {
			if (null == futureRank && rank < 0) {
				String text = nameInput.getText().trim();
				if (!text.trim().isEmpty()) {
					text = text.substring(0, Math.min(12, text.length()));
					futureRank = client.upload(new HighScore(text, sharedCompletionTime.get()));
					nameInput.setText("");

					futureRank.thenAccept(r -> {
						rank = r;
					}).thenRun(() -> {
						futureRank = null;
					}).thenRun(() -> {
						highscores = null;
						futureHighscores = client.getHighscores(10);
						futureHighscores.thenAccept(list -> {
							highscores = list;
						}).thenRun(() -> {
							futureHighscores = null;
						});
					});
				}
			}
		});

		nameInput.setBackgroundColor(Color.white);
		nameInput.setFocus(true);
		nameInput.setBorderColor(Color.gray);
		nameInput.setTextColor(Color.black);

		client = new HighScoresClient(ShadowBlocksGame.SERVER_HOST, ShadowBlocksGame.SERVER_PORT);
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		nameInput.render(container, graphics);

		graphics.drawString("Highscores",
				(container.getWidth() - graphics.getFont().getWidth("Highscores")) / 2,
				20);

		if (client.getState() == ConnectingState.NOT_CONNECTED) {
			graphics.drawString("Not Connected.", 100, 50);
		} else if (client.getState() == ConnectingState.CONNECTING) {
			graphics.drawString("Connecting to the highscores server...", 100, 50);
		} else if (client.getState() == ConnectingState.FAILED) {
			graphics.drawString("Error connecting to the server:\n" + client.getConnectionErrorMessage(), 100, 50);
		} else if (client.getState() == ConnectingState.CONNECTED) {
			if (futureHighscores != null) {
				graphics.drawString("Loading...", 100, 50);
			} else if (highscores != null) {
				List<HighScore> list = this.highscores.getHighscores();
				for (int i = 0; i < 10; i++) {
					if (i < list.size())
						graphics.drawString(
								String.format("%02d: %-12s %s", i, list.get(i).getPlayerName(), TimeUtils.formatDuration(list.get(i).getTotalTimeTaken())),
								100, 50 + (graphics.getFont().getLineHeight() + 2) * (i - 1));
					else
						graphics.drawString(String.format("%02d: %-12s %s", i, "-", "-"),
								100, 50 + (graphics.getFont().getLineHeight() + 2) * (i - 1));
				}
			}
			if (futureRank != null) {
				graphics.drawString("Submitting...", 100, 500);
			} else if (rank >= 0) {
				graphics.drawString("Submitted rank: " + rank, 100, 500);
			}
		}

		if (sharedCompletionTime.get() > 0)

			graphics.drawString("Enter your name",
					(container.getWidth() - graphics.getFont().getWidth("Enter your name")) / 2,
					nameInput.getY() - graphics.getFont().getLineHeight() - 5);

		btnMainMenu.render(container, graphics);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
//		if (gameContainer.getInput().isKeyPressed(Input.KEY_N)) {
//			stateBasedGame.enterState(GameStateSinglePlayer.STATE_ID);
//		}

		/* If we are connected, with no highscores and none in the progress of being fetched */
		if (client.getState() == ConnectingState.CONNECTED && highscores == null && futureHighscores == null) {
			futureHighscores = client.getHighscores(10);
			futureHighscores.thenAccept(list -> {
				highscores = list;
			}).thenRun(() -> {
				futureHighscores = null;
			});
		}


	}

	@Override
	public void componentActivated(AbstractComponent abstractComponent) {
		if (abstractComponent == btnMainMenu) {
			game.enterState(GameStateMenu.STATE_ID, new FadeOutTransition(), new FadeInTransition());
		}
	}
}
