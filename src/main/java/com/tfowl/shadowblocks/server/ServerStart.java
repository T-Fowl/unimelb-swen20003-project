package com.tfowl.shadowblocks.server;

import java.io.IOException;

public class ServerStart {

	public static void main(String[] args) {
		Server server = new Server("0.0.0.0", 6066);
		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
