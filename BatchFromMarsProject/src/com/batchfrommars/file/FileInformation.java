package com.batchfrommars.file;

/**
 * File information interface
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public interface FileInformation {

	/**
	 * read a line of string form the file
	 * 
	 * @return
	 * @throws Exception
	 */
	String readFile() throws Exception;

	/**
	 * write a line of string into a file
	 * 
	 * @param data
	 * @throws Exception
	 */
	void writeFile(String data) throws Exception;

	/**
	 * check if the file is empty
	 * 
	 * @return
	 */
	boolean isEmpty();

	/*
	 * Close the related resources of reading and writing the file
	 */
	void closeFile() throws Exception;

	/**
	 * Delete the file
	 * 
	 * @throws Exception
	 */
	void deleteFile() throws Exception;

	/**
	 * Show information for debugging
	 * 
	 * @return
	 */
	String toString();

}