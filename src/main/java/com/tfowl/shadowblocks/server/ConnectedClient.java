package com.tfowl.shadowblocks.server;

import com.tfowl.shadowblocks.net.IChannelContext;
import com.tfowl.shadowblocks.net.IChannelHandler;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ConnectedClient implements IChannelContext {

	private SocketChannel channel;
	private ByteBuffer readBuffer;
	private ByteBuffer writeBuffer;
	private ConnectingState currentState;
	private IChannelHandler handler;
	private boolean shouldTerminate = false;

	public ConnectedClient(SocketChannel channel, ConnectingState currentState, ByteBuffer readBuffer, ByteBuffer writeBuffer) {
		this.currentState = new ConnectingState();
		this.channel = channel;
		this.currentState = currentState;
		this.readBuffer = readBuffer;
		this.writeBuffer = writeBuffer;
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

	public ConnectingState getConnectingState() {
		return currentState;
	}

	public ByteBuffer getReadBuffer() {
		return readBuffer;
	}

	public ByteBuffer getWriteBuffer() {
		return writeBuffer;
	}

	public IChannelHandler getHandler() {
		if (handler == null) {
			switch (currentState.connectionType) {
				case HIGHSCORES:
					handler = new HighscoresChannelHandler();
					break;
				case COMPETE:
					//TODO
					break;
				case UNKNOWN:
					setShouldTerminate();
					handler = new IChannelHandler() {
						@Override
						public void onReadAvailable(IChannelContext context) {

						}

						@Override
						public void onWriteAvailable(IChannelContext context) {

						}
					}; //Simply ignore everything
					break;
			}
		}
		return handler;
	}
}
