package com.tfowl.shadowblocks.server;

import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class InitialisationResponse implements ISerializable {

	private boolean isOkay;
	private byte error;

	public InitialisationResponse() {
	}

	public InitialisationResponse(boolean isOkay, byte error) {
		this.isOkay = isOkay;
		this.error = error;
	}

	public static InitialisationResponse okay() {
		InitialisationResponse response = new InitialisationResponse();
		response.isOkay = true;
		return response;
	}

	public static InitialisationResponse error(byte error) {
		InitialisationResponse response = new InitialisationResponse();
		response.isOkay = false;
		response.error = error;
		return response;
	}

	@Override
	public boolean writeToBuffer(ByteBuffer buffer) {
		try {
			buffer.put(isOkay ? (byte) 0 : (byte) 1);
			buffer.put(error);
			return true;
		} catch (BufferOverflowException e) {
			return false;
		}
	}

	@Override
	public boolean readFromBuffer(ByteBuffer buffer) {
		try {
			this.isOkay = buffer.get() == 0;
			this.error = buffer.get();
			return true;
		} catch (BufferUnderflowException e) {
			return false;
		}
	}
}
