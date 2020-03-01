package it.vitalegi.globalworkinghours.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {

	public static final DateTimeFormatter DATE_TIME_LONG_FORMATTER = DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm",
			Locale.US);

	public static final DateTimeFormatter DATE_LONG_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd E", Locale.US);

	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	public static String formatDateTimeLong(ZonedDateTime time) {
		return time.format(DATE_TIME_LONG_FORMATTER);
	}

	public static String formatTime(LocalTime time) {
		return time.format(TIME_FORMATTER);
	}

	public static String formatDateLong(LocalDate date) {
		return date.format(DATE_LONG_FORMATTER);
	}
}
