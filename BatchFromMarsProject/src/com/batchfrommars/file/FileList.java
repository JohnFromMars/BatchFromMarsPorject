package com.batchfrommars.file;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public class FileList {
	// member area
	private List<FileInformation> fileInformationsList;
	// constant area
	private static String OUTPUT_NUMBER_MISMATCH_ERROR_MSG = "FileInformationList.writeFile.Output size is not equal to outputList";

	public FileList(ArrayList<FileInformation> fileInformationsList) {
		this.fileInformationsList = fileInformationsList;
	}

	public FileList() {
		fileInformationsList = new ArrayList<>();
	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param fileInformation
	 */
	public void addFileInformation(FileInformation fileInformation) {
		fileInformationsList.add(fileInformation);
	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @return
	 * @throws Exception 
	 */
	public LinkedList<String> readFile() throws Exception {
		LinkedList<String> inputList = new LinkedList<>();
		for (FileInformation item : fileInformationsList) {
			inputList.add(item.readFile());
		}
		return inputList;
	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param i
	 * @return
	 * @throws Exception 
	 */
	public String readFile(int i) throws Exception {
		String output = null;
		output = fileInformationsList.get(i).readFile();
		return output;
	}

	/**
	 * all element in file list write file
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param outputList
	 * @throws Exception 
	 */
	public void writeFile(List<String> outputList) throws Exception {
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
	 * @date 2016-09-17
	 * @remark
	 * @param i
	 * @param outputData
	 * @throws Exception 
	 */
	public void writeFile(int i, String outputData) throws Exception {
		fileInformationsList.get(i).writeFile(outputData);
	}

	/**
	 * write the string to all elements in file list
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param outputData
	 * @throws Exception 
	 */
	public void writeToAllFile(String outputData) throws Exception {
		for (FileInformation item : fileInformationsList) {
			item.writeFile(outputData);
		}
	}

	/**
	 * all element in file list close file
	 * @throws Exception 
	 * 
	 * @date 2016-09-17
	 * @remark
	 */
	public void closeFile() throws Exception {
		for (FileInformation item : fileInformationsList) {
			item.closeFile();
		}
	}

	/**
	 * specified element in file list close file
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param i
	 * @throws Exception 
	 */
	public void closeFile(int i) throws Exception {
		fileInformationsList.get(i).closeFile();
	}

	/**
	 * return true when all elements are empty
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @return
	 */
	public boolean isAllEmpty() {
		boolean isEmty = true;
		for (FileInformation item : fileInformationsList) {
			isEmty = isEmty && item.isEmpty();
		}
		return isEmty;
	}

	/**
	 * return true when some elements are empty
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @return
	 */
	public boolean isSomeEmpty() {
		boolean isEmty = false;
		for (FileInformation item : fileInformationsList) {
			isEmty = isEmty || item.isEmpty();
		}
		return isEmty;

	}

	/**
	 * return specified element is empty or not
	 * 
	 * @date 2016-09-17
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
	 * @date 2016-09-17
	 * @remark
	 * @return
	 */
	public int size() {
		return fileInformationsList.size();
	}

	
	public FileInformation get(int fileInformation) {
		return this.fileInformationsList.get(fileInformation);
	}

	
	public FileInformation getLast() {
		return fileInformationsList.get(fileInformationsList.size() - 1);
	}

	public void clear() {
		this.fileInformationsList.clear();
	}

	public void deleteAllFile() throws Exception {
		for (FileInformation item : this.fileInformationsList) {
			item.deleteFile();
		}
	}

	public void deleteFile(int fileInformation) throws Exception {
		this.fileInformationsList.get(fileInformation).deleteFile();
	}

	public List<FileInformation> getFileInformationsList() {
		return fileInformationsList;
	}

	public void setFileInformationsList(List<FileInformation> fileInformationsList) {
		this.fileInformationsList = fileInformationsList;
	}

	public String toString() {
		return "FileList [fileInformationsList=" + fileInformationsList + "]";
	}

}
