package com.tfowl.project.util;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Thomas on 5/09/2017.
 */
public class ResourceLoader {

	public static URL getResource(String ref) {
		return org.newdawn.slick.util.ResourceLoader.getResource(ref);
	}

	public static InputStream getResourceAsStream(String ref) {
		return org.newdawn.slick.util.ResourceLoader.getResourceAsStream(ref);
	}

	public static boolean resourceExists(String ref) {
		return org.newdawn.slick.util.ResourceLoader.resourceExists(ref);
	}

	private ResourceLoader() {
	}
}
