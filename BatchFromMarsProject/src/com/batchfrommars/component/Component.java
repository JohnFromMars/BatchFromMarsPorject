package com.batchfrommars.component;

import com.batchfrommars.file.FileInformation;

public class Component extends Thread {
	protected FileInformation inputFile;
	protected FileInformation outputFile;
	private Component lastComponent;


	public Component getLastComponent() {
		return lastComponent;
	}

	public void setLastComponent(Component lastComponent) {
		this.lastComponent = lastComponent;
	}

	public boolean isLastComponentRunning() {
		
			if (lastComponent == null) {
				return false;
			} else {
				return lastComponent.isAlive();
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
