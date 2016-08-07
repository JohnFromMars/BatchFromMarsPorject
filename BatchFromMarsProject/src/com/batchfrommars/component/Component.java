package com.batchfrommars.component;

import com.batchfrommars.file.FileInformtion;

public class Component extends Thread {
	protected FileInformtion inputFile;
	protected FileInformtion outputFile;
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

	public FileInformtion getInputFile() {
		return inputFile;
	}

	public void setInputFile(FileInformtion inputFile) {
		this.inputFile = inputFile;
	}

	public FileInformtion getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(FileInformtion outputFile) {
		this.outputFile = outputFile;
	}

	
	

	


}
