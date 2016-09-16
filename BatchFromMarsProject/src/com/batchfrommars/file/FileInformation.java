package com.batchfrommars.file;
/**
 * 
 * @author JohnFromMars
 * @date 2016年8月13日
 * @remark 2016年8月13日
 */
public interface FileInformation {

	String readFile();

	void writeFile(String data);

	boolean isEmpty();

	void closeFile();
	
	void deleteFile();

}