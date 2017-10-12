package com.tfowl.shadowblocks.util;

import org.newdawn.slick.util.LogSystem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A {@link java.util.logging.Logger java.util.logging (JUL)} implementation of {@link LogSystem} for slick2d.
 * Simply delegated most logging calls to an underlying {@link java.util.logging.Logger} instance, with some {@link Level} changes.
 */
public class JulLoggingSystem implements LogSystem {

	/*
	* From org.slf4j.bridge.SLF4JBridgeHandler
	 *
	 *  FINEST  -> TRACE
	 *	FINER   -> DEBUG
	 *	FINE    -> DEBUG
	 *	INFO    -> INFO
	 *	WARNING -> WARN
	 *	SEVERE  -> ERROR
	*/

	private static final Level DEBUG = Level.FINE;
	private static final Level ERROR = Level.SEVERE;

	private final Logger logger;

	/**
	 * Creates a new instance with an underlying {@link java.util.logging.Logger} of name <b>com.tfowl.project.util.JulLoggingSystem</b>.
	 */
	public JulLoggingSystem() {
		this.logger = Logger.getLogger(JulLoggingSystem.class.getName());
	}

	@Override
	public void error(String message, Throwable e) {
		logger.log(ERROR, "", e);
	}

	@Override
	public void error(Throwable e) {
		logger.log(ERROR, "", e);
	}

	@Override
	public void error(String message) {
		logger.log(ERROR, message);
	}

	@Override
	public void warn(String message) {
		logger.log(Level.WARNING, message);
	}

	@Override
	public void warn(String message, Throwable e) {
		logger.log(Level.WARNING, message, e);
	}

	@Override
	public void info(String message) {
		logger.log(Level.INFO, message);
	}

	@Override
	public void debug(String message) {
		logger.log(DEBUG, message);
	}
}
