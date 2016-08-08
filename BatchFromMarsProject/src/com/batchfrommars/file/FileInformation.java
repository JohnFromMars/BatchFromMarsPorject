package com.batchfrommars.file;

public interface FileInformation {

	String readFile();

	void writeFile(String data);

	boolean isEmpty();

	void closeFile();

}