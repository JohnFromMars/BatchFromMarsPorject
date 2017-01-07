package com.batchfrommars.component;

import com.batchfrommars.file.FileList;
import com.batchfrommars.util.CompareUtil;

/**
 * CompareComponent is designed for compare purpose. Extend CompareComponent and
 * implement getKeyFromInput1, getKeyFromInput2 and getResultFormat, then you
 * can easily create a compare process of two data. Notice that CompareComponent
 * can has only two inputs and both inputs key should be sorted.
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public abstract class CompareComponent extends ComponentII {

	// constant area
	private final static int INPUT_1 = 0;
	private final static int INPUT_2 = 1;
	private final static int INPUT_SIZE = 2;
	private final static int EQUAL = 0;
	private final static int ZERO = 0;
	private final static int DEFAULT_MAX_EMPTY_TIMES = 50;
	private final static int NO_COMPONENT = 0;
	private final static int ONE_COMPONENT = 1;
	private final static int TWO_COMPONENTS = 2;
	private final static String WARNING_MSG = "The input number of CompareComponent can only be 2.";

	protected abstract Object getKeyFromInput1(String inputData);

	protected abstract Object getKeyFromInput2(String inputData);

	protected abstract String getResultFormat(String inputData1, String inputData2);

	public CompareComponent() {
		inputFileList = new FileList();
		outputFileList = new FileList();
	}

	protected void act() {

		String input1 = null;
		String input2 = null;

		if (inputFileList.size() != INPUT_SIZE) {
			logger.severe(WARNING_MSG);

		} else {
			// perform with 3 condition
			// no last component
			if (getLastComponentsSize() == NO_COMPONENT) {
				logger.finest("getLastComponentsSize()=" + getLastComponentsSize());
				compareNoLastComponent(input1, input2);

				// one last component
			} else if (getLastComponentsSize() == ONE_COMPONENT) {
				logger.finest("getLastComponentsSize()=" + getLastComponentsSize());
				compareOneLastComponent(input1, input2);

				// two last component
			} else if (getLastComponentsSize() == TWO_COMPONENTS) {
				logger.finest("getLastComponentsSize()=" + getLastComponentsSize());
				compareTwoLastComponent(input1, input2);
			}
		}

	}

	private void compareNoLastComponent(String input1, String input2) {
		logger.finest("In compareNoLastComponent ehile loop checking condition...");
		logger.finest("inputFileList.size()=" + inputFileList.size() + ", inputFileList.isAllEmpty()"
				+ inputFileList.isAllEmpty());

		while (inputFileList.size() == INPUT_SIZE && !inputFileList.isAllEmpty()) {
			// no inut
			if (input1 == null && input2 == null) {
				input1 = inputFileList.readFile(INPUT_1);
				input2 = inputFileList.readFile(INPUT_2);

			} else if (input1 != null && input2 == null) {
				break;

			} else if (input1 == null && input2 != null) {
				break;

			} else if (input1 != null && input2 != null) {

				int compare = CompareUtil.compare(getKeyFromInput1(input1), getKeyFromInput2(input2));
				logger.finest("Key compare result=" + compare + ", getKeyFromInput1(input1)=" + getKeyFromInput1(input1)
						+ ", getKeyFromInput2(input2)=" + getKeyFromInput2(input2));

				if (compare == EQUAL) {
					// write out the data with the specified format
					String outputData = this.getResultFormat(input1, input2);

					if (outputFileList.size() != ZERO) {
						outputFileList.writeToAllFile(outputData);
						logger.finest("Compare result is 0, write out input1=" + input1 + " and input2=" + input2
								+ ", outputData=" + outputData);
					}

					logger.finest("Input1 and Input2 read next data...");
					input1 = inputFileList.readFile(INPUT_1);
					input2 = inputFileList.readFile(INPUT_2);
				}

				else if (compare > EQUAL) {

					input2 = inputFileList.readFile(INPUT_2);
					logger.finest("Compare result is " + compare + " read input2 next data...");

				} else if (compare < EQUAL) {
					// if file is not empty then read the next data
					input1 = inputFileList.readFile(INPUT_1);
					logger.finest("Compare result is " + compare + " read input1 next data...");
				}

			}
		}

		// check last round data
		if (input1 != null && input2 != null) {

			int compare = CompareUtil.compare(getKeyFromInput1(input1), getKeyFromInput2(input2));

			logger.finest("Checking last round data, compare result=" + compare);

			if (compare == EQUAL) {
				// write out the data with the specified format
				String outputData = this.getResultFormat(input1, input2);

				if (outputFileList.size() != ZERO) {
					outputFileList.writeToAllFile(outputData);
					logger.finest("Last round compare result is 0, write out input1=" + input1 + " and input2=" + input2
							+ ", outputData=" + outputData);
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
				if (emptyCount < DEFAULT_MAX_EMPTY_TIMES) {
					input2 = inputFileList.readFile(INPUT_2);
					emptyCount++;
					System.out.println("count++");
				} else {
					input1 = inputFileList.readFile(INPUT_1);
				}

			} else if (input1 == null && input2 != null) {
				if (emptyCount < DEFAULT_MAX_EMPTY_TIMES) {
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

				int compare = CompareUtil.compare(getKeyFromInput1(input1), getKeyFromInput2(input2));
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
			int compare = CompareUtil.compare(getKeyFromInput1(input1), getKeyFromInput2(input2));

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

				int compare = CompareUtil.compare(getKeyFromInput1(input1), getKeyFromInput2(input2));

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
			int compare = CompareUtil.compare(getKeyFromInput1(input1), getKeyFromInput2(input2));
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
