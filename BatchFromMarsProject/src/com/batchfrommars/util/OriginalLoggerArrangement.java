package com.batchfrommars.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.batchfrommars.file.LogUtil;

public class OriginalLoggerArrangement {

	public Logger arrangeLoggerTask(String logName, String filePath, Level logLevel) {

		return getLoggerWithLevel(logName, filePath, logLevel);

	}

	private Logger getLoggerWithLevel(String logName, String filePath, Level logLevel) {

		return LogUtil.getLogger(logName, filePath, Logger.getLogger(logName), logLevel);

	}

}
