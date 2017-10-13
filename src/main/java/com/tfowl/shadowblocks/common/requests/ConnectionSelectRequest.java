package com.tfowl.shadowblocks.common.requests;

import com.tfowl.shadowblocks.common.ConnectionType;
import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.ByteBuffer;

public class ConnectionSelectRequest implements ISerializable {

	private ConnectionType type = ConnectionType.UNESTABLISHED;

	public ConnectionSelectRequest() {
	}

	public ConnectionSelectRequest(ConnectionType type) {
		this.type = type;
	}

	@Override
	public ByteBuffer getBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(1);
		buffer.put(type.getValue());
		buffer.flip();
		return buffer;
	}

	@Override
	public void readBuffer(ByteBuffer buffer) {
		this.type = ConnectionType.fromByte(buffer.get());
	}
}
