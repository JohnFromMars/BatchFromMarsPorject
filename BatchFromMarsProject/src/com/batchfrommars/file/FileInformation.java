package com.batchfrommars.file;

import java.io.IOException;

/**
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public interface FileInformation {

	String readFile();

	void writeFile(String data);

	boolean isEmpty();

	void closeFile() throws IOException;
	
	void deleteFile() throws IOException;
	
	String toString();

}