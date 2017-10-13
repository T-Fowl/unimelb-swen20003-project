package com.tfowl.shadowblocks.common.response;

import com.tfowl.shadowblocks.common.HighScoreList;
import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.ByteBuffer;

public class GetHighScoreListResponse implements ISerializable {

	private HighScoreList highscores;

	public GetHighScoreListResponse() {
		highscores = new HighScoreList();
	}

	public GetHighScoreListResponse(HighScoreList highscores) {
		this.highscores = highscores;
	}

	public HighScoreList getHighscores() {
		return highscores;
	}

	@Override
	public ByteBuffer getBytes() {
		return highscores.getBytes();
	}

	@Override
	public void readBuffer(ByteBuffer buffer) {
		this.highscores.readBuffer(buffer);
	}
}
