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
	public static final String PLAYER_IMAGE_DIRECTORY_NAME = "units/player";
	public static final String TILES_DIRECTORY_NAME = "tiles";

	/* default extensions to add when an extension isn't provided */
	public static final String DEFAULT_IMAGE_EXTENSION = "png";
	public static final String DEFAULT_LEVEL_EXTENSION = "lvl";

	public static class Blocks {
		public static final String ICE_NAME = "ice";
		public static final String STONE_NAME = "stone";
		public static final String TNT_NAME = "tnt";
	}

	public static class Effects {
		public static final String EXPLOSION_NAME = "explosion";
	}

	public static class Tiles {
		public static final String WALL_NAME = "wall";
		public static final String CRACKED_NAME = "cracked";
		public static final String FLOOR_NAME = "floor";
		public static final String TARGET_NAME = "target";
		public static final String SWITCH_NAME = "switch";
		public static final String DOOR_NAME = "door";
	}

	public static class Units {
		public static final String SKELETON_NAME = "skeleton";
		public static final String ROGUE_NAME = "rogue";
		public static final String PLAYER_NAME = "player";
		public static final String MAGE_NAME = "mage";
	}

	/* No initialisation */
	private Resources() {
	}
}
