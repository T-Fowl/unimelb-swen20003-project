package com.tfowl.project.logging;

/**
 * Custom Logger interface. Inspired by that of SLF4J.
 * <p>
 * https://github.com/qos-ch/slf4j/blob/master/slf4j-api/src/main/java/org/slf4j/Logger.java
 * <p>
 * Created by Thomas on 7/09/2017.
 */
public interface Logger {

	public String getName();

	public boolean isTraceEnabled();

	public void trace(String message);

	public boolean isDebugEnabled();

	public void debug(String message);

	public void debug(String format, Object... args);

	public boolean isInfoEnabled();

	public void info(String message);

	public void info(String format, Object... args);

	public boolean isWarnEnabled();

	public void warn(String message);

	public boolean isErrorEnabled();

	public void error(String message);

	public void error(String message, Throwable t);
}
