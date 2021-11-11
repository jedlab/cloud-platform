package com.cloud.cache.app;

import org.apache.commons.lang.StringEscapeUtils;

public class CacheUtil {

	private CacheUtil() {
	}

	public static String escapeSql(String input) {
		return StringEscapeUtils.escapeSql(input);
	}

	public static java.sql.Date getCurrentDateSql() {
		return new java.sql.Date(System.currentTimeMillis());
	}

}
