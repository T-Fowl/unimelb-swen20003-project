package com.tfowl.shadowblocks.net;

import com.tfowl.shadowblocks.common.ConnectionType;
import com.tfowl.shadowblocks.common.Packet;
import com.tfowl.shadowblocks.server.Server;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public interface IChannelContext {

	public SocketChannel getChannel();

	public void setShouldTerminate();

	public boolean getShouldTerminate();

	public void enqueueOutbound(Packet packet);

	public void setHandler(IChannelHandler handler);

	public IChannelHandler getHandler();

	public void setConnectionType(ConnectionType type);
}
