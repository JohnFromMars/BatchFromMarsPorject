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
	private final static String WARNING_MSG = "The input number of MergeSortComponent can only be 2.";
	protected final static int ACSENDING = 1;
	protected final static int DECSENDING = -1;

	protected abstract Object getKey(String data);

	protected abstract int getMergeSortMethod();

	@Override
	protected void act() {
		String input1 = null;
		String input2 = null;

		if (this.getClass().getSimpleName() != null && inputFileList.size() == INPUT_SIZE) {
			System.out.println(this.getClass().getSimpleName() + START_MSG);
		} else if (inputFileList.size() != INPUT_SIZE) {
			System.err.println(WARNING_MSG);
		}

		input1 = inputFileList.readFile(INPUT_1);
		input2 = inputFileList.readFile(INPUT_2);

		if (getLastComponentsSize() == 0) {
			sortNoLastComponent(input1, input2);
		} else if (getLastComponentsSize() == 1) {
			sortOneLastComponent(input1, input2);
		} else if (getLastComponentsSize() == 2) {
			sortTwoLastComponent(input1, input2);
		}

	

		if (this.getClass().getSimpleName() != null && inputFileList.size() == INPUT_SIZE) {
			System.out.println(this.getClass().getSimpleName() + COMPELETE_MSG);
		}
	}

	private void sortNoLastComponent(String input1, String input2) {
		while (inputFileList.size() == INPUT_SIZE) {
			// System.out.println("input1 " + input1 + "," + "input2 " +
			// input2);
			int compare;

			if (input1 != null && input2 != null) {
				compare = CompareUtil.compare(getKey(input1), getKey(input2)) * getMergeSortMethod();
			} else if (input1 != null && input2 == null) {
				compare = -1;
			} else if (input1 == null && input2 != null) {
				compare = 1;
			} else {
				break;
			}

			if (compare == EQUAL) {
				outputFileList.writeToAllFile(input1);
				// outputFileList.writeToAllFile(input2);
				// System.out.println("write 1 2 " + input1 + " " + input2);

				if (!inputFileList.isEmpty(INPUT_1)) {
					input1 = inputFileList.readFile(INPUT_1);

				} else {
					input1 = null;
				}
				// if (!inputFileList.isEmpty(INPUT_2)) {
				// input2 = inputFileList.readFile(INPUT_2);
				//
				// } else {
				// input2 = null;
				// }

			} else if (compare > EQUAL) {
				outputFileList.writeToAllFile(input2);
				// System.out.println("write 2 " + input2);
				if (!inputFileList.isEmpty(INPUT_2)) {
					input2 = inputFileList.readFile(INPUT_2);
				} else {
					input2 = null;
				}

			} else if (compare < EQUAL) {
				outputFileList.writeToAllFile(input1);
				// System.out.println("write 1 " + input1);
				if (!inputFileList.isEmpty(INPUT_1)) {
					input1 = inputFileList.readFile(INPUT_1);
				} else {
					input1 = null;
				}
			}
		}

	}

	private void sortTwoLastComponent(String input1, String input2) {
		while (inputFileList.size() == INPUT_SIZE && !inputFileList.isAllEmpty() || isSomeLastComponentsRunning()) {
			// System.out.println("input1 " + input1 + "," + "input2 " +
			// input2);
			int compare = 0;

			if (input1 != null && input2 != null) {
				compare = CompareUtil.compare(getKey(input1), getKey(input2)) * getMergeSortMethod();

				if (compare == EQUAL) {
					outputFileList.writeToAllFile(input1);
					// outputFileList.writeToAllFile(input2);
					// System.out.println(this.getClass().getSimpleName()+"write
					// 1 2 " + input1 + " " + input2);
					input1 = inputFileList.readFile(INPUT_1);
					// input2 = inputFileList.readFile(INPUT_2);

				} else if (compare > EQUAL) {
					outputFileList.writeToAllFile(input2);
					// System.out.println(this.getClass().getSimpleName()+"write
					// 2 " + input2);
					input2 = inputFileList.readFile(INPUT_2);

				} else if (compare < EQUAL) {
					outputFileList.writeToAllFile(input1);
					// System.out.println(this.getClass().getSimpleName()+"write
					// 1 " + input1);
					input1 = inputFileList.readFile(INPUT_1);

				}

			} else if (input1 != null && input2 == null) {

				if (isLastComponentRunning(INPUT_2) || !inputFileList.isEmpty(INPUT_2)) {
					input2 = inputFileList.readFile(INPUT_2);
				} else if (!isLastComponentRunning(INPUT_2) && inputFileList.isEmpty(INPUT_2)) {
					outputFileList.writeToAllFile(input1);
					input1 = inputFileList.readFile(INPUT_1);
				}

			} else if (input1 == null && input2 != null) {

				if (isLastComponentRunning(INPUT_1) || !inputFileList.isEmpty(INPUT_1)) {
					input1 = inputFileList.readFile(INPUT_1);
				} else if (!isLastComponentRunning(INPUT_1) && inputFileList.isEmpty(INPUT_1)) {
					outputFileList.writeToAllFile(input2);
					input2 = inputFileList.readFile(INPUT_2);
				}

			} else if (input1 == null && input2 == null) {
				input1 = inputFileList.readFile(INPUT_1);
				input2 = inputFileList.readFile(INPUT_2);
			}

		}

		if (input1 != null && input2 != null) {
			int compare = CompareUtil.compare(getKey(input1), getKey(input2)) * getMergeSortMethod();

			if (compare == EQUAL) {
				outputFileList.writeToAllFile(input1);
				outputFileList.writeToAllFile(input2);
				// System.out.println("write 1 2 " + input1 + " " + input2);
				// input1 = inputFileList.readFile(INPUT_1);
				// input2 = inputFileList.readFile(INPUT_2);

			} else if (compare > EQUAL) {
				outputFileList.writeToAllFile(input2);
				outputFileList.writeToAllFile(input1);

			} else if (compare < EQUAL) {
				outputFileList.writeToAllFile(input1);
				outputFileList.writeToAllFile(input2);
			}
		} else {
			outputFileList.writeToAllFile(input1);
			outputFileList.writeToAllFile(input2);
		}

	}

	private void sortOneLastComponent(String input1, String input2) {
		int emptyCount = 0;

		while (inputFileList.size() == INPUT_SIZE && !inputFileList.isAllEmpty() || isSomeLastComponentsRunning()) {
			// System.out.println("input1 " + input1 + "," + "input2 " +
			// input2);

			if (input1 != null && input2 != null) {
				emptyCount = 0;
				int compare = CompareUtil.compare(getKey(input1), getKey(input2)) * getMergeSortMethod();

				if (compare == EQUAL) {
					outputFileList.writeToAllFile(input1);
					// outputFileList.writeToAllFile(input2);
					// System.out.println("write 1 2 " + input1 + " " + input2);
					input1 = inputFileList.readFile(INPUT_1);
					// input2 = inputFileList.readFile(INPUT_2);

				} else if (compare > EQUAL) {
					outputFileList.writeToAllFile(input2);
					// System.out.println("write 2 " + input2);
					input2 = inputFileList.readFile(INPUT_2);

				} else if (compare < EQUAL) {
					outputFileList.writeToAllFile(input1);
					// System.out.println("write 1 " + input1);
					input1 = inputFileList.readFile(INPUT_1);

				}

			} else if (input1 != null && input2 == null) {
				// System.out.println(emptyCount);
				if (emptyCount < 50) {
					input2 = inputFileList.readFile(INPUT_2);
					emptyCount++;
				} else if (emptyCount >= 50) {
					outputFileList.writeToAllFile(input1);
					input1 = inputFileList.readFile(INPUT_1);
				}

			} else if (input1 == null && input2 != null) {
				// System.out.println(emptyCount);
				if (emptyCount < 50) {
					input1 = inputFileList.readFile(INPUT_1);
					emptyCount++;
				} else if (emptyCount >= 50) {
					outputFileList.writeToAllFile(input2);
					input2 = inputFileList.readFile(INPUT_2);
				}

			} else if (input1 == null && input2 == null) {
				input1 = inputFileList.readFile(INPUT_1);
				input2 = inputFileList.readFile(INPUT_2);
			}

		}

		if (input1 != null && input2 != null) {
			int compare = CompareUtil.compare(getKey(input1), getKey(input2)) * getMergeSortMethod();

			if (compare == EQUAL) {
				outputFileList.writeToAllFile(input1);
				outputFileList.writeToAllFile(input2);
				// System.out.println("write 1 2 " + input1 + " " + input2);
				// input1 = inputFileList.readFile(INPUT_1);
				// input2 = inputFileList.readFile(INPUT_2);

			} else if (compare > EQUAL) {
				outputFileList.writeToAllFile(input2);
				outputFileList.writeToAllFile(input1);

			} else if (compare < EQUAL) {
				outputFileList.writeToAllFile(input1);
				outputFileList.writeToAllFile(input2);
			}

		} else {
			outputFileList.writeToAllFile(input1);
			outputFileList.writeToAllFile(input2);
		}

	}

}
