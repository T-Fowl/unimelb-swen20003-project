package com.tfowl.shadowblocks.server;

import com.tfowl.shadowblocks.common.ConnectionType;
import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectingState {

	public ConnectionType connectionType = null;
	public Queue<ISerializable> writeQueue = new LinkedList<>();

	public void update(ByteBuffer readingBuffer) {
		if (connectionType == null) {
			connectionType = ConnectionType.fromByte(readingBuffer.get());

			if (connectionType == ConnectionType.UNKNOWN)
				writeQueue.offer(InitialisationResponse.okay());
			else
				writeQueue.offer(InitialisationResponse.error((byte) 100));
		}
	}
}
