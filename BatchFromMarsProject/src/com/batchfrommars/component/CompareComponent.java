package com.batchfrommars.component;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.FileList;

/**
 * input data should be sorted 
 * 
 * @author JohnFromMars
 * @date 2016年8月13日
 * @remark 2016年8月13日
 */
public abstract class CompareComponent extends ComponentII {
	// member area
	private FileList inputFileList;
	private FileList outputFileList;
	// constant area
	private final static int INPUT_1 = 0;
	private final static int INPUT_2 = 1;
	private final static int INPUT_SIZE = 2;
	private final static int EQUAL = 0;

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

		String input1 = null;
		String input2 = null;

		System.out.println(this.getClass().getSimpleName() + START_MSG);

		if (!inputFileList.isEmpty(INPUT_1) && !inputFileList.isEmpty(INPUT_2)) {
			input1 = inputFileList.readFile(INPUT_1);
			input2 = inputFileList.readFile(INPUT_2);
		}

		// it can only be 2 input
		while (inputFileList.size() == INPUT_SIZE) {

			if (input1 != null && input2 != null) {
				// compare two key
				int compare = this.getKeyFromInput1(input1).compareTo(this.getKeyFromInput2(input2));

				if (compare == EQUAL) {
					// write out the data with the specified format
					outputFileList.writeToAllFile(this.getResultFormat(input1, input2));
					// if file is not empty then read the next data
					if (!inputFileList.isEmpty(INPUT_1) && !inputFileList.isEmpty(INPUT_2)) {
						input1 = inputFileList.readFile(INPUT_1);
						input2 = inputFileList.readFile(INPUT_2);
					} else {
						break;
					}
				} else if (compare > EQUAL) {
					// if file is not empty then read the next data
					if (!inputFileList.isEmpty(INPUT_2)) {
						input2 = inputFileList.readFile(INPUT_2);
					} else {
						break;
					}

				} else if (compare < EQUAL) {
					// if file is not empty then read the next data
					if (!inputFileList.isEmpty(INPUT_1)) {
						input1 = inputFileList.readFile(INPUT_1);
					} else {
						break;
					}
				}
			}
		}
		// close files
		inputFileList.closeFile();
		outputFileList.closeFile();
		System.out.println(this.getClass().getSimpleName() + COMPELETE_MSG);

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
