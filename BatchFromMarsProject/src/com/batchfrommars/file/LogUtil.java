package com.batchfrommars.file;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtil {
	private static final SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd");
	private static final String LOG_POSTFIX = ".txt";
	private static final String LOG_DATE = "_D";

	public static Logger setLogger(String logName, String logPath, Logger logger) {
		return setLogger(logName, logPath, logger, Level.ALL);
	}

	private static Logger setLogger(String logName, String logPath, Logger logger, Level level) {

		FileHandler fileHandler = null;
		try {
			
			fileHandler = new FileHandler(getLogFilePath(logName,logPath), true);
			fileHandler.setFormatter(new BatchLogFormatter());
			logger.addHandler(fileHandler);
			logger.setLevel(level);
			
		}catch (IOException e) {
			logger.severe(e.getMessage());
		}catch (SecurityException e) {  
            logger.severe(e.getMessage());  
        }

		return logger;
	}

	private static String getLogFilePath(String logName, String logPath) {
		StringBuffer logFilePath = new StringBuffer();
		logFilePath.append(logPath);

		File file = new File(logFilePath.toString());
		if(!file.exists()) {
			file.mkdirs();
		}

		logFilePath.append(File.separatorChar);
		logFilePath.append(logName);
		logFilePath.append(LOG_DATE);
		logFilePath.append(form.format(new Date()));
		logFilePath.append(LOG_POSTFIX);

		return logFilePath.toString();
	}
	


}
