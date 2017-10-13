package com.tfowl.shadowblocks.common.response;

import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.ByteBuffer;

public class ConnectionSelectResponse implements ISerializable {

	private boolean isOkay;
	private byte error;

	public ConnectionSelectResponse() {
		this(true, (byte) 0);
	}

	public ConnectionSelectResponse(boolean isOkay, byte error) {
		this.isOkay = isOkay;
		this.error = error;
	}

	public boolean isOkay() {
		return isOkay;
	}

	public byte getError() {
		return error;
	}

	@Override
	public ByteBuffer getBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.put((byte) (isOkay ? 1 : 0));
		buffer.put(error);
		buffer.flip();
		return buffer;
	}

	@Override
	public void readBuffer(ByteBuffer buffer) {
		this.isOkay = buffer.get() == 1;
		this.error = buffer.get();
	}
}
