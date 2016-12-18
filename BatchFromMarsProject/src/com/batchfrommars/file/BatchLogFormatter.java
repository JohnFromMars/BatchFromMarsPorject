package com.batchfrommars.file;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class BatchLogFormatter extends Formatter {
	private final SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final String OOUTE = " : ";

	@Override
	public String format(LogRecord record) {
		String message = form.format(new Date()) + OOUTE
				+ String.format("%1$-30s", "[" + record.getSourceMethodName() + "] ")
				+ String.format("%1$-10s", "[" + record.getLevel() + "] ") + "- " + record.getMessage() + "\r\n";

		return message;
	}

}
