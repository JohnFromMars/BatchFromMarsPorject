package com.batchfrommars.component;

import java.util.LinkedList;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.FileList;

/**
 * Batch Component with multiple input and output
 * 
 * @author Yj
 *
 */
public abstract class BatchComponentII extends ComponentII {

	private FileList inputFileList;
	private FileList outputFileList;

	protected abstract LinkedList<String> excuteProcess(LinkedList<String> dataList);

	public BatchComponentII() {
		inputFileList = new FileList();
		outputFileList = new FileList();
	}

	public void addInputFileInformation(FileInformation fileInformation) {
		this.inputFileList.addFileInformation(fileInformation);
	}

	public void addOutputFileInformation(FileInformation fileInformation) {
		this.outputFileList.addFileInformation(fileInformation);
	}

	public void activate() {

		System.out.println(this.getClass().getSimpleName() + " started...");

		while (!inputFileList.isEmpty() || this.isLastComponentsRunning()) {
//			System.out.println(this.getClass().getSimpleName()+inputFileList.isEmpty());
//			System.out.println(this.getClass().getSimpleName()+isLastComponentsRunning());
//			System.out.println(this.getClass().getSimpleName() + " read start...");
			LinkedList<String> inputList = inputFileList.readFile();
//			System.out.println(this.getClass().getSimpleName()+inputList);
//			System.out.println(this.getClass().getSimpleName() + " read finish...");
			if (!isNull(inputList)) {
				LinkedList<String> outputList = this.excuteProcess(inputList);
				if (!isNull(outputList) && outputList != null) {
					outputFileList.writeFile(outputList);
				}

			}

		}

		inputFileList.closeFile();
		outputFileList.closeFile();
		System.out.println(this.getClass().getSimpleName() + " compeleted...");
	}

	public void run() {
		activate();
	}

	public boolean isNull(LinkedList<String> inputList) {
		boolean isNull = true;
		for (String item : inputList) {
			isNull = isNull && (item == null);
		}
		return isNull;
	}

	public FileList getInputFileList() {
		return inputFileList;
	}

	public void setInputFileList(FileList inputFileList) {
		this.inputFileList = inputFileList;
	}

	public FileList getOutputFileList() {
		return outputFileList;
	}

	public void setOutputFileList(FileList outputFileList) {
		this.outputFileList = outputFileList;
	}

}
