package com.tfowl.shadowblocks.common.requests;

import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.ByteBuffer;

public class GetHighScoreListRequest implements ISerializable {

	private int amount;

	public GetHighScoreListRequest() {

	}

	public GetHighScoreListRequest(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public ByteBuffer getBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(amount);
		buffer.flip();
		return buffer;
	}

	@Override
	public void readBuffer(ByteBuffer buffer) {
		this.amount = buffer.getInt();
	}
}
