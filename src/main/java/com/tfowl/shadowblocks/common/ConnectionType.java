package com.tfowl.shadowblocks.common;

import java.nio.ByteBuffer;

public enum ConnectionType {
	HIGHSCORES((byte) 3),
	COMPETE((byte) 5),
	UNKNOWN((byte) 120),
	UNESTABLISHED((byte) 121);

	byte value;

	ConnectionType(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public static ConnectionType fromByte(byte b) {
		for (ConnectionType type : values()) {
			if (type != UNKNOWN && type != UNESTABLISHED && type.value == b)
				return type;
		}
		return UNKNOWN;
	}

	public static ConnectionType fromBuffer(ByteBuffer buffer) {
		return fromByte(buffer.get());
	}

	public void toBuffer(ByteBuffer buffer) {
		buffer.put(value);
	}
}
