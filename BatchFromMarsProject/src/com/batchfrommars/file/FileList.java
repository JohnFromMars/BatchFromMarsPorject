package com.batchfrommars.file;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author JohnFromMars
 * @date 2016年8月13日
 * @remark 2016年8月13日
 */
public class FileList {
	// member area
	private ArrayList<FileInformation> fileInformationsList;
	// constant area
	private static String OUTPUT_NUMBER_MISMATCH_ERROR_MSG = "FileInformationList.writeFile.Output size is not equal to outputList";

	public FileList(ArrayList<FileInformation> fileInformationsList) {
		this.fileInformationsList = fileInformationsList;
	}

	public FileList() {
		fileInformationsList = new ArrayList<>();
	}

	/**
	 * add FileInformation into FileList
	 * 
	 * @date 2016年8月13日
	 * @remark
	 * @param fileInformation
	 */
	public void addFileInformation(FileInformation fileInformation) {
		fileInformationsList.add(fileInformation);
	}

	/**
	 * all element in file list read file
	 * 
	 * @date 2016年8月13日
	 * @remark
	 * @return
	 */
	public LinkedList<String> readFile() {
		LinkedList<String> inputList = new LinkedList<>();
		for (FileInformation item : fileInformationsList) {
			inputList.add(item.readFile());
		}
		return inputList;
	}

	/**
	 * specified element in file list read file
	 * 
	 * @date 2016年8月13日
	 * @remark
	 * @param i
	 * @return
	 */
	public String readFile(int i) {
		String output = null;
		output = fileInformationsList.get(i).readFile();
		return output;
	}

	/**
	 * all element in file list write file
	 * 
	 * @date 2016年8月13日
	 * @remark
	 * @param outputList
	 */
	public void writeFile(List<String> outputList) {
		if (outputList.size() == fileInformationsList.size()) {
			for (int i = 0; i < outputList.size(); i++) {

				fileInformationsList.get(i).writeFile(outputList.get(i));
			}
		} else {
			System.err.println(OUTPUT_NUMBER_MISMATCH_ERROR_MSG);
		}
	}

	/**
	 * specified element in file list write file
	 * 
	 * @date 2016年8月13日
	 * @remark
	 * @param i
	 * @param outputData
	 */
	public void writeFile(int i, String outputData) {
		fileInformationsList.get(i).writeFile(outputData);
	}

	/**
	 * write the string to all elements in file list
	 * 
	 * @date 2016年8月13日
	 * @remark
	 * @param outputData
	 */
	public void writeToAllFile(String outputData) {
		for (FileInformation item : fileInformationsList) {
			item.writeFile(outputData);
		}
	}

	/**
	 * all element in file list close file
	 * 
	 * @date 2016年8月13日
	 * @remark
	 */
	public void closeFile() {
		for (FileInformation item : fileInformationsList) {
			item.closeFile();
		}
	}

	/**
	 * specified element in file list close file
	 * 
	 * @date 2016年8月13日
	 * @remark
	 * @param i
	 */
	public void closeFile(int i) {
		fileInformationsList.get(i).closeFile();
	}

	/**
	 * return true when all elements are empty
	 * 
	 * @date 2016年8月13日
	 * @remark
	 * @return
	 */
	public boolean isEmpty() {
		boolean isEmty = true;
		for (FileInformation item : fileInformationsList) {
			isEmty = isEmty && item.isEmpty();
		}
		return isEmty;
	}

	/**
	 * return specified element is empty or not
	 * 
	 * @date 2016年8月13日
	 * @remark
	 * @param i
	 * @return
	 */
	public boolean isEmpty(int i) {
		return fileInformationsList.get(i).isEmpty();
	}

	/**
	 * return number of elements
	 * 
	 * @date 2016年8月13日
	 * @remark
	 * @return
	 */
	public int size() {
		return fileInformationsList.size();
	}

	public String toString() {
		return "FileList [fileInformationsList=" + fileInformationsList + "]";
	}

}
