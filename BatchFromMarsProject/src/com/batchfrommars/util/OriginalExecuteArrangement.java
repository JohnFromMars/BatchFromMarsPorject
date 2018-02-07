package com.batchfrommars.util;

import java.util.InputMismatchException;
import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.FileList;
import com.batchfrommars.file.QueueFile;

public class OriginalExecuteArrangement {
	private boolean compareTaskCkecked = false;

	public void arrangeExecuteTask(List<FileInformation> input, FileInformation output, Logger log, List<Task> tasks,
			List<Task> hiddenTasks, String header, String footer) throws Exception {

		log.finest("components size = " + tasks.size());

		compareTaskCheck(input, log, tasks);

		// set input
		setInput(input, log, tasks, hiddenTasks);

		// set output
		setOutput(output, log, tasks);

		// set temp file
		setTempFiles(log, tasks, hiddenTasks);

		// write header
		writeHeader(log, header, output);

		// start the batch
		startAllComponents(log, tasks, hiddenTasks);

		// wait the unfinished components
		waitUnfinishedComponents(log, tasks, hiddenTasks);

		// write footer
		writeFooter(log, footer, output);

		// close input and output
		closeInputAndOutput(log, input, output);

	}

	/**
	 * this method is to check if the compare task is the first task, and make
	 * sure that controller has two inputs when it has compare task
	 * 
	 * @param input
	 * @param log
	 * @param tasks
	 */
	private void compareTaskCheck(List<FileInformation> input, Logger log, List<Task> tasks)
			throws InputMismatchException, UnsupportedOperationException {
		int compareTaskNumber = 0;

		// navigate all the task to search compareTask
		for (Task task : tasks) {
			if (task.getTaskName().equals(TaskName.COMPARE)) {
				compareTaskNumber++;
			}
		}

		// there can only be 1 compare task
		if (compareTaskNumber > 1) {
			UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException(
					"BatchController does not support more than 1 compare tasks");
			log.warning(unsupportedOperationException.getMessage());
			throw unsupportedOperationException;

			// when compare task is the only and the first one ,input size
			// should be 2
		} else if (compareTaskNumber == 1 && input.size() != 2 && tasks.get(0).getTaskName().equals(TaskName.COMPARE)) {
			InputMismatchException inputMismatchException = new InputMismatchException(
					"input of this BatchController should be two");
			log.warning(inputMismatchException.getMessage());
			throw inputMismatchException;

			// compare size should be 1 and the first one
		} else if (compareTaskNumber == 1 && input.size() == 2
				&& !tasks.get(0).getTaskName().equals(TaskName.COMPARE)) {
			UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException(
					"Compare task must be the first task");
			log.warning(unsupportedOperationException.getMessage());
			throw unsupportedOperationException;
		}

		if (compareTaskNumber == 1) {
			log.finest("legal compare task confirm, set compareTaskCkecked = true");
			compareTaskCkecked = true;

		}

	}

	private void closeInputAndOutput(Logger log, List<FileInformation> input, FileInformation output) throws Exception {
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
	private void setInput(List<FileInformation> input, Logger log, List<Task> tasks, List<Task> hiddenTasks) {

		// input setting for compare task
		// using the sort task in the hidden task sort the data then go to
		// compare task
		if (compareTaskCkecked) {
			hiddenTasks.get(0).getComponent().addInputFileInformation(input.get(0));
			hiddenTasks.get(1).getComponent().addInputFileInformation(input.get(1));

			// normal situation
		} else if (input.size() != 0) {
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
	 * connect all the components with queue file and set the last step
	 * 
	 * @param log
	 * @param tasks
	 */
	private void setTempFiles(Logger log, List<Task> tasks, List<Task> hiddenTasks) {
		// compare task need to connect sort task in the hidden task first
		if (compareTaskCkecked) {
			FileInformation fileInformation1 = new QueueFile();
			FileInformation fileInformation2 = new QueueFile();

			// set the sort task output
			hiddenTasks.get(0).getComponent().addOutputFileInformation(fileInformation1);

			log.finest("add QueueFile" + fileInformation1.toString() + "output of hidden task "
					+ hiddenTasks.get(0).toString());
			hiddenTasks.get(1).getComponent().addOutputFileInformation(fileInformation2);

			log.finest("add QueueFile" + fileInformation2.toString() + "output of hidden task "
					+ hiddenTasks.get(1).toString());

			// add two file as inputs of compare task
			tasks.get(0).getComponent().addInputFileInformation(fileInformation1, fileInformation2);
			log.finest("add" + fileInformation1.toString() + " and " + fileInformation2.toString() + " to task"
					+ tasks.get(0).toString());
			
			tasks.get(0).getComponent().addLastComponent(hiddenTasks.get(0).getComponent(),
					hiddenTasks.get(1).getComponent());
		}

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

	private void startAllComponents(Logger log, List<Task> tasks, List<Task> hiddenTasks) {

		if (compareTaskCkecked) {
			for (Task task : hiddenTasks) {
				task.getComponent().start();
				log.finest("start the hiddenTasks " + task.toString());
			}
		}

		for (Task task : tasks) {
			task.getComponent().start();
			log.finest("start the Tasks " + task.toString());
		}

	}

	private void waitUnfinishedComponents(Logger log, List<Task> tasks, List<Task> hiddenTasks)
			throws InterruptedException {

		if (compareTaskCkecked) {
			for (Task task : hiddenTasks) {
				task.getComponent().join();
				log.finest("wait the task in hiddenTasks " + task.toString());
			}
		}

		for (Task task : tasks) {
			task.getComponent().join();
			log.finest("wait the task in Tasks " + task.toString());
		}

	}

	private void writeHeader(Logger log, String header, FileInformation output) throws Exception {
		if (output != null && header != null) {
			output.writeFile(header);
		}
	}

	private void writeFooter(Logger log, String footer, FileInformation output) throws Exception {
		if (output != null && footer != null) {
			output.writeFile(footer);
		}
	}

}
