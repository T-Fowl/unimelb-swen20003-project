package com.tfowl.shadowblocks.server;

import com.tfowl.shadowblocks.logging.Logger;
import com.tfowl.shadowblocks.logging.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(Server.class);

	private InetSocketAddress address;
	private ServerSocketChannel ssc;
	private Selector selector;

	private List<Client> clientList = new ArrayList<>();

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
							Client client = (Client) key.attachment();
							try {
								client.onRead();
							} catch (Throwable e) {
								e.printStackTrace();
								disconnectClient(client);
							}
						} else if (key.isWritable()) {
							Client client = (Client) key.attachment();
							try {
								client.onWrite();
							} catch (Throwable e) {
								e.printStackTrace();
								disconnectClient(client);
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

	public void disconnectClient(Client client) {
		clientList.removeIf(c -> c == client);
		client.getChannel().keyFor(selector).cancel();
		try {
			logger.info("Client disconnecting");
			client.getChannel().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void onAccept(SocketChannel channel) throws IOException {
		channel.configureBlocking(false);

		Client client = new Client(channel, this);
		clientList.add(client);

		channel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, client);
		logger.info("Accepted connection from {0}", channel.getRemoteAddress());
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
