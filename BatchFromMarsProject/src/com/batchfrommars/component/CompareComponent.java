package com.batchfrommars.component;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.FileList;

/**
 * 
 * @author JohnFromMars
 * @date 2016年8月13日
 * @remark 2016年8月13日
 */
public abstract class CompareComponent extends ComponentII {

	private FileList inputFileList;
	private FileList outputFileList;

	protected abstract String getKeyFromInput1(String inputData);

	protected abstract String getKeyFromInput2(String inputData);

	protected abstract String getResultFormat(String inputData1, String inputData2);

	public CompareComponent() {
		inputFileList = new FileList();
		outputFileList = new FileList();
	}

	/**
	 * 
	 * @date 2016年8月13日
	 * @remark
	 */
	public void activate() {

		System.out.println(this.getClass().getSimpleName() + " started...");

		String input1 = null;
		String input2 = null;

		if (!inputFileList.isEmpty(0) && !inputFileList.isEmpty(1)) {
			input1 = inputFileList.readFile(0);
			input2 = inputFileList.readFile(1);
		}

		while (inputFileList.size() == 2) {
			// compare two key
			int compare = getKeyFromInput1(input1).compareTo(getKeyFromInput2(input2));

			if (compare == 0) {
				// write out the data
				outputFileList.writeToAllFile(this.getResultFormat(input1, input2));
				// check if the file is not empty then read file
				if (!inputFileList.isEmpty(0) && !inputFileList.isEmpty(1)) {
					input1 = inputFileList.readFile(0);
					input2 = inputFileList.readFile(1);
				} else {
					break;
				}

			} else if (compare > 0) {
				// check if the file is not empty then read file
				if (!inputFileList.isEmpty(1)) {
					input2 = inputFileList.readFile(1);
				} else {
					break;
				}

			} else if (compare < 0) {
				// check if the file is not empty then read file
				if (!inputFileList.isEmpty(0)) {
					input1 = inputFileList.readFile(0);
				} else {
					break;
				}

			}
		}
		// close files
		inputFileList.closeFile();
		outputFileList.closeFile();
		System.out.println(this.getClass().getSimpleName() + " compeleted...");

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
