package com.tfowl.shadowblocks.net;

import java.nio.ByteBuffer;

public interface ISerializable {

	public boolean writeToBuffer(ByteBuffer buffer);

	public boolean readFromBuffer(ByteBuffer buffer);
}
