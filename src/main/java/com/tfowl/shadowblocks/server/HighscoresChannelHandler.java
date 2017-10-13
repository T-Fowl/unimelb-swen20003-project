package com.tfowl.shadowblocks.server;

import com.tfowl.shadowblocks.common.HighScore;
import com.tfowl.shadowblocks.common.HighScoreList;
import com.tfowl.shadowblocks.logging.Logger;
import com.tfowl.shadowblocks.logging.LoggerFactory;
import com.tfowl.shadowblocks.net.IChannelContext;
import com.tfowl.shadowblocks.net.IChannelHandler;
import com.tfowl.shadowblocks.net.ISerializable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Queue;

public class HighscoresChannelHandler implements IChannelHandler {

	private static final Logger logger = LoggerFactory.getLogger(HighscoresChannelHandler.class);


	private ByteBuffer readBuffer = ByteBuffer.allocate(2048);
	private ByteBuffer writeBuffer = ByteBuffer.allocate(2048);
	private Queue<ISerializable> toBeWritten = new LinkedList<>();

	@Override
	public void onReadAvailable(IChannelContext context) {
		try {
			int count = context.getChannel().read(readBuffer);
			if (count < 0) {
				logger.info("Terminating connection for {0}.", context.getChannel().getRemoteAddress());
				context.setShouldTerminate();
			} else {
				readBuffer.flip();

				int amount = readBuffer.getInt();
				System.out.println(amount);

				HighScoreList list = new HighScoreList();
				for (int i = 1; i <= amount; i++)
					list.getHighscores().add(new HighScore("Player" + i, i));
				toBeWritten.offer(list);

				//read
				readBuffer.compact();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onWriteAvailable(IChannelContext context) {
		try {
			if (!toBeWritten.isEmpty()) {

				ISerializable serializable = toBeWritten.peek();
				int position = writeBuffer.position();

				if (!serializable.writeToBuffer(writeBuffer)) {
					writeBuffer.position(position);
				} else {
					toBeWritten.poll();
				}
				writeBuffer.flip();
				context.getChannel().write(writeBuffer);
				writeBuffer.compact();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
