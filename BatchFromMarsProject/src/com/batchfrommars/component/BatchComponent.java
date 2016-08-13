package com.batchfrommars.component;

import com.batchfrommars.file.FileInformation;

/**
 * 
 * @author JohnFromMars
 * @date 2016年8月13日
 * @remark 2016年8月13日
 */
public abstract class BatchComponent extends ComponentII {
	protected FileInformation inputFile;
	protected FileInformation outputFile;

	protected abstract String excuteProcess(String data);

	public BatchComponent() {
	}

	public BatchComponent(FileInformation inputFile, FileInformation outputFile) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	public void activate() {

		System.out.println(this.getClass().getSimpleName() + " started...");

		while (!inputFile.isEmpty() || isLastComponentsRunning()) {
			String input = (String) inputFile.readFile();
			if (input != null) {
				String output = this.excuteProcess(input);
				if (output != null && outputFile != null) {
					outputFile.writeFile(output);
				}
			}
		}

		inputFile.closeFile();
		outputFile.closeFile();
		System.out.println(this.getClass().getSimpleName() + " compeleted...");
	}

	public FileInformation getInputFile() {
		return inputFile;
	}

	public void setInputFile(FileInformation inputFile) {
		this.inputFile = inputFile;
	}

	public FileInformation getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(FileInformation outputFile) {
		this.outputFile = outputFile;
	}

}
