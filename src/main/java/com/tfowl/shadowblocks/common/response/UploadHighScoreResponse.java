package com.tfowl.shadowblocks.common.response;

import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.ByteBuffer;

public class UploadHighScoreResponse implements ISerializable {

	private int rank;

	public UploadHighScoreResponse() {
	}

	public UploadHighScoreResponse(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}

	@Override
	public ByteBuffer getBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(4).putInt(rank);
		buffer.flip();
		return buffer;
	}

	@Override
	public void readBuffer(ByteBuffer buffer) {
		this.rank = buffer.getInt();
	}
}
