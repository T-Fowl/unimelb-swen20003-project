package com.tfowl.shadowblocks.logging;

/**
 * Custom Logger interface. Inspired by that of SLF4J.
 * <p>
 * https://github.com/qos-ch/slf4j/blob/master/slf4j-api/src/main/java/org/slf4j/Logger.java
 * <p>
 * Not all of the methods are implemented, just the basics of each and then more were added as they were needed.
 * <p>
 * Created by Thomas on 7/09/2017.
 */
@SuppressWarnings({"unused"})
public interface Logger {

	/* All methods self-explanatory */

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
