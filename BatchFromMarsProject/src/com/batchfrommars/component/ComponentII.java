package com.batchfrommars.component;

import java.util.ArrayList;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.FileList;

/**
 * abstract class component , extend it to build your owner batch program
 * 
 * @author JohnFromMars
 * @date 2016年8月13日
 * @remark 2016年8月13日
 */
public abstract class ComponentII extends Thread {
	// last components list
	private ArrayList<ComponentII> lastComponentList;
	// input , output and temp file information list
	protected FileList inputFileList;
	protected FileList outputFileList;

	// constant area
	protected final static String START_MSG = " started...";
	protected final static String COMPELETE_MSG = " compeleted...";
	private final static int NO_COMPONENT = 0;

	public ComponentII() {
		lastComponentList = new ArrayList<>();
		inputFileList = new FileList();
		outputFileList = new FileList();
	}

	/**
	 * 
	 * @date 2016年8月14日
	 * @remark
	 */
	protected abstract void act();

	public ArrayList<ComponentII> getLastComponentList() {
		return this.lastComponentList;
	}

	/**
	 * implements for start
	 */
	public void run() {
		this.act();
	}

	/**
	 * 
	 * @date 2016年8月18日
	 * @remark
	 */
	public void activate() {
		this.act();
	}

	/**
	 * 
	 * @date 2016年8月16日
	 * @remark
	 * @param lastComponentList
	 */
	public void setLastComponentList(ArrayList<ComponentII> lastComponentList) {
		this.lastComponentList = lastComponentList;
	}

	/**
	 * return all last components is alive
	 * 
	 * @return
	 */
	public boolean isSomeLastComponentsRunning() {
		boolean isRunnnig = false;

		if (lastComponentList.size() != NO_COMPONENT) {
			for (ComponentII item : lastComponentList) {
				isRunnnig = isRunnnig || item.isAlive();
			}
		}
		return isRunnnig;
	}

	/**
	 * return true if all components are alive
	 * 
	 * @date 2016年8月16日
	 * @remark
	 * @return
	 */
	public boolean isAllLastComponentsRunning() {

		if (lastComponentList.size() != NO_COMPONENT) {
			boolean isRunnnig = true;
			for (ComponentII item : lastComponentList) {
				isRunnnig = isRunnnig && item.isAlive();
			}
			return isRunnnig;
		} else {
			return false;
		}
	}

	public boolean isLastComponentRunning(int last) {
		return lastComponentList.get(last).isAlive();
	}

	/**
	 * return number of last components list
	 * 
	 * @date 2016年8月16日
	 * @remark
	 * @return
	 */
	public int getLastComponentsSize() {
		return this.lastComponentList.size();
	}

	/**
	 * add componentII into last component list
	 * 
	 * @param component
	 */
	public void addLastComponent(ComponentII... component) {
		for (ComponentII c : component) {
			lastComponentList.add(c);
		}

	}

	/**
	 * get input file list
	 * 
	 * @date 2016年8月16日
	 * @remark
	 * @return
	 */
	public FileList getInputFileList() {
		return inputFileList;
	}

	/**
	 * set input file list
	 * 
	 * @date 2016年8月16日
	 * @remark
	 * @param inputFileList
	 */
	public void setInputFileList(FileList inputFileList) {
		this.inputFileList = inputFileList;
	}

	/**
	 * get output file list
	 * 
	 * @date 2016年8月16日
	 * @remark
	 * @return
	 */
	public FileList getOutputFileList() {
		return outputFileList;
	}

	/**
	 * set output file list
	 * 
	 * @date 2016年8月16日
	 * @remark
	 * @param outputFileList
	 */
	public void setOutputFileList(FileList outputFileList) {
		this.outputFileList = outputFileList;
	}

	/**
	 * add FileInformation into inputFileList
	 * 
	 * @param fileInformation
	 */
	public void addInputFileInformation(FileInformation... fileInformations) {
		for (FileInformation f : fileInformations) {
			this.inputFileList.addFileInformation(f);
		}

	}

	/**
	 * add FileInformation into outputFileList
	 * 
	 * @param fileInformation
	 */
	public void addOutputFileInformation(FileInformation... fileInformations) {
		for (FileInformation f : fileInformations) {
			this.outputFileList.addFileInformation(f);
		}

	}

}