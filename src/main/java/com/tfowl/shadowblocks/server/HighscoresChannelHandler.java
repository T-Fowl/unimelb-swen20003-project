package com.tfowl.shadowblocks.server;

import com.tfowl.shadowblocks.common.HighScore;
import com.tfowl.shadowblocks.common.HighScoreList;
import com.tfowl.shadowblocks.common.Packet;
import com.tfowl.shadowblocks.common.requests.GetHighScoreListRequest;
import com.tfowl.shadowblocks.common.requests.UploadHighScoreRequest;
import com.tfowl.shadowblocks.common.response.UploadHighScoreResponse;
import com.tfowl.shadowblocks.logging.Logger;
import com.tfowl.shadowblocks.logging.LoggerFactory;
import com.tfowl.shadowblocks.net.IChannelContext;
import com.tfowl.shadowblocks.net.IChannelHandler;
import com.tfowl.shadowblocks.reference.PacketIds;

import java.io.IOException;

public class HighscoresChannelHandler implements IChannelHandler {

	private static final Logger logger = LoggerFactory.getLogger(HighscoresChannelHandler.class);

	@Override
	public void onPacket(IChannelContext context, Packet packet) throws IOException {

		if (packet.getId() == PacketIds.HIGHSCORES_LIST_REQUEST) {
			GetHighScoreListRequest request = new GetHighScoreListRequest();
			request.readBuffer(packet.getBody());

			logger.info("Request for {0} hiscores from {1}", request.getAmount(), context.getChannel().getRemoteAddress());

			HighScoreList list = new HighScoreList();
			list.getHighscores().addAll(DatabaseHighscores.getTop(request.getAmount()));

			context.enqueueOutbound(
					Packet.wrap(PacketIds.HIGHSCORES_LIST_RESPONSE, list)
			);

		} else if (packet.getId() == PacketIds.HIGHSCORES_UPLOAD_REQUEST) {
			UploadHighScoreRequest req = new UploadHighScoreRequest();
			req.readBuffer(packet.getBody());

			int rank = DatabaseHighscores.add(req.getHighScore());

			logger.info("Upload of high score ({0},{1}) has rank {2}", req.getHighScore().getPlayerName(), req.getHighScore().getTotalTimeTaken(),
					rank);

			UploadHighScoreResponse res = new UploadHighScoreResponse(rank);
			context.enqueueOutbound(
					Packet.wrap(PacketIds.HIGHSCORES_UPLOAD_RESPONSE, res)
			);

		}
	}
}
