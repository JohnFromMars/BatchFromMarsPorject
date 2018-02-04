package com.batchfrommars.util;

import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;

public class OriginalExecuteArrangement {

	public void arrangeExecuteTask(List<FileInformation> input, FileInformation output, Logger log,
			List<ComponentII> components, String header, String footer) throws Exception {

		log.finest("components size = " + components.size());

		// set input
		setInput(input, log, components);

		// set output
		setOutput(output, log, components);

		// set temp file
		setTempFiles(log, components);

		// write header
		writeHeader(header, output);

		// start the batch
		startAllComponents(components);

		// wait the unfinished components
		waitUnfinishedComponents(components);

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

	private void setInput(List<FileInformation> input, Logger log, List<ComponentII> components) {
		if (input.size() != 0) {
			components.get(0).addInputFileInformation(input.get(0));
			log.finest("start component(0)+input");
		}

	}

	private void setOutput(FileInformation output, Logger log, List<ComponentII> components) {
		if (output != null) {
			components.get(components.size() - 1).addOutputFileInformation(output);
			log.finest("start component(" + (components.size() - 1) + ")+output");

		}
	}

	private void setTempFiles(Logger log, List<ComponentII> components) {

		for (int i = 0; i < components.size(); i++) {
			FileInformation fileInformation = new TemporaryFile();

			if (i < components.size() - 1) {
				components.get(i).addOutputFileInformation(fileInformation);
				log.finest("in loop component(" + i + ")+output");

			}

			if ((i + 1) < components.size()) {
				components.get(i + 1).addInputFileInformation(fileInformation);
				components.get(i + 1).addLastComponent(components.get(i));
				log.finest("in loop component(" + (i + 1) + ")+output");
				log.finest("in loop, add component(" + i + ") to component(" + i + 1 + ")'s last component list");
			}
		}
	}

	private void startAllComponents(List<ComponentII> components) {

		for (ComponentII componentII : components) {
			componentII.start();
		}
	}

	private void waitUnfinishedComponents(List<ComponentII> components) throws InterruptedException {
		for (ComponentII componentII : components) {
			componentII.join();
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
