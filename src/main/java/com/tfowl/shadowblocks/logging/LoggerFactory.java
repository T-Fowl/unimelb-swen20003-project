package com.tfowl.shadowblocks.logging;

import com.tfowl.shadowblocks.logging.impl.JulLoggerWrapper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * LoggerFactory, inspired by that of SLF4J
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public class LoggerFactory {

	/* All the created loggers */
	private static final ConcurrentMap<String, Logger> loadedLoggers = new ConcurrentHashMap<>();

	/**
	 * Get or create a {@link Logger} with the fully-qualified-name of the passed class.
	 *
	 * @param clazz Class with the name to be the loggers.
	 * @return A {@link Logger} with the specified name.
	 * @see #getLogger(String)
	 */
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	/**
	 * Get or create a {@link Logger} with the specified name.
	 *
	 * @param name Name of the logger.
	 * @return A {@link Logger} with the specified name.
	 */
	public static Logger getLogger(String name) {
		return loadedLoggers.computeIfAbsent(name, loggerName -> {
			java.util.logging.Logger log = java.util.logging.Logger.getLogger(loggerName);
			return new JulLoggerWrapper(log);
		});
	}
}
