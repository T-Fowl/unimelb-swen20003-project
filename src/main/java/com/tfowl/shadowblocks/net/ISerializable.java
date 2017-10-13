package com.tfowl.shadowblocks.net;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public interface ISerializable {

	public ByteBuffer getBytes();


	public void readBuffer(ByteBuffer buffer);

	default public boolean checkRead(ByteBuffer buffer) {
		int position = buffer.position();
		try {
			readBuffer(buffer);
			return true;
		} catch (BufferUnderflowException e) {
			buffer.position(position);
			return false;
		}
	}
}
