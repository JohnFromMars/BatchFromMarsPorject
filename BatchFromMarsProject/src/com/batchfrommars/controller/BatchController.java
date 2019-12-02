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

/**
 * Main controller of the batch program. It can be set to have multiple tasks,
 * input, out put and logging level
 * 
 * @author user
 *
 */
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

	// Utility area
	private SortUtil sortUtil;
	private LoggerUtil loggerUtil;

	public BatchController() {
		components = new ArrayList<>();
		tempFile = new ArrayList<>();
	}

	/**
	 * This method add a filter task into the controller based on the given
	 * lamba function
	 * 
	 * @param predicate
	 * @return
	 */
	public BatchController filter(Predicate<String> predicate) {
		FilterUtil mapUtil = new OringinFilterArrangement();
		mapUtil.mapArrangement(components, logger, predicate);

		return this;
	}

	/**
	 * This method adds a sort task into the controller based on the given sort
	 * text
	 * 
	 * @param sortText
	 * @return
	 */
	public BatchController sort(String sortText) {
		sortUtil = new OringinSortArrangement();
		sortUtil.sortArrangement(components, logger, sortText);

		return this;
	}

	/**
	 * This method ads a map task into the controller base on the given lamba
	 * function
	 * 
	 * @param function
	 * @return
	 */
	public BatchController map(Function<String, String> function) {
		MapUtil mapUtil = new OringinMapArangement();
		mapUtil.mapArrangement(components, logger, function);

		return this;
	}

	/**
	 * This method add the logger into the controller
	 * 
	 * @param logName
	 * @param filePath
	 * @param level
	 * @return
	 */
	public BatchController logger(String logName, String filePath, LogeLevel level) {
		loggerUtil = new OriginLoggerArrangement();
		this.logger = loggerUtil.loggerArrangement(logName, filePath, level);
		return this;
	}

	/**
	 * This method set the logger
	 * 
	 * @param logger
	 * @return
	 */
	public BatchController logger(Logger logger) {
		this.logger = logger;
		return this;
	}

	/**
	 * this method add a input into the controller
	 * 
	 * @param filePath
	 * @param encodeing
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public BatchController input(String filePath, String encodeing)
			throws UnsupportedEncodingException, FileNotFoundException {

		FileInformation input = new PhysicalFile(PhysicalFile.INPUT, filePath, encodeing, false);
		this.input = input;

		return this;
	}

	/**
	 * This method add an input information into the controller
	 * 
	 * @param fileInformation
	 * @return
	 */
	public BatchController input(FileInformation fileInformation) {
		this.input = fileInformation;
		return this;
	}

	/**
	 * This method adds a output into the controller.
	 * 
	 * @param filePath
	 * @param encodeing
	 * @param appdening
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public BatchController output(String filePath, String encodeing, boolean appdening)
			throws UnsupportedEncodingException, FileNotFoundException {

		FileInformation output = new PhysicalFile(PhysicalFile.OUTPUT, filePath, encodeing, appdening);
		this.output = output;
		return this;
	}

	/**
	 * This method adds a file information into output of the controller
	 * 
	 * @param fileInformation
	 * @return
	 */
	public BatchController output(FileInformation fileInformation) {
		this.output = fileInformation;
		return this;
	}

	/**
	 * This method starts the controller, which executes all the tasks of the
	 * controller
	 * 
	 * @throws Exception
	 */
	public void execute() throws Exception {
		ExecuteUtil executeUtil = new OriginExecuteArrangement();
		executeUtil.executeArrangement(input, output, logger, components, header, footer);
	}

	/**
	 * This method not only executes the controller but also returns the sum of
	 * certain area of data based on the given lamba function
	 * 
	 * @param function
	 * @return
	 * @throws Exception
	 */
	public BigDecimal sum(Function<String, String> function) throws Exception {
		SumUtil originSumArranement = new OriginSumArrangement();
		return originSumArranement.arrangeSum(function, components, logger, input, output, header, footer);
	}

	/**
	 * This method not only executes the controller, but also return the count
	 * number of data
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer count() throws Exception {
		OriginCountArrangement count = new OriginCountArrangement();
		return count.arrangeCount(components, logger, input, output, header, footer);
	}

	/**
	 * Set the header of the controller
	 * 
	 * @param header
	 * @return
	 */
	public BatchController header(String header) {
		this.header = header;
		return this;
	}

	/**
	 * Set the footer of the controller
	 * 
	 * @param footer
	 * @return
	 */
	public BatchController footer(String footer) {
		this.footer = footer;
		return this;
	}

}
