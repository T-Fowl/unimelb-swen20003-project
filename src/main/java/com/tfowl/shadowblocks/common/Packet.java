package com.tfowl.shadowblocks.common;

import com.tfowl.shadowblocks.net.ISerializable;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Packet {

	private int id;
	private int length; //of the body
	private ByteBuffer body;

	public Packet() {
	}

	public Packet(int id, int length, ByteBuffer body) {
		this.id = id;
		this.length = length;
		this.body = body;
	}

	public int getId() {
		return id;
	}

	public int getLength() {
		return length;
	}

	public ByteBuffer getBody() {
		return body;
	}

	public boolean tryWrite(ByteBuffer buffer) {
		int position = buffer.position();
		try {
			buffer.putInt(id);
			buffer.putInt(length);
			body.rewind();
			buffer.put(body);
			return true;
		} catch (BufferOverflowException e) {
			buffer.position(position);
			return false;
		}
	}

	public boolean tryRead(ByteBuffer buffer) {
		int position = buffer.position();
		try {
			this.id = buffer.getInt();
			this.length = buffer.getInt();
			byte[] data = new byte[length];
			buffer.get(data);
			this.body = ByteBuffer.wrap(data);
			return true;
		} catch (BufferUnderflowException e) {
			buffer.position(position);
			return false;
		}
	}


	public static Packet wrap(int id, ISerializable serializable) {
		Packet p = new Packet();
		p.id = id;
		p.body = serializable.getBytes();
		p.length = p.body.limit();
		return p;
	}
}
