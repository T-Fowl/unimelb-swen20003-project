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

	/* If the file reference doesn't have an extension, then add the default */
	private static String fixExtension(String ref, String defaultExtension) {
		/* indexOf returns -1 if the character is not found, not dot => no extension */
		return ref.indexOf('.') < 0 ? ref + '.' + defaultExtension : ref;
	}

	/**
	 * Load an image resource.
	 *
	 * @param name Reference to the image file.
	 * @return An Image loaded from disk.
	 * @throws SlickException If the underlying slick library throws an error.
	 */
	public static Image getImageResource(String name) throws SlickException {
		name = fixExtension(name, Resources.DEFAULT_IMAGE_EXTENSION);
		logger.info("Loading image resource: {0}", name);
		return new Image(Resources.IMAGES_DIRECTORY + "/" + name);
	}

	/**
	 * Load a level resource.
	 *
	 * @param name Reference to the level file.
	 * @return A level loaded from disk.
	 * @throws IOException If the Level parsing method throws an exception.
	 * @see Level#readFromStream(InputStream)
	 */
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

	/* No instancing */
	private ResourceLoader() {
	}
}
