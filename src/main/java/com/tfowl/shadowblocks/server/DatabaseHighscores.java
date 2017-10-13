package com.tfowl.shadowblocks.server;

import com.tfowl.shadowblocks.common.HighScore;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHighscores {

	private static List<HighScore> highScores = new ArrayList<>();

	static {
		for (int i = 0; i < 4; i++) {
			add(new HighScore("T-Fowl", 5_000 + i * 250));
		}
	}

	public static List<HighScore> getTop(int top) {
		return highScores.subList(0, Math.min(highScores.size(), top));
	}

	public static int add(HighScore score) {
		int insert = 0;
		for (int i = 0; i < highScores.size(); i++) {
			if (highScores.get(i).getTotalTimeTaken() > score.getTotalTimeTaken())
				break;
			else if (i == highScores.size() - 1)
				insert = highScores.size();
			else
				insert = i;
		}
		highScores.add(insert, score);
		return insert;
	}
}
