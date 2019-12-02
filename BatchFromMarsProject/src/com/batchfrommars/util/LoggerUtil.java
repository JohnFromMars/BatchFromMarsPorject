package com.batchfrommars.util;

import java.util.logging.Logger;

/**
 * Log task for the BatchController
 * 
 * @author user
 *
 */
public interface LoggerUtil {
	/**
	 * Set the logger for the controller
	 * 
	 * @param logName
	 * @param filePath
	 * @param logLevel
	 * @return
	 */
	Logger loggerArrangement(String logName, String filePath, LogeLevel logLevel);
}
