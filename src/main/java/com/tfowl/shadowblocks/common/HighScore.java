package com.tfowl.shadowblocks.common;

import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
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
	public boolean writeToBuffer(ByteBuffer buffer) {
		ByteBuffer encode = Charset.forName("UTF-8")
				.encode(playerName);

		try {
			buffer.putInt(playerName.length());
			buffer.put(encode);
			buffer.putLong(totalTimeTaken);
			return true;
		} catch (BufferOverflowException e) {
			return false;
		}
	}

	@Override
	public boolean readFromBuffer(ByteBuffer buffer) {
		try {
			int length = buffer.getInt();

			byte[] data = new byte[length];
			ByteBuffer b = ByteBuffer.wrap(data);
			buffer.get(data);
			this.playerName = Charset.forName("UTF-8").decode(b).toString();

			this.totalTimeTaken = buffer.getLong();
			return true;
		} catch (BufferUnderflowException e) {
			return false;
		}
	}
}
