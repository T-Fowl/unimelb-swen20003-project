package com.tfowl.shadowblocks.client;

import com.tfowl.shadowblocks.common.ConnectionType;
import com.tfowl.shadowblocks.common.HighScore;
import com.tfowl.shadowblocks.common.HighScoreList;
import com.tfowl.shadowblocks.common.Packet;
import com.tfowl.shadowblocks.common.requests.GetHighScoreListRequest;
import com.tfowl.shadowblocks.common.requests.UploadHighScoreRequest;
import com.tfowl.shadowblocks.common.response.GetHighScoreListResponse;
import com.tfowl.shadowblocks.common.response.UploadHighScoreResponse;
import com.tfowl.shadowblocks.reference.PacketIds;

import java.util.concurrent.CompletableFuture;

public class HighScoresClient extends Client {

	public HighScoresClient(String host, int port) {
		super(host, port, ConnectionType.HIGHSCORES);
	}

	@Override
	protected void onPacket(Packet packet) {
		if (packet.getId() == PacketIds.HIGHSCORES_LIST_RESPONSE) {
			if (null != futureList) {
				GetHighScoreListResponse res = new GetHighScoreListResponse();
				res.readBuffer(packet.getBody());

				futureList.complete(res.getHighscores());
				futureList = null;
			}
		}
		if (packet.getId() == PacketIds.HIGHSCORES_UPLOAD_RESPONSE) {
			if (null != futureUpload) {
				UploadHighScoreResponse res = new UploadHighScoreResponse();
				res.readBuffer(packet.getBody());

				futureUpload.complete(res.getRank());
				futureUpload = null;
			}
		}
	}

	@Override
	protected void onConnected() {
		System.out.println("Connected");
	}

	private CompletableFuture<Integer> futureUpload = null;
	private CompletableFuture<HighScoreList> futureList = null;

	public CompletableFuture<Integer> upload(HighScore score) {
		if (futureUpload == null) {
			futureUpload = new CompletableFuture<>();

			UploadHighScoreRequest req = new UploadHighScoreRequest(score);
			Packet p = Packet.wrap(PacketIds.HIGHSCORES_UPLOAD_REQUEST, req);
			sendPacket(p);
		}
		return futureUpload;
	}

	public CompletableFuture<HighScoreList> getHighscores(int amount) {
		if (null == futureList) {
			futureList = new CompletableFuture<>();

			GetHighScoreListRequest req = new GetHighScoreListRequest(amount);
			Packet p = Packet.wrap(PacketIds.HIGHSCORES_LIST_REQUEST, req);
			sendPacket(p);
		}

		return futureList;
	}
}