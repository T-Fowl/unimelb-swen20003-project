package com.tfowl.shadowblocks.client;

import com.tfowl.shadowblocks.common.ConnectingState;
import com.tfowl.shadowblocks.common.ConnectionType;
import com.tfowl.shadowblocks.common.Packet;
import com.tfowl.shadowblocks.common.requests.ConnectionSelectRequest;
import com.tfowl.shadowblocks.common.response.ConnectionSelectResponse;
import com.tfowl.shadowblocks.logging.Logger;
import com.tfowl.shadowblocks.logging.LoggerFactory;
import com.tfowl.shadowblocks.reference.PacketIds;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Client implements CompletionHandler<Integer, Object> {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);

	private static final Object ATTACHMENT_SEND = "send";
	private static final Object ATTACHMENT_RECIEVE = "received";

	private InetSocketAddress remoteAddress;
	private AsynchronousSocketChannel channel;

	private ConnectingState state = ConnectingState.NOT_CONNECTED;
	private String connectionErrorMessage;

	private ConnectionType type;

	private ByteBuffer readBuffer;
	private ByteBuffer writeBuffer;

	private boolean isSending = false;
	private Queue<Packet> outgoing = new LinkedList<>();

	public Client(String host, int port, ConnectionType type) {
		this.remoteAddress = new InetSocketAddress(host, port);
		this.type = type;
	}

	public ConnectingState getState() {
		return state;
	}

	public String getConnectionErrorMessage() {
		return connectionErrorMessage;
	}

	private int packetsInAir = 0;

	protected abstract void onPacket(Packet packet);

	protected abstract void onConnected();

	private void onPacketResponse(Packet packet) {
		if (packet.getId() == PacketIds.CONNECTION_SELECT_RESPONSE) {
			ConnectionSelectResponse response = new ConnectionSelectResponse();
			response.readBuffer(packet.getBody());

			if (response.isOkay()) {
				state = ConnectingState.CONNECTED;
				onConnected();
			}
		} else {
			onPacket(packet);
		}

	}

	protected void sendPacket(Packet packet) {
		outgoing.offer(packet);
		if (!isSending) {
			isSending = true;
			writeOutgoingPackets();
			writeBuffer.flip();
			channel.write(writeBuffer, ATTACHMENT_SEND, this);
		}
	}

	public void connect() throws IOException {
		channel = AsynchronousSocketChannel.open();
		channel.connect(remoteAddress, null, new CompletionHandler<Void, Object>() {
			@Override
			public void completed(Void result, Object attachment) {
				readBuffer = ByteBuffer.allocate(2048);
				writeBuffer = ByteBuffer.allocate(2048);

				ConnectionSelectRequest req = new ConnectionSelectRequest(type);
				Packet packet = Packet.wrap(PacketIds.CONNECTION_SELECT_REQUEST, req);
				sendPacket(packet);
				channel.read(readBuffer, ATTACHMENT_RECIEVE, Client.this);
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				exc.printStackTrace();
				state = ConnectingState.FAILED;
			}
		});
		state = ConnectingState.CONNECTING;
	}

	public void disconnect() throws IOException {
		try {
			channel.close();
		} catch (Throwable t) {

		}
		state = ConnectingState.NOT_CONNECTED;
	}

	private void writeOutgoingPackets() {
		while (!outgoing.isEmpty()) {
			Packet packet = outgoing.peek();
			if (packet.tryWrite(writeBuffer)) {
				outgoing.poll();
			} else {
				break;
			}
		}
	}

	private void readIncomingPackets() {
		while (true) {
			Packet p = new Packet();
			if (p.tryRead(readBuffer)) {
				onPacketResponse(p);
			} else {
				break;
			}
		}
	}


	@Override
	public void completed(Integer result, Object attachment) {
		if (ATTACHMENT_SEND.equals(attachment)) {
			writeBuffer.compact();
			writeOutgoingPackets();
			writeBuffer.flip();

			if (writeBuffer.limit() > 0) {
				channel.write(writeBuffer, attachment, this);
			} else {
				isSending = false;
			}
		}
		if (ATTACHMENT_RECIEVE.equals(attachment)) {
			readBuffer.flip();
			readIncomingPackets();
			readBuffer.compact();
			channel.read(readBuffer, attachment, this);
		}
	}

	@Override
	public void failed(Throwable exc, Object attachment) {
		exc.printStackTrace();
	}
}
