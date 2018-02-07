package com.batchfrommars.controller;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.PhysicalFile;
import com.batchfrommars.util.LogLevel;
import com.batchfrommars.util.OriginalCompareArrangement;
import com.batchfrommars.util.OriginalCountArrangement;
import com.batchfrommars.util.OriginalExecuteArrangement;
import com.batchfrommars.util.OriginalFilterArrangement;
import com.batchfrommars.util.OriginalLoggerArrangement;
import com.batchfrommars.util.OriginalMapArangement;
import com.batchfrommars.util.OriginalMergeArrangement;
import com.batchfrommars.util.OriginalSortArrangement;
import com.batchfrommars.util.OriginalSumArrangement;
import com.batchfrommars.util.Task;

public class BatchController {

	// list of components to execute
	protected List<Task> tasks;
	// list of hidden component
	protected List<Task> hiddenTasks;

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
	protected List<FileInformation> tempFiles;
	protected List<FileInformation> inputs;
	protected FileInformation output;

	public BatchController() {
		tasks = new ArrayList<>();
		hiddenTasks = new ArrayList<>();
		tempFiles = new ArrayList<>();
		inputs = new ArrayList<>();
		logger = Logger.getAnonymousLogger();
	}

	public BatchController filter(Predicate<String> predicate) {
		OriginalFilterArrangement mapUtil = new OriginalFilterArrangement();
		mapUtil.arrangeFilterTask(tasks, logger, predicate);

		return this;
	}

	public BatchController sort(String sortText) {
		OriginalSortArrangement sortUtil = new OriginalSortArrangement();
		sortUtil.arrangeSortTask(tasks, logger, sortText);

		return this;
	}

	public BatchController map(Function<String, String> function) {
		OriginalMapArangement mapUtil = new OriginalMapArangement();
		mapUtil.arrangeMapTask(tasks, logger, function);

		return this;
	}

	public BatchController logger(String logName, String filePath, LogLevel level) {
		OriginalLoggerArrangement loggerArrangement = new OriginalLoggerArrangement();
		this.logger = loggerArrangement.arrangeLoggerTask(logName, filePath, level);
		return this;
	}

	public BatchController logger(Logger logger) {
		this.logger = logger;
		return this;
	}

	public BatchController input(String filePath, String encodeing)
			throws UnsupportedEncodingException, FileNotFoundException {

		FileInformation fileInformation = new PhysicalFile(PhysicalFile.INPUT, filePath, encodeing, false);
		this.inputs.add(fileInformation);

		return this;
	}

	public BatchController input(FileInformation fileInformation) {
		this.inputs.add(fileInformation);
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
		executeUtil.arrangeExecuteTask(inputs, output, logger, tasks, hiddenTasks, header, footer);
	}

	public BigDecimal sum(Function<String, String> function) throws Exception {
		OriginalSumArrangement originSumArranement = new OriginalSumArrangement();
		return originSumArranement.arrangeSumTask(function, tasks, hiddenTasks, logger, inputs, output, header, footer);
	}

	public Integer count() throws Exception {
		OriginalCountArrangement count = new OriginalCountArrangement();
		return count.arrangeCountTask(tasks, hiddenTasks, logger, inputs, output, header, footer);
	}

	public BatchController header(String header) {
		this.header = header;
		return this;
	}

	public BatchController footer(String footer) {
		this.footer = footer;
		return this;
	}

	public BatchController compare(Function<String, String> firstInputKey, Function<String, String> secondInputKey,
			BiFunction<String, String, String> resultForm) throws Exception {
		OriginalCompareArrangement compareArrangement = new OriginalCompareArrangement();
		compareArrangement.arrangeCompareTask(tasks, hiddenTasks, logger, firstInputKey, secondInputKey, resultForm);
		return this;
	}

	public BatchController merge() {
		OriginalMergeArrangement originalMergeArrangement = new OriginalMergeArrangement();
		originalMergeArrangement.arrangeMergeTask(tasks, logger);
		return this;
	}

}
