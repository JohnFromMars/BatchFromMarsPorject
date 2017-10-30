package com.batchfrommars.controller;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.batchfrommars.component.ComponentII;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.PhysicalFile;
import com.sun.istack.internal.logging.Logger;

public abstract class BatchController {

	// list of components to execute
	protected List<ComponentII> components = new ArrayList<>();

	// logger
	protected Logger logger;

	// list of file information to connect to different components
	/*
	 * protected List<FileInformation> inputInformations = new ArrayList<>();
	 * protected List<FileInformation> outputInformations = new ArrayList<>();
	 */

	protected FileInformation input;
	protected FileInformation output;

	public BatchController filter() {

		return this;
	}

	public BatchController sort() {

		return this;
	}

	public BatchController mapping() {

		return this;
	}

	public BatchController addLogger(String s) {

		return this;
	}

	public BatchController addInput(String filePath, String encodeing)
			throws UnsupportedEncodingException, FileNotFoundException {

		FileInformation input = new PhysicalFile(PhysicalFile.INPUT, filePath, encodeing, false);
		this.input = input;

		return this;
	}

	public BatchController addOutput(String filePath, String encodeing, boolean appdening)
			throws UnsupportedEncodingException, FileNotFoundException {
		
		FileInformation output = new PhysicalFile(PhysicalFile.OUTPUT, filePath, encodeing, appdening);
		this.output = output;
		return this;
	}

	public void execute() {

		// stream up the components
		components.get(0).addInputFileInformation(this.input);
		components.get(components.size()).addOutputFileInformation(output);
		
		
		
		//start the batch flow
		for (ComponentII componentII : components) {
			componentII.activate();
		}
	}

}
