package com.tfowl.project.logging.impl;

import com.tfowl.project.logging.Logger;

import java.util.logging.Level;

/**
 * JUL implementation of {@link Logger}.
 * Inspired by https://github.com/qos-ch/slf4j/blob/master/slf4j-jdk14/src/main/java/org/slf4j/jul/JDK14LoggerAdapter.java
 * <p>
 * If simply a wrapper around a {@link java.util.logging.Logger}.
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public class JulLoggerWrapper implements Logger {

	private final String name;
	private final java.util.logging.Logger logger;

	public JulLoggerWrapper(java.util.logging.Logger logger) {
		this.logger = logger;
		this.name = logger.getName();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isLoggable(Level.FINEST);
	}

	@Override
	public void trace(String message) {
		logger.log(Level.FINEST, message);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isLoggable(Level.FINE);
	}

	@Override
	public void debug(String message) {
		logger.log(Level.FINE, message);
	}

	@Override
	public void debug(String format, Object... args) {
		logger.log(Level.FINE, format, args);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isLoggable(Level.INFO);
	}

	@Override
	public void info(String message) {
		logger.log(Level.INFO, message);
	}

	@Override
	public void info(String format, Object... args) {
		logger.log(Level.INFO, format, args);
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isLoggable(Level.WARNING);
	}

	@Override
	public void warn(String message) {
		logger.log(Level.WARNING, message);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}

	@Override
	public void error(String message) {
		logger.log(Level.SEVERE, message);
	}

	@Override
	public void error(String message, Throwable t) {
		logger.log(Level.SEVERE, message, t);
	}
}
