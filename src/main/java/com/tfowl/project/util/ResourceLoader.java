package com.tfowl.project.util;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Thomas on 5/09/2017.
 */
public class ResourceLoader {

	private static final String IMAGE_RESOURCE_DIR = "/images";
	private static final String DEFAULT_IMAGE_EXRENSION = ".png";
	private static final String LEVEL_RESOURCE_DIR = "/levels";

	public static Image getImageResource(String ref) {
		if (ref.indexOf('.') < 0) {
			ref = ref + DEFAULT_IMAGE_EXRENSION;
		}
		try {
			return new Image(getResourceAsStream(IMAGE_RESOURCE_DIR + "/" + ref), ref, false);
		} catch (SlickException e) {
			return null;
		}
	}

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
