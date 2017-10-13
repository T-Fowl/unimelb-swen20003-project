package com.tfowl.shadowblocks.server;

import com.tfowl.shadowblocks.common.response.ConnectionSelectResponse;
import com.tfowl.shadowblocks.common.ConnectionType;
import com.tfowl.shadowblocks.common.Packet;
import com.tfowl.shadowblocks.logging.Logger;
import com.tfowl.shadowblocks.logging.LoggerFactory;
import com.tfowl.shadowblocks.net.IChannelContext;
import com.tfowl.shadowblocks.net.IChannelHandler;
import com.tfowl.shadowblocks.reference.PacketIds;

import java.io.IOException;

public class ConnectionSelectHandler implements IChannelHandler {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionSelectHandler.class);

	@Override
	public void onPacket(IChannelContext context, Packet packet) throws IOException {

		if (packet.getId() == PacketIds.CONNECTION_SELECT_REQUEST) {

			ConnectionType connectionType = ConnectionType.fromBuffer(packet.getBody());
			context.setConnectionType(connectionType);

			logger.info("Connection type {0} selected for {1}", connectionType, context.getChannel().getRemoteAddress());

			switch (connectionType) {
				case UNKNOWN:
					context.enqueueOutbound(
							Packet.wrap(PacketIds.CONNECTION_SELECT_RESPONSE,
									new ConnectionSelectResponse(false, (byte) 100)));
					context.setShouldTerminate();
					break;
				case HIGHSCORES:
					context.enqueueOutbound(
							Packet.wrap(PacketIds.CONNECTION_SELECT_RESPONSE,
									new ConnectionSelectResponse(true, (byte) 0)));
					context.setHandler(new HighscoresChannelHandler());
					break;
				case COMPETE:
					context.enqueueOutbound(
							Packet.wrap(PacketIds.CONNECTION_SELECT_RESPONSE,
									new ConnectionSelectResponse(false, (byte) 120)));
					context.setShouldTerminate();
					break;
			}
		}
	}
}
