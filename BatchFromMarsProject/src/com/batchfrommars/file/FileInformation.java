package com.batchfrommars.file;

/**
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public interface FileInformation {

	String readFile();

	void writeFile(String data);

	boolean isEmpty();

	void closeFile();
	
	void deleteFile();

}