package com.batchfrommars.component;

import com.batchfrommars.file.FileInformation;

public abstract class BatchComponent extends Component {
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

		while (!inputFile.isEmpty() || isLastComponentRunning()) {
			String input = inputFile.readFile();
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

	public void run() {
		try {
			activate();
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " compeleted...");
		}
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
