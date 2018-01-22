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
import com.batchfrommars.util.ExecuteUtil;
import com.batchfrommars.util.FilterUtil;
import com.batchfrommars.util.LogeLevel;
import com.batchfrommars.util.LoggerUtil;
import com.batchfrommars.util.MapUtil;
import com.batchfrommars.util.OriginCountArrangement;
import com.batchfrommars.util.OriginExecuteArrangement;
import com.batchfrommars.util.OriginLoggerArrangement;
import com.batchfrommars.util.OriginSumArrangement;
import com.batchfrommars.util.OringinFilterArrangement;
import com.batchfrommars.util.OringinMapArangement;
import com.batchfrommars.util.OringinSortArrangement;
import com.batchfrommars.util.SortUtil;
import com.batchfrommars.util.SumUtil;

public abstract class BatchController {

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
	protected FileInformation input;
	protected FileInformation output;

	private SortUtil sortUtil;
	private LoggerUtil loggerUtil;

	public BatchController() {
		components = new ArrayList<>();
		tempFile = new ArrayList<>();
	}

	public BatchController filter(Predicate<String> predicate) {
		FilterUtil mapUtil = new OringinFilterArrangement();
		mapUtil.mapArrangement(components, logger, predicate);

		return this;
	}

	public BatchController sort(String sortText) {
		sortUtil = new OringinSortArrangement();
		sortUtil.sortArrangement(components, logger, sortText);

		return this;
	}

	public BatchController map(Function<String, String> function) {
		MapUtil mapUtil = new OringinMapArangement();
		mapUtil.mapArrangement(components, logger, function);

		return this;
	}

	public BatchController logger(String logName, String filePath, LogeLevel level) {
		loggerUtil = new OriginLoggerArrangement();
		this.logger = loggerUtil.loggerArrangement(logName, filePath, level);
		return this;
	}

	public BatchController logger(Logger logger) {
		this.logger = logger;
		return this;
	}

	public BatchController input(String filePath, String encodeing)
			throws UnsupportedEncodingException, FileNotFoundException {

		FileInformation input = new PhysicalFile(PhysicalFile.INPUT, filePath, encodeing, false);
		this.input = input;

		return this;
	}

	public BatchController input(FileInformation fileInformation) {
		this.input = fileInformation;
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
		ExecuteUtil executeUtil = new OriginExecuteArrangement();
		executeUtil.executeArrangement(input, output, logger, components, header, footer);
	}

	public BigDecimal sum(Function<String, String> function) throws Exception {
		SumUtil originSumArranement = new OriginSumArrangement();
		return originSumArranement.arrangeSum(function, components, logger, input, output, header, footer);
	}

	public Integer count() throws Exception {
		OriginCountArrangement count = new OriginCountArrangement();
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
