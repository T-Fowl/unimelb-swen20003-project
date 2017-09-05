package com.tfowl.project.util;

import org.newdawn.slick.util.LogSystem;

import java.util.logging.Level;
import java.util.logging.Logger;

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

	private static Level DEBUG = Level.FINE;
	private static Level ERROR = Level.SEVERE;

	private Logger logger;

	public JulLoggingSystem() {
		this(Logger.getLogger(JulLoggingSystem.class.getName()));
	}

	public JulLoggingSystem(Logger logger) {
		this.logger = logger;
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
