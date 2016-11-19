package com.batchfrommars.file;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class BatchLogFormatter extends Formatter {
	private final SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private final String OOUTE = " : ";

	@Override
	public String format(LogRecord record) {
		String message = form.format(new Date()) + OOUTE + "[" + record.getSourceMethodName() + "] ["
				+ record.getLevel() + "] " + record.getMessage() + "\r\n";

		return message;
	}

}
