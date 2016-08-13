package com.batchfrommars.file;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileList {
	private ArrayList<FileInformation> fileInformationsList;

	public FileList(ArrayList<FileInformation> fileInformationsList) {
		this.fileInformationsList = fileInformationsList;
	}

	public FileList() {
		fileInformationsList = new ArrayList<>();
	}

	public void addFileInformation(FileInformation fileInformation) {
		fileInformationsList.add(fileInformation);
	}

	public LinkedList<String> readFile() {
		LinkedList<String> inputList = new LinkedList<>();
		for (FileInformation item : fileInformationsList) {
			inputList.add(item.readFile());
		}
		return inputList;
	}

	public void writeFile(List<String> outputList) {
		if (outputList.size() == fileInformationsList.size()) {
			for (int i = 0; i < outputList.size(); i++) {
				// System.out.println(outputList.get(i));
				// System.out.println(fileInformationsList.get(i));
				fileInformationsList.get(i).writeFile(outputList.get(i));
			}
		} else {
			System.err.println("FileInformationList.writeFile.Output size is not equal to output File information");
		}
	}

	public void closeFile() {
		for (FileInformation item : fileInformationsList) {
			item.closeFile();
		}
	}

	public boolean isEmpty() {
		boolean isEmty = true;
		for (FileInformation item : fileInformationsList) {
			isEmty = isEmty && item.isEmpty();
		}
		return isEmty;
	}

	public String toString() {
		return "FileList [fileInformationsList=" + fileInformationsList + "]";
	}

}
