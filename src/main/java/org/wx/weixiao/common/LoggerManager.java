package org.wx.weixiao.common;

import org.apache.log4j.Logger;

public class LoggerManager {
	private static boolean HIDDEN = false;

	public static void info(Logger logger, String content) {
		if (!HIDDEN) {
			logger.info(content);
		}
	}

	public static void error(Logger logger, String content) {
		if (!HIDDEN) {
			logger.error(content);
		}
	}
}
