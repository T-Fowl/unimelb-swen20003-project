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
	public ByteBuffer getBytes() {
		int total = 4;
		List<ByteBuffer> buffers = new ArrayList<>();
		for (HighScore score : highscores) {
			ByteBuffer bytes = score.getBytes();
			total += bytes.limit();
			buffers.add(bytes);
		}
		ByteBuffer buffer = ByteBuffer.allocate(total);
		buffer.putInt(highscores.size());
		for (ByteBuffer byteBuffer : buffers) {
			buffer.put(byteBuffer);
		}
		buffer.flip();
		return buffer;
	}

	@Override
	public void readBuffer(ByteBuffer buffer) {
		int length = buffer.getInt();
		for(int i = 0; i < length; i++) {
			HighScore hs = new HighScore();
			hs.readBuffer(buffer);
			highscores.add(hs);
		}
	}
}
