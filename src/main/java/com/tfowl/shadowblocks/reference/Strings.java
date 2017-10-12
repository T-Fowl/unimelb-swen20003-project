package com.tfowl.shadowblocks.reference;

/**
 * A set of constant strings for the game.
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public class Strings {


	/* Field should be filled-in by gradle when exporting the project */
	private static final String STUDENT_ID = "@STUDENTID@";
	private static final String STUDENT_NAME = "@STUDENTNAME@";

	/* Fifty percent pain
	 * And a hundred percent reason to remember the name */
	public static final String GAME_NAME = "Shadow Blocks";

	public static final String DISPLAY_MOVES_STRING = "Moves: %d";

	public static final String DEFAULT_WINDOW_TITLE = GAME_NAME + " by " + STUDENT_NAME + "(" + STUDENT_ID + ")";

	/* No instancing */
	private Strings() {
	}
}
