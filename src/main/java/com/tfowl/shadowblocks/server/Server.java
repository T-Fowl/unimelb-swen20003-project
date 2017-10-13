package com.tfowl.shadowblocks.server;

import com.tfowl.shadowblocks.net.ISerializable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Server implements Runnable {

	private InetSocketAddress address;
	private Selector selector;
	private ServerSocketChannel ssc;

	private List<ConnectedClient> clientList = new ArrayList<>();

	public Server(String bindAddress, int port) {
		address = new InetSocketAddress(bindAddress, port);
	}

	public void start() throws IOException {
		ssc = ServerSocketChannel.open();
		ssc.bind(address);
		ssc.configureBlocking(false);
		selector = Selector.open();
		ssc.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			try {
				int selected = selector.select();
				if (selected > 0) {
					Set<SelectionKey> selectionKeys = selector.selectedKeys();

					for (Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext(); ) {
						SelectionKey key = iterator.next();
						if (!key.isValid())
							continue;

						if (key.isAcceptable()) {
							SocketChannel accept = ssc.accept();
							onAccept(accept);
						} else if (key.isReadable()) {
							ConnectedClient client = (ConnectedClient) key.attachment();
							if (client.getShouldTerminate()) {
								client.getChannel().close();
								clientList.removeIf(c -> c == client);
							} else {
								onRead(client);
							}
						} else if (key.isWritable()) {
							ConnectedClient client = (ConnectedClient) key.attachment();
							if (client.getShouldTerminate()) {
								client.getChannel().close();
								clientList.removeIf(c -> c == client);
							} else {
								onWrite(client);
							}
						}
						iterator.remove();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void onAccept(SocketChannel channel) throws IOException {
		channel.configureBlocking(false);

		ConnectedClient connectedClient = new ConnectedClient(channel, new ConnectingState(), ByteBuffer.allocate(1), ByteBuffer.allocate(2));
		clientList.add(connectedClient);

		channel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, connectedClient);
	}

	private void onRead(ConnectedClient client) throws IOException {
		if (client.getConnectingState().connectionType == null) {
			int count = client.getChannel().read(client.getReadBuffer());
			client.getReadBuffer().flip();
			if (count < 0) {
				client.setShouldTerminate();
			} else {
				client.getConnectingState().update(client.getReadBuffer());
				client.getReadBuffer().compact();
			}
		} else {
			client.getHandler().onReadAvailable(client);
		}
	}

	private void onWrite(ConnectedClient client) throws IOException {
		if (client.getConnectingState().connectionType == null || !client.getConnectingState().writeQueue.isEmpty()) {
			ByteBuffer writeBuffer = client.getWriteBuffer();
			ISerializable toWrite = client.getConnectingState().writeQueue.peek();
			int position = writeBuffer.position();
			if (!toWrite.writeToBuffer(writeBuffer)) {
				writeBuffer.position(position);
			} else {
				client.getConnectingState().writeQueue.poll();
			}
			writeBuffer.flip();
			client.getChannel().write(writeBuffer);
			writeBuffer.compact();
		} else {
			client.getHandler().onWriteAvailable(client);
		}
	}

	@Override
	public void run() {
		try {
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
