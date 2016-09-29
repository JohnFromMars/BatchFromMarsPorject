package com.batchfrommars.component;

import java.util.ArrayList;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.FileList;

/**
 * ComponentII is parent class of every components in BatchFromMars. Every
 * component has inputFileList, outputFileList and lastComponentList.
 * ComponentII provide some method to access these member.Extend ComponentII and
 * implement act method, then you can create a component in BatchFromMars
 * system.
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public abstract class ComponentII extends Thread {
	// last components list
	private ArrayList<ComponentII> lastComponentList;
	// input , output file information list
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

	protected abstract void act();

	public ArrayList<ComponentII> getLastComponentList() {
		return this.lastComponentList;
	}

	/**
	 * 
	 */
	public void run() {
		onInit();
		act();
		onFinish();
		closeFileLists();

	}

	/**
	 * activate method would start the act method that children class implement
	 * with single thread.
	 * 
	 * @date 2016-09-17
	 * @remark
	 */
	public void activate() {
		onInit();
		act();
		onFinish();
		closeFileLists();
	}

	public void onInit() {

	}

	public void onFinish() {

	}

	public void closeFileLists() {
		inputFileList.closeFile();
		outputFileList.closeFile();
	}

	/**
	 * setLastComponentList aloud you set the property lastComponentList
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param lastComponentList
	 */
	public void setLastComponentList(ArrayList<ComponentII> lastComponentList) {
		this.lastComponentList = lastComponentList;
	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
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
	 * 
	 * @date 2016-09-17
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

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param last
	 * @return
	 */
	public boolean isLastComponentRunning(int last) {
		return lastComponentList.get(last).isAlive();
	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @return
	 */
	public int getLastComponentsSize() {
		return this.lastComponentList.size();
	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param component
	 */
	public void addLastComponent(ComponentII... component) {
		for (ComponentII c : component) {
			lastComponentList.add(c);
		}

	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @return
	 */
	public FileList getInputFileList() {
		return inputFileList;
	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param inputFileList
	 */
	public void setInputFileList(FileList inputFileList) {
		this.inputFileList = inputFileList;
	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @return
	 */
	public FileList getOutputFileList() {
		return outputFileList;
	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param outputFileList
	 */
	public void setOutputFileList(FileList outputFileList) {
		this.outputFileList = outputFileList;
	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param fileInformations
	 */
	public void addInputFileInformation(FileInformation... fileInformations) {
		for (FileInformation f : fileInformations) {
			this.inputFileList.addFileInformation(f);
		}

	}

	/**
	 * 
	 * @date 2016-09-17
	 * @remark
	 * @param fileInformations
	 */
	public void addOutputFileInformation(FileInformation... fileInformations) {
		for (FileInformation f : fileInformations) {
			this.outputFileList.addFileInformation(f);
		}

	}

}