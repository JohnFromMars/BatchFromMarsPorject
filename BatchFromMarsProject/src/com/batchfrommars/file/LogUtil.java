package com.batchfrommars.file;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Log utility for BatchFromMars project
 * 
 * @author user
 *
 */
public class LogUtil {
	private static final SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd");
	private static final String LOG_POSTFIX = ".txt";
	private static final String LOG_DATE = "_D";
	private static final String FRONT = String.format("%1$-130s", " ");
	private static final String NEW_LINE = "\r\n";

	/**
	 * Get a logger
	 * 
	 * @param logName
	 * @param logPath
	 * @return
	 */
	public static Logger getLogger(String logName, String logPath) {
		return getLogger(logName, logPath, Logger.getLogger(logName), Level.INFO

		);
	}

	/**
	 * Get a logger by name, log path and logging level
	 * 
	 * @param logName
	 * @param logPath
	 * @param logger
	 * @param level
	 * @return
	 */
	public static Logger getLogger(String logName, String logPath, Logger logger, Level level) {

		FileHandler fileHandler = null;
		try {

			fileHandler = new FileHandler(getLogFilePath(logName, logPath), true);
			fileHandler.setFormatter(new BatchLogFormatter());
			logger.addHandler(fileHandler);
			logger.setLevel(level);

		} catch (IOException e) {
			logger.severe(e.getMessage());
		} catch (SecurityException e) {
			logger.severe(e.getMessage());
		}

		return logger;
	}

	/**
	 * Return a log file name and path
	 * 
	 * @param logName
	 * @param logPath
	 * @return
	 */
	private static String getLogFilePath(String logName, String logPath) {
		StringBuffer logFilePath = new StringBuffer();
		logFilePath.append(logPath);

		File file = new File(logFilePath.toString());
		if (!file.exists()) {
			file.mkdirs();
		}

		logFilePath.append(File.separatorChar);
		logFilePath.append(logName);
		logFilePath.append(LOG_DATE);
		logFilePath.append(form.format(new Date()));
		logFilePath.append(LOG_POSTFIX);

		return logFilePath.toString();
	}

	/**
	 * Log the exception message in a nice way
	 * 
	 * @param e
	 * @return
	 */
	public static String getExMsg(Exception e) {

		String msg = FRONT + e.getClass() + NEW_LINE + e.getMessage() + NEW_LINE;

		for (int i = 0; i < e.getStackTrace().length; i++) {
			if (i == e.getStackTrace().length) {
				msg = FRONT + msg + e.getStackTrace()[i];

			} else {
				msg = FRONT + msg + e.getStackTrace()[i] + NEW_LINE;
			}
		}

		return msg;

	}

}
