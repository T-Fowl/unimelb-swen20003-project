package com.tfowl.project.logging;

import com.tfowl.project.logging.impl.JulLoggerWrapper;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * LoggerFactory, inspired by that of SLF4J
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public class LoggerFactory {

	private static ConcurrentMap<String, Logger> loadedLoggers = new ConcurrentHashMap<>();

	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	public static Logger getLogger(String name) {
		return loadedLoggers.computeIfAbsent(name, loggerName -> {
			java.util.logging.Logger log = java.util.logging.Logger.getLogger(loggerName);
			return new JulLoggerWrapper(log);
		});
	}
}
