package com.batchfrommars.component;

import java.util.LinkedList;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.FileList;

/**
 * Batch Component with multiple input and output
 * 
 * @author JohnFromMars
 *
 */
public abstract class BatchComponentII extends ComponentII {
	// FileInformation list for input and output
	private FileList inputFileList;
	private FileList outputFileList;
	// constant area
	protected final static int INPUT_1 = 0;
	protected final static int INPUT_2 = 1;
	protected final static int INPUT_3 = 2;
	protected final static int INPUT_4 = 4;
	protected final static int INPUT_5 = 4;


	/**
	 * implement excuteProcess then you can activate or start your
	 * BatchComponent
	 * 
	 * @param dataList
	 * @return
	 */
	protected abstract LinkedList<String> excuteProcess(LinkedList<String> dataList);

	public BatchComponentII() {
		inputFileList = new FileList();
		outputFileList = new FileList();
	}

	/**
	 * activate the BatchComponentII when children class implements
	 * excuteProcess method
	 * 
	 */
	public void activate() {

		System.out.println(this.getClass().getSimpleName() + START_MSG);

		while (!inputFileList.isEmpty() || this.isLastComponentsRunning()) {
			LinkedList<String> inputList = inputFileList.readFile();

			if (!isNull(inputList)) {
				LinkedList<String> outputList = this.excuteProcess(inputList);
				if (!isNull(outputList) && outputList != null) {
					outputFileList.writeFile(outputList);
				}
			}
		}

		inputFileList.closeFile();
		outputFileList.closeFile();
		System.out.println(this.getClass().getSimpleName() + COMPELETE_MSG);
	}

	/**
	 * to check if inputList is null or not return true if all element in the
	 * list is null
	 * 
	 * @param inputList
	 * @return
	 */
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

	/**
	 * add FileInformation into inputFileList
	 * 
	 * @param fileInformation
	 */
	public void addInputFileInformation(FileInformation fileInformation) {
		this.inputFileList.addFileInformation(fileInformation);
	}

	/**
	 * add FileInformation into inputFileList
	 * 
	 * @param fileInformation
	 */
	public void addInputFileInformation(FileInformation fileInformation, FileInformation fileInformation2) {
		this.inputFileList.addFileInformation(fileInformation);
		this.inputFileList.addFileInformation(fileInformation2);
	}

	/**
	 * add FileInformation into inputFileList
	 * 
	 * @param fileInformation
	 */
	public void addInputFileInformation(FileInformation fileInformation, FileInformation fileInformation2,
			FileInformation fileInformation3) {
		this.inputFileList.addFileInformation(fileInformation);
		this.inputFileList.addFileInformation(fileInformation2);
		this.inputFileList.addFileInformation(fileInformation3);
	}

	/**
	 * add FileInformation into inputFileList
	 * 
	 * @param fileInformation
	 */
	public void addInputFileInformation(FileInformation fileInformation, FileInformation fileInformation2,
			FileInformation fileInformation3, FileInformation fileInformation4) {
		this.inputFileList.addFileInformation(fileInformation);
		this.inputFileList.addFileInformation(fileInformation2);
		this.inputFileList.addFileInformation(fileInformation3);
		this.inputFileList.addFileInformation(fileInformation4);
	}

	/**
	 * add FileInformation into inputFileList
	 * 
	 * @param fileInformation
	 */
	public void addInputFileInformation(FileInformation fileInformation, FileInformation fileInformation2,
			FileInformation fileInformation3, FileInformation fileInformation4, FileInformation fileInformation5) {
		this.inputFileList.addFileInformation(fileInformation);
		this.inputFileList.addFileInformation(fileInformation2);
		this.inputFileList.addFileInformation(fileInformation3);
		this.inputFileList.addFileInformation(fileInformation4);
		this.inputFileList.addFileInformation(fileInformation5);
	}

	/**
	 * add FileInformation into outputFileList
	 * 
	 * @param fileInformation
	 */
	public void addOutputFileInformation(FileInformation fileInformation) {
		this.outputFileList.addFileInformation(fileInformation);
	}

	/**
	 * add FileInformation into outputFileList
	 * 
	 * @param fileInformation
	 */
	public void addOutputFileInformation(FileInformation fileInformation, FileInformation fileInformation2) {
		this.outputFileList.addFileInformation(fileInformation);
		this.outputFileList.addFileInformation(fileInformation2);
	}

	/**
	 * add FileInformation into outputFileList
	 * 
	 * @param fileInformation
	 */
	public void addOutputFileInformation(FileInformation fileInformation, FileInformation fileInformation2,
			FileInformation fileInformation3) {
		this.outputFileList.addFileInformation(fileInformation);
		this.outputFileList.addFileInformation(fileInformation2);
		this.outputFileList.addFileInformation(fileInformation3);
	}

	/**
	 * add FileInformation into outputFileList
	 * 
	 * @param fileInformation
	 */
	public void addOutputFileInformation(FileInformation fileInformation, FileInformation fileInformation2,
			FileInformation fileInformation3, FileInformation fileInformation4) {
		this.outputFileList.addFileInformation(fileInformation);
		this.outputFileList.addFileInformation(fileInformation2);
		this.outputFileList.addFileInformation(fileInformation3);
		this.outputFileList.addFileInformation(fileInformation4);
	}

	/**
	 * add FileInformation into outputFileList
	 * 
	 * @param fileInformation
	 */
	public void addOutputFileInformation(FileInformation fileInformation, FileInformation fileInformation2,
			FileInformation fileInformation3, FileInformation fileInformation4, FileInformation fileInformation5) {
		this.outputFileList.addFileInformation(fileInformation);
		this.outputFileList.addFileInformation(fileInformation2);
		this.outputFileList.addFileInformation(fileInformation3);
		this.outputFileList.addFileInformation(fileInformation4);
		this.outputFileList.addFileInformation(fileInformation5);
	}

}
