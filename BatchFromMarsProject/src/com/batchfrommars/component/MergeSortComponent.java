package com.batchfrommars.component;

import com.batchfrommars.util.CompareUtil;

/**
 *
 * MergeSortComponent is designed for merging two file. Notice that both data of
 * files should be sorted first before you use this component.
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public abstract class MergeSortComponent extends ComponentII {
	private final static int INPUT_1 = 0;
	private final static int INPUT_2 = 1;
	private final static int INPUT_SIZE = 2;
	private final static int EQUAL = 0;
	protected final static int ACSENDING = 1;
	protected final static int DECSENDING = -1;

	protected abstract Object getKey(String data);

	protected abstract int getMergeSortMethod();

	/**
	 * The action of this component is to compare data form two previous
	 * components and sort them
	 */
	@Override
	protected void act() throws Exception {
		String input1 = null;
		String input2 = null;

		if (inputFileList.size() != INPUT_SIZE) {
			logger.severe("(inputFileList.size() should be " + INPUT_SIZE + " but was " + inputFileList.size());

		} else {
			input1 = inputFileList.readFile(INPUT_1);
			input2 = inputFileList.readFile(INPUT_2);
			logger.finest("Checking getLastComponentsSize()=" + getLastComponentsSize());

			if (getLastComponentsSize() == 0) {
				sortNoLastComponent(input1, input2);

			} else if (getLastComponentsSize() == 1) {
				sortOneLastComponent(input1, input2);

			} else if (getLastComponentsSize() == 2) {
				sortTwoLastComponent(input1, input2);
			}
		}

	}

	private void sortNoLastComponent(String input1, String input2) throws Exception {
		logger.finest("In sortNoLastComponent method, cheching while loop condition...");
		logger.finest("inputFileList.size() == INPUT_SIZE " + (inputFileList.size() == INPUT_SIZE));

		while (inputFileList.size() == INPUT_SIZE) {

			int compare;

			if (input1 != null && input2 != null) {
				logger.finest("input1 != null && input2 != null, compare getKey(input1) and getKey(input2)");

				compare = CompareUtil.compare(getKey(input1), getKey(input2)) * getMergeSortMethod();

				logger.finest("Compare result=" + compare + ", which getKey(input1)=" + getKey(input1)
						+ ",  getKey(input2)=" + getKey(input2) + ", getMergeSortMethod()=" + getMergeSortMethod());

			} else if (input1 != null && input2 == null) {
				logger.finest("input1 != null && input2 == null, set compare result=-1");
				compare = -1;

			} else if (input1 == null && input2 != null) {
				logger.finest("input1 == null && input2 != null, set compare result=1");
				compare = 1;

			} else {
				logger.finest("input1 == null && input2 == null, while loop finish...");
				break;
			}

			if (compare == EQUAL) {
				logger.finest("Compare=" + EQUAL + " writeToAllFile(input1), which input1=" + input1);
				logger.finest("Checking inputFileList.isEmpty(INPUT_1)=" + inputFileList.isEmpty(INPUT_1));
				outputFileList.writeToAllFile(input1);

				if (!inputFileList.isEmpty(INPUT_1)) {
					logger.finest("Input1 is not empty, read next data");
					input1 = inputFileList.readFile(INPUT_1);

				} else {
					logger.finest("Input1 is empty, set input1=null");
					input1 = null;
				}

			} else if (compare > EQUAL) {
				logger.finest("Compare>" + EQUAL + " writeToAllFile(input2), which input2=" + input2);
				logger.finest("Checking inputFileList.isEmpty(INPUT_2)=" + inputFileList.isEmpty(INPUT_2));
				outputFileList.writeToAllFile(input2);

				if (!inputFileList.isEmpty(INPUT_2)) {
					logger.finest("Input2 is not empty, read next data");
					input2 = inputFileList.readFile(INPUT_2);

				} else {
					logger.finest("Input2 is empty, set input2=null");
					input2 = null;
				}

			} else if (compare < EQUAL) {
				logger.finest("Compare<" + EQUAL + " writeToAllFile(input1), which input1=" + input1);
				logger.finest("Checking inputFileList.isEmpty(INPUT_1)=" + inputFileList.isEmpty(INPUT_1));
				outputFileList.writeToAllFile(input1);

				if (!inputFileList.isEmpty(INPUT_1)) {
					logger.finest("Input1 is not empty, read next data");
					input1 = inputFileList.readFile(INPUT_1);

				} else {
					logger.finest("Input1 is empty, set input1=null");
					input1 = null;
				}
			}
		}

	}

	private void sortTwoLastComponent(String input1, String input2) throws Exception {
		logger.finest("In sortTwoLastComponent method, cheching while loop condition...");
		logger.finest("inputFileList.size() == INPUT_SIZE " + (inputFileList.size() == INPUT_SIZE)
				+ ", inputFileList.isAllEmpty()=" + inputFileList.isAllEmpty() + ", isSomeLastComponentsRunning()="
				+ isSomeLastComponentsRunning());

		while (inputFileList.size() == INPUT_SIZE && !inputFileList.isAllEmpty() || isSomeLastComponentsRunning()) {
			int compare = 0;

			if (input1 != null && input2 != null) {
				logger.finest("input1 != null && input2 != null, compare getKey(input1) and getKey(input2)");
				compare = CompareUtil.compare(getKey(input1), getKey(input2)) * getMergeSortMethod();
				logger.finest("Compare result=" + compare + ", which getKey(input1)=" + getKey(input1)
						+ ",  getKey(input2)=" + getKey(input2) + ", getMergeSortMethod()=" + getMergeSortMethod());

				if (compare == EQUAL) {
					logger.finest("Compare=" + EQUAL + " writeToAllFile(input1), which input1=" + input1);
					logger.finest("Read input1 next data");
					outputFileList.writeToAllFile(input1);
					input1 = inputFileList.readFile(INPUT_1);

				} else if (compare > EQUAL) {
					logger.finest("Compare>" + EQUAL + " writeToAllFile(input2), which input2=" + input2);
					logger.finest("Read input2 next data");
					outputFileList.writeToAllFile(input2);
					input2 = inputFileList.readFile(INPUT_2);

				} else if (compare < EQUAL) {
					logger.finest("Compare<" + EQUAL + " writeToAllFile(input1), which input1=" + input1);
					logger.finest("Read input1 next data");
					outputFileList.writeToAllFile(input1);
					input1 = inputFileList.readFile(INPUT_1);

				}

			} else if (input1 != null && input2 == null) {
				logger.finest("input1 != null && input2 == null, checking condition...");

				if (isLastComponentRunning(INPUT_2) || !inputFileList.isEmpty(INPUT_2)) {
					logger.finest("isLastComponentRunning(INPUT_2)=" + isLastComponentRunning(INPUT_2)
							+ ", inputFileList.isEmpty(INPUT_2)=" + inputFileList.isEmpty(INPUT_2));
					logger.finest("Read input2 next data");
					input2 = inputFileList.readFile(INPUT_2);

				} else if (!isLastComponentRunning(INPUT_2) && inputFileList.isEmpty(INPUT_2)) {
					logger.finest("isLastComponentRunning(INPUT_2)=" + isLastComponentRunning(INPUT_2)
							+ ", inputFileList.isEmpty(INPUT_2)=" + inputFileList.isEmpty(INPUT_2));
					logger.finest(
							"Input2 is empty and not running, write input1=" + input1 + ", and read input1 next data");
					outputFileList.writeToAllFile(input1);
					input1 = inputFileList.readFile(INPUT_1);
				}

			} else if (input1 == null && input2 != null) {
				logger.finest("input1 == null && input2 != null, checking condition...");

				if (isLastComponentRunning(INPUT_1) || !inputFileList.isEmpty(INPUT_1)) {
					logger.finest("isLastComponentRunning(INPUT_1)=" + isLastComponentRunning(INPUT_1)
							+ ", inputFileList.isEmpty(INPUT_1)=" + inputFileList.isEmpty(INPUT_1));
					logger.finest("Read input1 next data");
					input1 = inputFileList.readFile(INPUT_1);

				} else if (!isLastComponentRunning(INPUT_1) && inputFileList.isEmpty(INPUT_1)) {
					logger.finest("isLastComponentRunning(INPUT_1)=" + isLastComponentRunning(INPUT_1)
							+ ", inputFileList.isEmpty(INPUT_1)=" + inputFileList.isEmpty(INPUT_1));
					logger.finest(
							"Input1 is empty and not running, write input2=" + input2 + ", and read input2 next data");
					outputFileList.writeToAllFile(input2);
					input2 = inputFileList.readFile(INPUT_2);
				}

			} else if (input1 == null && input2 == null) {
				logger.finest("input1 == null && input2 == null, read input1 and input2 next data");
				input1 = inputFileList.readFile(INPUT_1);
				input2 = inputFileList.readFile(INPUT_2);
			}

		}

		if (input1 != null && input2 != null) {
			logger.finest("Last round compare, input1 != null && input2 != null");
			int compare = CompareUtil.compare(getKey(input1), getKey(input2)) * getMergeSortMethod();
			logger.finest("Compare result=" + compare + ", which getKey(input1)=" + getKey(input1)
					+ ",  getKey(input2)=" + getKey(input2) + ", getMergeSortMethod()=" + getMergeSortMethod());

			if (compare == EQUAL) {
				logger.finest("Write out input1");
				logger.finest("Write out input2");
				outputFileList.writeToAllFile(input1);
				outputFileList.writeToAllFile(input2);

			} else if (compare > EQUAL) {
				logger.finest("Write out input2");
				logger.finest("Write out input1");
				outputFileList.writeToAllFile(input2);
				outputFileList.writeToAllFile(input1);

			} else if (compare < EQUAL) {
				logger.finest("Write out input1");
				logger.finest("Write out input2");
				outputFileList.writeToAllFile(input1);
				outputFileList.writeToAllFile(input2);
			}
		} else {
			logger.finest("Write out input1");
			logger.finest("Write out input2");
			outputFileList.writeToAllFile(input1);
			outputFileList.writeToAllFile(input2);
		}

	}

	private void sortOneLastComponent(String input1, String input2) throws Exception {
		logger.finest("In sortOneLastComponent method, cheching while loop condition...");
		logger.finest("inputFileList.size() == INPUT_SIZE " + (inputFileList.size() == INPUT_SIZE)
				+ ", inputFileList.isAllEmpty()=" + inputFileList.isAllEmpty() + ", isSomeLastComponentsRunning()="
				+ isSomeLastComponentsRunning());

		int emptyCount = 0;

		while (inputFileList.size() == INPUT_SIZE && !inputFileList.isAllEmpty() || isSomeLastComponentsRunning()) {

			if (input1 != null && input2 != null) {
				emptyCount = 0;
				logger.finest("input1 != null && input2 != null, compare getKey(input1) and getKey(input2)");
				int compare = CompareUtil.compare(getKey(input1), getKey(input2)) * getMergeSortMethod();
				logger.finest("Compare result=" + compare + ", which getKey(input1)=" + getKey(input1)
						+ ",  getKey(input2)=" + getKey(input2) + ", getMergeSortMethod()=" + getMergeSortMethod());

				if (compare == EQUAL) {
					logger.finest("Compare=" + EQUAL + " writeToAllFile(input1), which input1=" + input1);
					logger.finest("Read input1 next data");
					outputFileList.writeToAllFile(input1);
					input1 = inputFileList.readFile(INPUT_1);

				} else if (compare > EQUAL) {
					logger.finest("Compare>" + EQUAL + " writeToAllFile(input2), which input2=" + input2);
					logger.finest("Read input2 next data");
					outputFileList.writeToAllFile(input2);
					input2 = inputFileList.readFile(INPUT_2);

				} else if (compare < EQUAL) {
					logger.finest("Compare<" + EQUAL + " writeToAllFile(input1), which input1=" + input1);
					logger.finest("Read input1 next data");
					outputFileList.writeToAllFile(input1);
					input1 = inputFileList.readFile(INPUT_1);

				}

			} else if (input1 != null && input2 == null) {
				logger.finest("input1 != null && input2 == null, checking emptyCount...");

				if (emptyCount < 50) {
					logger.finest("EmptyCount=" + emptyCount + " less then 50, read input2 next data");
					input2 = inputFileList.readFile(INPUT_2);
					emptyCount++;

				} else if (emptyCount >= 50) {
					logger.finest("EmptyCount=" + emptyCount + " more then 50, write out input1=" + input1);
					logger.finest("Read input1 next data");
					outputFileList.writeToAllFile(input1);
					input1 = inputFileList.readFile(INPUT_1);
				}

			} else if (input1 == null && input2 != null) {
				logger.finest("input1 == null && input2 != null, checking emptyCount...");

				if (emptyCount < 50) {
					logger.finest("EmptyCount=" + emptyCount + " less then 50, read input1 next data");
					input1 = inputFileList.readFile(INPUT_1);
					emptyCount++;

				} else if (emptyCount >= 50) {
					logger.finest("EmptyCount=" + emptyCount + " more then 50, write out input2=" + input2);
					logger.finest("Read input2 next data");
					outputFileList.writeToAllFile(input2);
					input2 = inputFileList.readFile(INPUT_2);
				}

			} else if (input1 == null && input2 == null) {
				logger.finest("input1 == null && input2 == null, read input1 and input2 next data");
				input1 = inputFileList.readFile(INPUT_1);
				input2 = inputFileList.readFile(INPUT_2);
			}

		}

		if (input1 != null && input2 != null) {
			int compare = CompareUtil.compare(getKey(input1), getKey(input2)) * getMergeSortMethod();
			logger.finest("Last round compare, input1 != null && input2 != null");
			logger.finest("Compare result=" + compare + ", which getKey(input1)=" + getKey(input1)
					+ ",  getKey(input2)=" + getKey(input2) + ", getMergeSortMethod()=" + getMergeSortMethod());

			if (compare == EQUAL) {
				logger.finest("Write out input1");
				logger.finest("Write out input2");
				outputFileList.writeToAllFile(input1);
				outputFileList.writeToAllFile(input2);

			} else if (compare > EQUAL) {
				logger.finest("Write out input2");
				logger.finest("Write out input1");
				outputFileList.writeToAllFile(input2);
				outputFileList.writeToAllFile(input1);

			} else if (compare < EQUAL) {
				logger.finest("Write out input1");
				logger.finest("Write out input2");
				outputFileList.writeToAllFile(input1);
				outputFileList.writeToAllFile(input2);
			}

		} else {
			logger.finest("Write out input1");
			logger.finest("Write out input2");
			outputFileList.writeToAllFile(input1);
			outputFileList.writeToAllFile(input2);
		}

	}

}
