package com.batchfrommars.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.FileList;
import com.batchfrommars.file.PhysicalFile;
import com.batchfrommars.util.CompareUtil;

/**
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public abstract class SortComponent extends ComponentII {
	// constant area
	// number of data that can be sorted in memory
	private final static int SINGLE_SORT_SIZE = 200000;
	private final static int INPUT_1 = 0;
	private final static String TEMP_FILE_PATH = "c://";
	private final static String TEMP_FILE_ENCODING = "BIG5";
	private final static String WARNING_MSG="The input number of Sort Component can only be 1.";
	protected final static int ASCESNDING = 1;
	protected final static int DESCESNDING = -1;

	
	protected abstract ArrayList<Object> getInputKey(String inputData);

	protected abstract ArrayList<Integer> getSortMethod();

	public SortComponent() {

	}

	@Override
	protected void act() {
		ArrayList<String> sortList = new ArrayList<>();// list to restore data
		ArrayList<FileWritingComponent> writingFileList = new ArrayList<>();

		FileList roundFileList = new FileList();
		FileList tempFileList = new FileList();
		int tempFileCount = 0;

		if (inputFileList.size() != 1) {
			System.err.println(WARNING_MSG);
		} else {
			System.out.println(this.getClass().getSimpleName() + START_MSG);
		}

		for (int i = 0; i < getSortMethod().size(); i++) {
			// System.out.println("round " + i + " not in loop");
			// System.out.println("tempfile list is empty " +
			// tempFileList.isAllEmpty());
			//
			while (inputFileList.size() == 1 && !inputFileList.isAllEmpty() || !tempFileList.isAllEmpty()
					|| isAllLastComponentsRunning()) {
				// System.out.println("round " + i + " in loop");
				String input = null;

				// if it is first round read input file
				if (i == 0) {
					input = inputFileList.readFile(INPUT_1);
					// Syste
					// if not , read 0temporary file
				} else {
					input = tempFileList.readFile(INPUT_1);
					// System.out.println("read temp file size :" +
					// tempFileList.size() + "input =" + input);
				}

				// add data to list if it is not null
				if (input != null) {
					sortList.add(input);
				}

				// when list size greater than 200000
				// sort data and write them out as temp file
				if (sortList.size() > SINGLE_SORT_SIZE) {
					int sortNo = i;
					ArrayList<String> tempSortList = new ArrayList<>();
					// System.out.println("sortNo " + sortNo);
					// System.out.println("tempFileCount" + tempFileCount);
					// sort data and put them to another list to write out
					sortData(sortList, sortNo);
					tempSortList.addAll(sortList);
					// System.out.println("temp sort list size :" +
					// tempSortList.size());
					FileInformation tempOutputFile = newOutputPhyscalFile(tempFileCount);
					FileWritingComponent fileWritingComponent = newFileWritingComponent(tempSortList);
					fileWritingComponent.addOutputFileInformation(tempOutputFile);
					writingFileList.add(fileWritingComponent);
					fileWritingComponent.start();

					tempFileCount++;
					sortList.clear();
					// System.out.println("ort list size: " + sortList.size());

				}
			}

			if (sortList.size() <= SINGLE_SORT_SIZE) {
				// if data no more than 200000
				if (tempFileCount == 0) {
					int sortNo = i;
					// if it is last round, write data out to output file list
					if (i == getSortMethod().size() - 1) {
						// System.out.println("sortNo " + sortNo);

						for (String item : sortData(sortList, sortNo)) {
							// System.out.println("write out " + item);
							outputFileList.writeToAllFile(item);
						}

						outputFileList.closeFile();
						tempFileList.closeFile();
						roundFileList.closeFile();

						// if it is not last round,write data out to temp file
					} else {
						tempFileList.closeFile();
						tempFileList.clear();
						FileInformation tempOutput = new PhysicalFile(PhysicalFile.OUTPUT,
								TEMP_FILE_PATH + this.getClass().getSimpleName() + "temp.txt", "BIG5", false);

						for (String item : sortData(sortList, sortNo)) {
							tempOutput.writeFile(item);
						}

						tempOutput.closeFile();
						FileInformation tempInput = new PhysicalFile(PhysicalFile.INPUT,
								TEMP_FILE_PATH + this.getClass().getSimpleName() + "temp.txt", "BIG5", false);
						tempFileList.addFileInformation(tempInput);
					}

					sortList.clear();

				} else if (tempFileCount > 0) {
					int sortNo = i;
					ArrayList<String> tempSortList = new ArrayList<>();

					// System.out.println("sortNo " + sortNo);
					sortData(sortList, sortNo);
					tempSortList.addAll(sortList);
					FileInformation tempOutputFile = newOutputPhyscalFile(tempFileCount);
					FileWritingComponent fileWritingComponent = newFileWritingComponent(tempSortList);
					fileWritingComponent.addOutputFileInformation(tempOutputFile);
					writingFileList.add(fileWritingComponent);
					fileWritingComponent.start();

					tempFileCount++;
					sortList.clear();

					// wait for every thread of writing file finish
					waitForWritingFile(writingFileList);

					/* merge sort start */

					// prepare round file list
					for (int j = 0; j < tempFileCount; j++) {
						FileInformation mergeSorInput = newInputPhyscalFile(j);
						roundFileList.addFileInformation(mergeSorInput);
					}

					// new multiple merge sort component
					// round file list set as input file list
					MultiMergeSortComponent multiMergeSortComponent = newMultiMergeSortComponent(sortNo);
					multiMergeSortComponent.setInputFileList(roundFileList);

					// decide output list for next round
					// if it is last round
					if (i == getSortMethod().size() - 1) {

						tempFileList.closeFile();
						multiMergeSortComponent.setOutputFileList(outputFileList);
						multiMergeSortComponent.start();
						// System.out.println("merge write to output file list
						// in round " + i);

						try {
							multiMergeSortComponent.join();
							roundFileList.closeFile();

						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// if it is not last round
					} else {
						tempFileList.closeFile();
						tempFileList.clear();
						FileInformation tempOutput = new PhysicalFile(PhysicalFile.OUTPUT,
								TEMP_FILE_PATH + this.getClass().getSimpleName() + "temp.txt", "BIG5", false);
						tempFileList.addFileInformation(tempOutput);
						multiMergeSortComponent.setOutputFileList(tempFileList);
						multiMergeSortComponent.start();
						// System.out.println("merge write to temp file list in
						// round " + i);

						try {
							multiMergeSortComponent.join();
							FileInformation tempInput = new PhysicalFile(PhysicalFile.INPUT,
									TEMP_FILE_PATH + this.getClass().getSimpleName() + "temp.txt", "BIG5", false);
							tempFileList.clear();
							tempFileList.addFileInformation(tempInput);
							roundFileList.closeFile();
							roundFileList.clear();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}

				}
			}

			tempFileCount = 0;
		}

		if (inputFileList.size() == 1) {
			tempFileList.deleteAllFile();
			roundFileList.deleteAllFile();
			System.out.println(this.getClass().getSimpleName() + COMPELETE_MSG);
		}

	}

	private FileInformation newOutputPhyscalFile(int tempFileCount) {
		return new PhysicalFile(PhysicalFile.OUTPUT,
				TEMP_FILE_PATH + this.getClass().getSimpleName() + "temp" + tempFileCount + ".txt", TEMP_FILE_ENCODING,
				false);
	}

	private FileInformation newInputPhyscalFile(int tempFileCount) {
		return new PhysicalFile(PhysicalFile.INPUT,
				TEMP_FILE_PATH + this.getClass().getSimpleName() + "temp" + tempFileCount + ".txt", TEMP_FILE_ENCODING,
				false);
	}

	private FileWritingComponent newFileWritingComponent(ArrayList<String> sortList) {
		return new FileWritingComponent() {

			protected ArrayList<String> getWritingData() {
				return sortList;
			}
		};
	}

	private ArrayList<String> sortData(ArrayList<String> sortList, int sortNo) {
		Collections.sort(sortList, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return CompareUtil.compare(getInputKey(o1).get(sortNo), getInputKey(o2).get(sortNo))
						* getSortMethod().get(sortNo);

			}
		});

		return sortList;
	}

	private void waitForWritingFile(ArrayList<FileWritingComponent> writingFileList) {
		for (FileWritingComponent item : writingFileList) {
			try {
				item.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private MultiMergeSortComponent newMultiMergeSortComponent(int sortNo) {
		return new MultiMergeSortComponent() {
			@Override
			protected int getMethod() {
				return getSortMethod().get(sortNo);
			}

			@Override
			protected Object getSortKey(String inputData) {
				return getInputKey(inputData).get(sortNo);
			}
		};
	}
}
