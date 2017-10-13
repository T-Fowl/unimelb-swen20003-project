package com.tfowl.shadowblocks.common;

import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class HighScore implements ISerializable {

	private String playerName;
	private long totalTimeTaken;

	public HighScore() {
	}

	public HighScore(String playerName, long totalTimeTaken) {
		this.playerName = playerName;
		this.totalTimeTaken = totalTimeTaken;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public long getTotalTimeTaken() {
		return totalTimeTaken;
	}

	public void setTotalTimeTaken(long totalTimeTaken) {
		this.totalTimeTaken = totalTimeTaken;
	}


	@Override
	public ByteBuffer getBytes() {
		ByteBuffer nameBuffer = Charset.forName("UTF-8").encode(playerName);
		ByteBuffer buffer = ByteBuffer.allocate(nameBuffer.limit() + 4 + 8);
		buffer.putInt(nameBuffer.limit());
		buffer.put(nameBuffer);
		buffer.putLong(totalTimeTaken);
		buffer.flip();
		return buffer;
	}

	@Override
	public void readBuffer(ByteBuffer buffer) {
		int length = buffer.getInt();
		byte[] data = new byte[length];
		buffer.get(data);
		ByteBuffer wrap = ByteBuffer.wrap(data);
		playerName = Charset.forName("UTF-8").decode(wrap).toString();
		totalTimeTaken = buffer.getLong();
	}
}
