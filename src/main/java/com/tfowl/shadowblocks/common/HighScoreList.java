package com.tfowl.shadowblocks.common;

import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class HighScoreList implements ISerializable {

	private List<HighScore> highscores;

	public HighScoreList() {
		this.highscores = new ArrayList<>();
	}

	public List<HighScore> getHighscores() {
		return highscores;
	}


	@Override
	public boolean writeToBuffer(ByteBuffer buffer) {
		try {
			buffer.putInt(highscores.size());
			for (HighScore highscore : highscores) {
				highscore.writeToBuffer(buffer);
			}
			return true;
		} catch (BufferOverflowException e) {
			return false;
		}
	}

	@Override
	public boolean readFromBuffer(ByteBuffer buffer) {
		try {
			int length = buffer.getInt();
			this.highscores = new ArrayList<>(length);
			for (int i = 0; i < length; i++) {
				HighScore s = new HighScore();
				s.readFromBuffer(buffer);
				highscores.add(s);
			}
			return true;
		} catch (BufferUnderflowException e) {
			return false;
		}
	}
}
