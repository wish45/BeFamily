package com.befam.api.domain.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {

	private final static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

	public static String toDateTimeStringFrom(Date date) {
		return DATETIME_FORMATTER.format(date.toInstant().atZone(ZoneId.systemDefault()));
	}

}
