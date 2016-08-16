package com.batchfrommars.component;

import com.batchfrommars.file.FileInformation;

/**
 * 
 * @author JohnFromMars
 * @date 2016年8月13日
 * @remark 2016年8月13日
 */
public abstract class BatchComponent extends ComponentII {
	// member area
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

		System.out.println(this.getClass().getSimpleName() + START_MSG);

		while (!inputFile.isEmpty() || isSomeLastComponentsRunning()) {
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
		System.out.println(this.getClass().getSimpleName() + COMPELETE_MSG);
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
