package com.tfowl.shadowblocks.level;

import java.util.ArrayList;
import java.util.List;

/**
 * A location is a part of a level with some objects. This class allows for multiple objects to be
 * at one position in the level.
 *
 * Created by Thomas on 6/09/2017.
 */
public class Location {

	private final List<String> objects;

	Location() {
		objects = new ArrayList<>();
	}

	private int getObjectCount() {
		return objects.size();
	}

	void addObjectAtTop(String object) {
		addObject(object, getObjectCount());
	}

	private void addObject(String object, int index) {
		objects.add(index, object);
	}

	public List<String> getObjects() {
		return objects;
	}
}
