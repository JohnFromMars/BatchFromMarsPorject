package com.batchfrommars.controller;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.PhysicalFile;
import com.batchfrommars.util.LogeLevel;
import com.batchfrommars.util.LoggerUtil;
import com.batchfrommars.util.OriginalCountArrangement;
import com.batchfrommars.util.OriginalExecuteArrangement;
import com.batchfrommars.util.OriginalFilterArrangement;
import com.batchfrommars.util.OriginalLoggerArrangement;
import com.batchfrommars.util.OriginalMapArangement;
import com.batchfrommars.util.OriginalSortArrangement;
import com.batchfrommars.util.OriginalSumArrangement;
import com.batchfrommars.util.SortUtil;

public class BatchController {

	// list of components to execute
	protected List<ComponentII> components;

	// logger
	protected Logger logger;

	// list of file information to connect to different components
	/*
	 * protected List<Filermation> inputInformations = new ArrayList<>();
	 * protected List<FileInformation> outputInformations = new ArrayList<>();
	 */

	// header and footer
	protected String header;
	protected String footer;

	// file area
	protected List<FileInformation> tempFile;
	protected List<FileInformation> input;
	protected FileInformation output;

	private SortUtil sortUtil;
	private LoggerUtil loggerUtil;

	public BatchController() {
		components = new ArrayList<>();
		tempFile = new ArrayList<>();
		input = new ArrayList<>();
	}

	public BatchController filter(Predicate<String> predicate) {
		OriginalFilterArrangement mapUtil = new OriginalFilterArrangement();
		mapUtil.mapArrangement(components, logger, predicate);

		return this;
	}

	public BatchController sort(String sortText) {
		sortUtil = new OriginalSortArrangement();
		sortUtil.sortArrangement(components, logger, sortText);

		return this;
	}

	public BatchController map(Function<String, String> function) {
		OriginalMapArangement mapUtil = new OriginalMapArangement();
		mapUtil.mapArrangement(components, logger, function);

		return this;
	}

	public BatchController logger(String logName, String filePath, LogeLevel level) {
		loggerUtil = new OriginalLoggerArrangement();
		this.logger = loggerUtil.loggerArrangement(logName, filePath, level);
		return this;
	}

	public BatchController logger(Logger logger) {
		this.logger = logger;
		return this;
	}

	public BatchController input(String filePath, String encodeing)
			throws UnsupportedEncodingException, FileNotFoundException {

		FileInformation fileInformation = new PhysicalFile(PhysicalFile.INPUT, filePath, encodeing, false);
		this.input.add(fileInformation);

		return this;
	}

	public BatchController input(FileInformation fileInformation) {
		this.input.add(fileInformation);
		return this;
	}

	public BatchController output(String filePath, String encodeing, boolean appdening)
			throws UnsupportedEncodingException, FileNotFoundException {

		FileInformation output = new PhysicalFile(PhysicalFile.OUTPUT, filePath, encodeing, appdening);
		this.output = output;
		return this;
	}

	public BatchController output(FileInformation fileInformation) {
		this.output = fileInformation;
		return this;
	}

	public void execute() throws Exception {
		OriginalExecuteArrangement executeUtil = new OriginalExecuteArrangement();
		executeUtil.executeArrangement(input, output, logger, components, header, footer);
	}

	public BigDecimal sum(Function<String, String> function) throws Exception {
		OriginalSumArrangement originSumArranement = new OriginalSumArrangement();
		return originSumArranement.arrangeSum(function, components, logger, input, output, header, footer);
	}

	public Integer count() throws Exception {
		OriginalCountArrangement count = new OriginalCountArrangement();
		return count.arrangeCount(components, logger, input, output, header, footer);
	}

	public BatchController header(String header) {
		this.header = header;
		return this;
	}

	public BatchController footer(String footer) {
		this.footer = footer;
		return this;
	}

}
