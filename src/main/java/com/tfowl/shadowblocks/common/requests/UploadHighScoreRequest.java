package com.tfowl.shadowblocks.common.requests;

import com.tfowl.shadowblocks.common.HighScore;
import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.ByteBuffer;

public class UploadHighScoreRequest implements ISerializable {

	private HighScore highScore;

	public UploadHighScoreRequest() {
		highScore = new HighScore();
	}

	public UploadHighScoreRequest(HighScore highScore) {
		this.highScore = highScore;
	}

	public HighScore getHighScore() {
		return highScore;
	}

	@Override
	public ByteBuffer getBytes() {
		return highScore.getBytes();
	}

	@Override
	public void readBuffer(ByteBuffer buffer) {
		this.highScore.readBuffer(buffer);
	}
}
