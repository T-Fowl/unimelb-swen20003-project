package com.tfowl.project.world;

import com.tfowl.project.level.Level;

import java.util.ArrayList;
import java.util.List;

public class WorldLevelProvider {

	private int currentLevelIndex;
	private List<Level> levels;

	public WorldLevelProvider() {
		currentLevelIndex = -1;
		levels = new ArrayList<>();
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void addLevel(Level level) {
		levels.add(level);
	}

	public void addLevel(int index, Level level) {
		levels.add(index, level);
	}

	public int getCurrentLevelIndex() {
		return currentLevelIndex;
	}

	public boolean hasNextLevel() {
		return currentLevelIndex + 1 < levels.size();
	}

	public Level currentLevel() {
		return levels.get(currentLevelIndex);
	}

	public Level nextLevel() {
		if (hasNextLevel()) {
			currentLevelIndex++;
			return levels.get(currentLevelIndex);
		} else {
			return currentLevel();
		}
	}

	public Level previousLevel() {
		currentLevelIndex--;
		return levels.get(currentLevelIndex);
	}
}
