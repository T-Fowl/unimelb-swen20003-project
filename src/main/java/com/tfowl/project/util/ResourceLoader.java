package com.tfowl.project.util;

import com.tfowl.project.level.Level;
import com.tfowl.project.logging.Logger;
import com.tfowl.project.logging.LoggerFactory;
import com.tfowl.project.reference.Resources;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class used to loading resources.
 * <p>
 * Created by Thomas on 5/09/2017.
 */
public class ResourceLoader {

	private static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);

	private static String fixExtension(String ref, String defaultExtension) {
		return ref.indexOf('.') < 0 ? ref + '.' + defaultExtension : ref;
	}

	public static Image getImageResource(String name) throws SlickException {
		name = fixExtension(name, Resources.DEFAULT_IMAGE_EXTENSION);
		logger.info("Loading image resource: {0}", name);
		return new Image(Resources.IMAGES_DIRECTORY + "/" + name);
	}

	public static Level getLevelResource(String name) throws IOException {
		name = fixExtension(name, Resources.DEFAULT_LEVEL_EXTENSION);
		logger.info("Loading level resource: {0}", name);
		return Level.readFromStream(getResourceAsStream(Resources.LEVELS_DIRECTORY + "/" + name));
	}

	/**
	 * Loads the specified resource as a stream. Delegates to the implementation
	 * in the slick library.
	 *
	 * @param ref The reference to the resource to retrieve
	 * @return A stream from which the resource can be read
	 * @see org.newdawn.slick.util.ResourceLoader#getResourceAsStream(String)
	 */
	public static InputStream getResourceAsStream(String ref) {
		logger.info("Loading resource: {0}", ref);
		return org.newdawn.slick.util.ResourceLoader.getResourceAsStream(ref);
	}

	public static boolean resourceExists(String ref) {
		return org.newdawn.slick.util.ResourceLoader.resourceExists(ref);
	}

	/* No instancing */
	private ResourceLoader() {
	}
}
