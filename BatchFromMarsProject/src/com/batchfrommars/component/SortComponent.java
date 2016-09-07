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
 * @date 2016年8月16日
 * @remark 2016年8月16日
 */
public abstract class SortComponent extends ComponentII {
	// constant area
	// number of data that can be sorted in memory
	private final static int SINGLE_SORT_SIZE = 30;
	private final static int INPUT_1 = 0;
	private final static String TEMP_FILE_PATH = "C://testIO/";
	private final static String TEMP_FILE_ENCODING = "BIG5";
	protected final static int ASCESNDING = 1;
	protected final static int DESCESNDING = -1;

	/**
	 * 
	 * @date 2016年8月17日
	 * @remark
	 * @param data
	 * @return
	 */
	protected abstract ArrayList<Object> getInputKey(String inputData);

	protected abstract ArrayList<Integer> getSortMethod();

	public SortComponent() {

	}

	protected void act() {
		ArrayList<String> sortList = new ArrayList<>();

		ArrayList<FileWritingComponent> writingFileList = new ArrayList<>();
		FileList roundFileList = new FileList();
		int count = 0;
		int tempFileCount = 0;

		for (int i = 0; i < getSortMethod().size(); i++) {
			System.out.println("round " + i + " not in loop");
			System.out.println("tempfile list is empty " + tempFileList.isAllEmpty());
			while (inputFileList.size() == 1 && !inputFileList.isAllEmpty() || !tempFileList.isAllEmpty()
					|| isAllLastComponentsRunning()) {
				System.out.println("round " + i + " in loop");
				String input = null;

				if (i == 0) {
					input = inputFileList.readFile(INPUT_1);
					System.out.println("read input file");
				} else {
					input = tempFileList.readFile(INPUT_1);
					System.out.println("read temp file  size :" + tempFileList.size() + "input =" + input);
				}

				if (input != null) {
					sortList.add(input);
					count++;
				}
				System.out.println("count = " + count);

				if (count > SINGLE_SORT_SIZE) {
					int sortNo = i;
					System.out.println("sortNo " + sortNo);
					System.out.println("tempFileCount" + tempFileCount);
					sortData(sortList, sortNo);
					ArrayList<String> tempSortList = new ArrayList<>();
					tempSortList.addAll(sortList);
					System.out.println("temp sort list size :" + tempSortList.size());
					FileInformation tempOutputFile = newOutputPhyscalFile(tempFileCount);
					FileWritingComponent fileWritingComponent = newFileWritingComponent(sortData(tempSortList, sortNo));
					fileWritingComponent.addOutputFileInformation(tempOutputFile);
					writingFileList.add(fileWritingComponent);
					fileWritingComponent.start();

					count = 0;
					tempFileCount++;
					sortList.clear();

				}
			}

			if (count <= SINGLE_SORT_SIZE) {
				if (tempFileCount == 0) {
					int sortNo = i;

					if (i == getSortMethod().size() - 1) {
						System.out.println("sortNo " + sortNo);

						for (String item : sortData(sortList, sortNo)) {
							System.out.println("write out " + item);
							outputFileList.writeToAllFile(item);
						}

						outputFileList.closeFile();

					} else {
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

					count = 0;
					sortList.clear();

				} else if (tempFileCount > 0) {
					int sortNo = i;
					ArrayList<String> tempSortList = new ArrayList<>();

					System.out.println("sortNo " + sortNo);
					sortData(sortList, sortNo);
					tempSortList.addAll(sortList);
					FileInformation tempOutputFile = newOutputPhyscalFile(tempFileCount);
					FileWritingComponent fileWritingComponent = newFileWritingComponent(sortData(tempSortList, sortNo));
					fileWritingComponent.addOutputFileInformation(tempOutputFile);
					writingFileList.add(fileWritingComponent);
					fileWritingComponent.start();

					count = 0;
					tempFileCount++;
					sortList.clear();

					// wait for every thread of writing file
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
						multiMergeSortComponent.setOutputFileList(outputFileList);
						multiMergeSortComponent.start();
						System.out.println("merge write to output file list in round " + i);

						try {
							multiMergeSortComponent.join();
							roundFileList.closeFile();
							roundFileList.clear();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						// if it is not last round
					} else {
						tempFileList.clear();
						FileInformation tempOutput = new PhysicalFile(PhysicalFile.OUTPUT,
								TEMP_FILE_PATH + this.getClass().getSimpleName() + "temp.txt", "BIG5", false);
						tempFileList.addFileInformation(tempOutput);
						multiMergeSortComponent.setOutputFileList(tempFileList);
						multiMergeSortComponent.start();
						System.out.println("merge write to temp file list in round " + i);

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

				// return
				// (getInputKey(o1).get(sortNo).compareTo(getInputKey(o2).get(sortNo)))
				// * getSortMethod().get(sortNo);
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
				// TODO Auto-generated catch block
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

				// return getInputKey(inputData).get(sortNo);
				return getInputKey(inputData).get(sortNo);
			}
		};
	}
}
