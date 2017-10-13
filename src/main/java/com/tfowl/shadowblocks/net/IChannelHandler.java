package com.tfowl.shadowblocks.net;


public interface IChannelHandler {

	public void onReadAvailable(IChannelContext context);

	public void onWriteAvailable(IChannelContext context);
}
