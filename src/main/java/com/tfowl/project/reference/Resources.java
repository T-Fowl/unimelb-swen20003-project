package com.tfowl.project.reference;

/**
 * A set of resource-related constants.
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public class Resources {

	/* Used to configure the loggers */
	public static final String DEFAULT_LOGGING_CONFIG = "logging.properties";

	/* Top-level resource directories */
	public static final String RESOURCES_ROOT = ".";
	public static final String IMAGES_DIRECTORY = RESOURCES_ROOT + "/images";
	public static final String LEVELS_DIRECTORY = RESOURCES_ROOT + "/levels";

	/* sub-directories */
	public static final String PLAYER_IMAGE_DIRECTORY_NAME = "player";
	public static final String TILES_DIRECTORY_NAME = "tiles";

	/* default extensions to add when an extension isn't provided */
	public static final String DEFAULT_IMAGE_EXTENSION = "png";
	public static final String DEFAULT_LEVEL_EXTENSION = "lvl";

	/* No initialisation */
	private Resources() {
	}
}
