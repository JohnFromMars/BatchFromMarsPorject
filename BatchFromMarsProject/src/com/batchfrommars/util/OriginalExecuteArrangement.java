package com.batchfrommars.util;

import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.FileList;
import com.batchfrommars.file.QueueFile;

public class OriginalExecuteArrangement {

	public void arrangeExecuteTask(List<FileInformation> input, FileInformation output, Logger log,
			List<Task> tasks, String header, String footer) throws Exception {

		log.finest("components size = " + tasks.size());

		// set input
		setInput(input, log, tasks);

		// set output
		setOutput(output, log, tasks);

		// set temp file
		setTempFiles(log, tasks);

		// write header
		writeHeader(header, output);

		// start the batch
		startAllComponents(tasks);

		// wait the unfinished components
		waitUnfinishedComponents(tasks);

		// write footer
		writeFooter(footer, output);

		// close input and output
		closeInputAndOutput(input, output);

	}

	private void closeInputAndOutput(List<FileInformation> input, FileInformation output) throws Exception {
		if (input != null) {
			for (FileInformation fileInformation : input) {
				fileInformation.closeFile();
			}
		}
		if (output != null) {
			output.closeFile();
		}
	}

	/*
	 * connect the inputs to the first task
	 */
	private void setInput(List<FileInformation> input, Logger log, List<Task> tasks) {
		if (input.size() != 0) {
			FileList fileList = new FileList();
			fileList.setFileInformationsList(input);
			tasks.get(0).getComponent().setInputFileList(fileList);
			log.finest("start components.get(0).setInputFileList(fileList)");
		}

	}

	private void setOutput(FileInformation output, Logger log, List<Task> tasks) {
		if (output != null) {
			tasks.get(tasks.size() - 1).getComponent().addOutputFileInformation(output);
			log.finest("start component(" + (tasks.size() - 1) + ")+output");
		}
	}

	/**
	 * connect all the components with queue file
	 * and set the last step
	 * @param log
	 * @param tasks
	 */
	private void setTempFiles(Logger log, List<Task> tasks) {

		for (int i = 0; i < tasks.size(); i++) {
			FileInformation fileInformation = new QueueFile();

			if (i < tasks.size() - 1) {
				tasks.get(i).getComponent().addOutputFileInformation(fileInformation);
				log.finest("in loop component(" + i + ")+output");

			}

			if ((i + 1) < tasks.size()) {
				tasks.get(i + 1).getComponent().addInputFileInformation(fileInformation);
				tasks.get(i + 1).getComponent().addLastComponent(tasks.get(i).getComponent());
				log.finest("in loop component(" + (i + 1) + ")+output");
				log.finest("in loop, add component(" + i + ") to component(" + i + 1 + ")'s last component list");
			}
		}
	}

	private void startAllComponents(List<Task> tasks) {

		for (Task task : tasks) {
			task.getComponent().start();
		}
	}

	private void waitUnfinishedComponents(List<Task> tasks) throws InterruptedException {
		for (Task task : tasks) {
			task.getComponent().join();
		}
	}

	private void writeHeader(String header, FileInformation output) throws Exception {
		if (output != null && header != null) {
			output.writeFile(header);
		}
	}

	private void writeFooter(String footer, FileInformation output) throws Exception {
		if (output != null && footer != null) {
			output.writeFile(footer);
		}
	}

}
