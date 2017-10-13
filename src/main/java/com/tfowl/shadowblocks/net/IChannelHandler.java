package com.tfowl.shadowblocks.net;


import com.tfowl.shadowblocks.common.Packet;

import java.io.IOException;

public interface IChannelHandler {

	public void onPacket(IChannelContext context, Packet packet) throws IOException;
}
