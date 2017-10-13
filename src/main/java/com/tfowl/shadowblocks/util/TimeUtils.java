package com.tfowl.shadowblocks.util;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

	/* Format to use when there are at lest 1 hour(s) passed */
	private static final String DURATION_HOURS_FORMAT = "%02d:%02d:%02d.%d";
	/* Format to use when there are at least 1 minute(s) passed */
	private static final String DURATION_MINUTES_FORMAT = "%02d:%02d.%d";
	/* Format to use when there are less than 1 minute(s) passed */
	private static final String DURATION_SECONDS_FORMAT = "%02d.%d";

	/**
	 * Formats the given duration into a nice timestamp.
	 * <p>
	 * The following formats are tried in this order:
	 * If the duration is at least 1 hour, then the format is %02d:%02d:%02d.%d
	 * If the duration is at least 1 minute, then the format is %02d:%02d.%d
	 * Otherwise the format is %02d.%d
	 * <p>
	 * All of the above formats will receive the hours, minutes, seconds and decimals in that order.
	 *
	 * @param durationInMilliseconds Duration to format, in milliseconds
	 * @return A formatted duration string
	 */
	public static String formatDuration(long durationInMilliseconds) {
		long decimals = (durationInMilliseconds % 1000) / 100;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(durationInMilliseconds) % 60;
		long minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMilliseconds) % 60;
		long hours = TimeUnit.MILLISECONDS.toHours(durationInMilliseconds);

		if (hours > 0) {
			return String.format(DURATION_HOURS_FORMAT, hours, minutes, seconds, decimals);
		}
		if (minutes > 0) {
			return String.format(DURATION_MINUTES_FORMAT, minutes, seconds, decimals);
		}
		return String.format(DURATION_SECONDS_FORMAT, seconds, decimals);
	}
}
