package com.batchfrommars.file;

public interface FileInformtion {

	String readFile();

	void writeFile(String data);

	boolean isEmpty();

	void closeFile();

}