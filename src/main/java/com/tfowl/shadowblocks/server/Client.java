package com.tfowl.shadowblocks.server;

import com.tfowl.shadowblocks.common.ConnectionType;
import com.tfowl.shadowblocks.common.Packet;
import com.tfowl.shadowblocks.net.IChannelContext;
import com.tfowl.shadowblocks.net.IChannelHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;

public class Client implements IChannelContext {

	private Server server;
	private SocketChannel channel;
	private SelectionKey readKey;
	private SelectionKey writeKey;

	private ByteBuffer readBuffer;
	private ByteBuffer writeBuffer;

	private ConnectionType connectionType = ConnectionType.UNESTABLISHED;
	private IChannelHandler handler;

	private boolean shouldTerminate = false;

	private Queue<Packet> inboundQueue = new LinkedList<>();
	private Queue<Packet> outboundQueue = new LinkedList<>();

	public Client(SocketChannel channel, Server server) {
		this.server = server;
		this.channel = channel;
		this.handler = new ConnectionSelectHandler();

		this.readBuffer = ByteBuffer.allocate(2048);
		this.writeBuffer = ByteBuffer.allocate(2048);
	}

	@Override
	public SocketChannel getChannel() {
		return channel;
	}

	@Override
	public void setShouldTerminate() {
		shouldTerminate = true;
	}

	@Override
	public boolean getShouldTerminate() {
		return shouldTerminate;
	}

	@Override
	public void enqueueOutbound(Packet packet) {
		outboundQueue.offer(packet);
	}

	@Override
	public void setHandler(IChannelHandler handler) {
		this.handler = handler;
	}

	@Override
	public IChannelHandler getHandler() {
		return handler;
	}

	@Override
	public void setConnectionType(ConnectionType type) {
		this.connectionType = type;
	}

	private void decodePackets() throws IOException {
		while (true) {
			Packet p = new Packet();
			if (p.tryRead(readBuffer)) {
				inboundQueue.offer(p);
			} else {
				break;
			}
		}
		while (!inboundQueue.isEmpty()) {
			getHandler().onPacket(this, inboundQueue.poll());
		}
	}


	public void onRead() throws IOException {
		if (shouldTerminate && outboundQueue.isEmpty()) {
			server.disconnectClient(this);
		} else {
			int bytesRead = -1;
			try {
				bytesRead = channel.read(readBuffer);
			} catch (IOException e) {
				setShouldTerminate();
			}
			if (bytesRead < 0) {
				setShouldTerminate();
			} else {
				System.out.println("bytes:" + bytesRead);
				readBuffer.flip();
				decodePackets();
				readBuffer.compact();
			}
		}
	}

	public void onWrite() throws IOException {
		if (shouldTerminate && outboundQueue.isEmpty()) {
			server.disconnectClient(this);
		} else {
			while (!outboundQueue.isEmpty()) {
				Packet next = outboundQueue.peek();
				if (next.tryWrite(writeBuffer)) {
					outboundQueue.poll(); //Was successfully written, remove from queue
				} else {
					break; //We have filled the buffer as much as we can
				}
			}

			writeBuffer.flip();
			int bytesWritten = -1;
			try {
				bytesWritten = channel.write(writeBuffer);
			} catch (IOException e) {
				setShouldTerminate();
			}
			writeBuffer.compact();

			if (bytesWritten < 0) {
				outboundQueue.clear();
				setShouldTerminate();
			}
		}
	}
}
