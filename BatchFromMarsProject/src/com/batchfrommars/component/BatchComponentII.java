package com.batchfrommars.component;

import java.util.LinkedList;

import com.batchfrommars.file.FileList;

/**
 * BatchComponentII is used for data dealing. Extends BatchComponentII and
 * implements excuteProcess method then you can easily create a data dealing
 * process that you need.
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */

public abstract class BatchComponentII extends ComponentII {

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
	@Override
	protected void act() {

		logger.finest("In act method, cheching while loop condition...");
		logger.finest("inputFileList.isAllEmpty()=" + inputFileList.isAllEmpty() + ", isSomeLastComponentsRunning()="
				+ isSomeLastComponentsRunning());

		while (!inputFileList.isAllEmpty() || this.isSomeLastComponentsRunning()) {

			LinkedList<String> inputList = inputFileList.readFile();

			if (!isNull(inputList)) {
				LinkedList<String> outputList = this.excuteProcess(inputList);

				if (outputList != null) {
					if (!isNull(outputList) && outputFileList.size() != 0) {
						outputFileList.writeFile(outputList);
					}
				}
			}
		}

		logger.finest("While loop finish, checking while loop condition...");
		logger.finest("inputFileList.isAllEmpty()=" + inputFileList.isAllEmpty() + ", isSomeLastComponentsRunning()="
				+ isSomeLastComponentsRunning());
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

}
