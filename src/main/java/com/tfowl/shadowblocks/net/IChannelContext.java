package com.tfowl.shadowblocks.net;

import java.nio.channels.SocketChannel;

public interface IChannelContext {

	public SocketChannel getChannel();

	public void setShouldTerminate();

	public boolean getShouldTerminate();
}
