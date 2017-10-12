package com.tfowl.shadowblocks.world;

import com.tfowl.shadowblocks.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides access to loaded and registered levels in a convenient manner.
 */
public class WorldLevelProvider {

	private int currentLevelIndex;
	private List<Level> levels;

	WorldLevelProvider() {
		currentLevelIndex = -1; //Calling nextLevel will first give the first level
		levels = new ArrayList<>();
	}

	/**
	 * @return A list of all levels
	 */
	public List<Level> getLevels() {
		return levels;
	}

	/**
	 * Add a level as the final level
	 *
	 * @param level Level to add
	 */
	public void addLevel(Level level) {
		levels.add(level);
	}

	/**
	 * Add a level at the given index
	 *
	 * @param index index to add the level
	 * @param level level to add
	 */
	public void addLevel(int index, Level level) {
		levels.add(index, level);
	}

	/**
	 * @return The current level index
	 */
	public int getCurrentLevelIndex() {
		return currentLevelIndex;
	}

	/**
	 * @return true if there is a next level, false if this is the last level
	 */
	public boolean hasNextLevel() {
		return currentLevelIndex + 1 < levels.size();
	}

	/**
	 * @return The current level
	 */
	public Level currentLevel() {
		return levels.get(currentLevelIndex);
	}

	/**
	 * Move the level pointer forwards and return the next level.
	 *
	 * @return The next level.
	 */
	public Level nextLevel() {
		if (hasNextLevel()) {
			currentLevelIndex++;
			return levels.get(currentLevelIndex);
		} else {
			return currentLevel();
		}
	}

	/**
	 * Move the current level pointer backwards and return the previous level.
	 *
	 * @return The previous level.
	 */
	public Level previousLevel() {
		currentLevelIndex--;
		return levels.get(currentLevelIndex);
	}
}
