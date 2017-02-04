package com.batchfrommars.file;

/**
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public interface FileInformation {

	String readFile() throws Exception;

	void writeFile(String data)throws Exception;

	boolean isEmpty();

	void closeFile() throws Exception;
	
	void deleteFile() throws Exception;
	
	String toString();

}