package com.tfowl.shadowblocks.common;

public enum ConnectionType {
	HIGHSCORES((byte) 3),
	COMPETE((byte) 5),
	UNKNOWN((byte) 120);

	byte value;

	ConnectionType(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public static ConnectionType fromByte(byte b) {
		for (ConnectionType type : values()) {
			if (type != UNKNOWN && type.value == b)
				return type;
		}
		return UNKNOWN;
	}
}
