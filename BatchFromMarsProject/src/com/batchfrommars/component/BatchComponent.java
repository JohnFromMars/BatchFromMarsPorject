package com.batchfrommars.component;

import com.batchfrommars.file.FileInformtion;

public abstract class BatchComponent extends Component {

	protected abstract String excuteProcess(String data);

	public BatchComponent() {
	}

	public BatchComponent(FileInformtion inputFile, FileInformtion outputFile) {
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

	

}
