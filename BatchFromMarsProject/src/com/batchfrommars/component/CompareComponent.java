package com.batchfrommars.component;

import com.batchfrommars.file.FileList;

/**
 * input data should be sorted
 * 
 * @author JohnFromMars
 * @date 2016年8月13日
 * @remark 2016年8月13日
 */
public abstract class CompareComponent extends ComponentII {

	// constant area
	private final static int INPUT_1 = 0;
	private final static int INPUT_2 = 1;
	private final static int INPUT_SIZE = 2;
	private final static int EQUAL = 0;
	private final static int ZERO = 0;
	private final static int DEFAULT_MAX_EMPTY_TIME = 50;
	private final static int NO_COMPONENT = 0;
	private final static int ONE_COMPONENT = 1;
	private final static int TWO_COMPONENTS = 2;

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
	protected void act() {

		String input1 = null;
		String input2 = null;

		System.out.println(this.getClass().getSimpleName() + START_MSG);
		// perform with 3 condition
		// no last component
		if (getLastComponentsSize() == NO_COMPONENT) {

			compareNoLastComponent(input1, input2);

			// one last component
		} else if (getLastComponentsSize() == ONE_COMPONENT) {

			compareOneLastComponent(input1, input2);

			// two last component
		} else if (getLastComponentsSize() == TWO_COMPONENTS) {

			compareTwoLastComponent(input1, input2);
		}

		// close files
		inputFileList.closeFile();
		outputFileList.closeFile();
		System.out.println(this.getClass().getSimpleName() + COMPELETE_MSG);

	}

	private void compareNoLastComponent(String input1, String input2) {
		while (inputFileList.size() == INPUT_SIZE && !inputFileList.isAllEmpty()) {
			// no inut
			if (input1 == null && input2 == null) {
				input1 = inputFileList.readFile(INPUT_1);
				input2 = inputFileList.readFile(INPUT_2);
				System.out.println(inputFileList.isAllEmpty());

			} else if (input1 != null && input2 == null) {
				break;
			} else if (input1 == null && input2 != null) {
				break;
			} else if (input1 != null && input2 != null) {

				// compare two key
				System.out.println("*before com" + input1 + "," + input2);

				System.out.println("input 2 is " + input2 == null);

				int compare = this.getKeyFromInput1(input1).compareTo(this.getKeyFromInput2(input2));

				if (compare == EQUAL) {
					// write out the data with the specified format
					String outputData = this.getResultFormat(input1, input2);

					if (outputFileList.size() != ZERO) {
						outputFileList.writeToAllFile(outputData);
						System.out.println("write 1 2 " + input1 + "," + input2);
					}

					input1 = inputFileList.readFile(INPUT_1);
					input2 = inputFileList.readFile(INPUT_2);
				}

				else if (compare > EQUAL) {

					input2 = inputFileList.readFile(INPUT_2);
					System.out.println("read 2 " + input2);

				} else if (compare < EQUAL) {
					// if file is not empty then read the next data
					input1 = inputFileList.readFile(INPUT_1);
					System.out.println("read 1 " + input1);
				}

			}
		}

		if (input1 != null && input2 != null) {
			int compare = this.getKeyFromInput1(input1).compareTo(this.getKeyFromInput2(input2));

			if (compare == EQUAL) {
				// write out the data with the specified format
				String outputData = this.getResultFormat(input1, input2);
				if (outputFileList.size() != ZERO) {
					outputFileList.writeToAllFile(outputData);
					System.out.println("write 1 2 " + input1 + "," + input2);
				}
			}
		}
	}

	private void compareOneLastComponent(String input1, String input2) {
		int emptyCount = ZERO;

		while (inputFileList.size() == INPUT_SIZE && !inputFileList.isAllEmpty() || isSomeLastComponentsRunning()) {

			if (input1 == null && input2 == null) {
				input1 = inputFileList.readFile(INPUT_1);
				input2 = inputFileList.readFile(INPUT_2);
				System.out.println(inputFileList.isAllEmpty());

			} else if (input1 != null && input2 == null) {
				if (emptyCount < DEFAULT_MAX_EMPTY_TIME) {
					input2 = inputFileList.readFile(INPUT_2);
					emptyCount++;
					System.out.println("count++");
				} else {
					input1 = inputFileList.readFile(INPUT_1);
				}

			} else if (input1 == null && input2 != null) {
				if (emptyCount < DEFAULT_MAX_EMPTY_TIME) {
					input1 = inputFileList.readFile(INPUT_1);
					emptyCount++;
					System.out.println("count++");
				} else {
					input2 = inputFileList.readFile(INPUT_2);
				}

			} else if (input1 != null && input2 != null) {

				// compare two key
				System.out.println("*before com" + input1 + "," + input2);

				System.out.println("input 2 is " + input2 == null);

				int compare = this.getKeyFromInput1(input1).compareTo(this.getKeyFromInput2(input2));
				emptyCount = ZERO;

				if (compare == EQUAL) {

					String outputData = this.getResultFormat(input1, input2);
					if (outputFileList.size() != ZERO) {
						outputFileList.writeToAllFile(outputData);
						System.out.println("write 1 2 " + input1 + "," + input2);
					}

					input1 = inputFileList.readFile(INPUT_1);
					input2 = inputFileList.readFile(INPUT_2);
				}

				else if (compare > EQUAL) {

					input2 = inputFileList.readFile(INPUT_2);
					System.out.println("read 2 " + input2);

				} else if (compare < EQUAL) {
					// if file is not empty then read the next data
					input1 = inputFileList.readFile(INPUT_1);
					System.out.println("read 1 " + input1);
				}
			}
		}

		if (input1 != null && input2 != null) {
			int compare = this.getKeyFromInput1(input1).compareTo(this.getKeyFromInput2(input2));

			if (compare == EQUAL) {
				// write out the data with the specified format
				String outputData = this.getResultFormat(input1, input2);
				if (outputFileList.size() != ZERO) {
					outputFileList.writeToAllFile(outputData);
					System.out.println("write 1 2 " + input1 + "," + input2);
				}
			}
		}

	}

	private void compareTwoLastComponent(String input1, String input2) {
		while (inputFileList.size() == INPUT_SIZE && !inputFileList.isAllEmpty() || isSomeLastComponentsRunning()) {
			if (input1 == null && input2 == null) {
				input1 = inputFileList.readFile(INPUT_1);
				input2 = inputFileList.readFile(INPUT_2);
				System.out.println(inputFileList.isAllEmpty());

			} else if (input1 != null && input2 == null) {
				if (isLastComponentRunning(INPUT_2)) {
					input2 = inputFileList.readFile(INPUT_2);
					System.out.println("input2 alive");
				} else if (!isLastComponentRunning(INPUT_2)) {
					input1 = inputFileList.readFile(INPUT_1);
				}

			} else if (input1 == null && input2 != null) {
				if (isLastComponentRunning(INPUT_1)) {
					input1 = inputFileList.readFile(INPUT_1);
					System.out.println("count++");
				} else if (!isLastComponentRunning(INPUT_1)) {
					input2 = inputFileList.readFile(INPUT_2);
				}

			} else if (input1 != null && input2 != null) {

				// compare two key
				System.out.println("*before com" + input1 + "," + input2);

				System.out.println("input 2 is " + input2 == null);

				int compare = this.getKeyFromInput1(input1).compareTo(this.getKeyFromInput2(input2));

				if (compare == EQUAL) {
					// write out the data with the specified format
					String outputData = this.getResultFormat(input1, input2);
					if (outputFileList.size() != ZERO) {
						outputFileList.writeToAllFile(outputData);
						System.out.println("write 1 2 " + input1 + "," + input2);
					}
					input1 = inputFileList.readFile(INPUT_1);
					input2 = inputFileList.readFile(INPUT_2);
				}

				else if (compare > EQUAL) {

					input2 = inputFileList.readFile(INPUT_2);
					System.out.println("read 2 " + input2);

				} else if (compare < EQUAL) {
					// if file is not empty then read the next data
					input1 = inputFileList.readFile(INPUT_1);
					System.out.println("read 1 " + input1);
				}
			}
		}

		if (input1 != null && input2 != null) {
			int compare = this.getKeyFromInput1(input1).compareTo(this.getKeyFromInput2(input2));
			if (compare == EQUAL) {
				// write out the data with the specified format
				String outputData = this.getResultFormat(input1, input2);
				if (outputFileList.size() != ZERO) {
					outputFileList.writeToAllFile(outputData);
					System.out.println("write 1 2 " + input1 + "," + input2);
				}
			}
		}

	}
}
