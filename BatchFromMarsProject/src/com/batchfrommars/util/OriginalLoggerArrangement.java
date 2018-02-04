package com.batchfrommars.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.batchfrommars.file.LogUtil;

public class OriginalLoggerArrangement {

	public Logger arrangeLoggerTask(String logName, String filePath, LogLevel logLevel) {

		return getLoggerWithLevel(logName, filePath, logLevel);

	}

	private Logger getLoggerWithLevel(String logName, String filePath, LogLevel logLevel) {

		switch (logLevel) {

		case FINEST:
			return LogUtil.getLogger(logName, filePath, Logger.getLogger(logName), Level.FINEST);

		case FINE:

			return LogUtil.getLogger(logName, filePath, Logger.getLogger(logName), Level.FINE);

		case INFO:

			return LogUtil.getLogger(logName, filePath, Logger.getLogger(logName), Level.INFO);

		case WARNING:
			return LogUtil.getLogger(logName, filePath, Logger.getLogger(logName), Level.SEVERE);

		default:
			return LogUtil.getLogger(logName, filePath);

		}

	}

}
