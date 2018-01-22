package com.batchfrommars.component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.FileList;
import com.batchfrommars.file.LogUtil;

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

	protected Logger logger;

	// constant area
	protected final static String START_MSG = " started...";
	protected final static String COMPELETE_MSG = " compeleted...";
	protected final static SimpleDateFormat FORM = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static int NO_COMPONENT = 0;

	// get main function from children
	protected abstract void act() throws Exception;

	// get logger from children
	protected abstract Logger getLoggger();

	public ComponentII() {
		lastComponentList = new ArrayList<>();
		inputFileList = new FileList();
		outputFileList = new FileList();
		logger = this.getLoggger();
	}

	public ArrayList<ComponentII> getLastComponentList() {
		return this.lastComponentList;
	}

	/**
	 * 
	 */
	public void run() {
		logger.fine(this.getClass().getSimpleName() + " Runing...");
		logStart();
		init();
		performAct();
		finish();
//		closeFileLists();
		logComplete();

	}

	/**
	 * activate method would start the act method that children class implement
	 * with single thread.
	 * 
	 * @date 2016-09-17
	 * @remark
	 */
	public void activate() {
		logger.finest(this.getClass().getSimpleName() + " Activating...");
		logStart();
		init();
		performAct();
		finish();
//		closeFileLists();
		logComplete();

	}

	private void performAct() {
		try {
			this.act();

		} catch (Exception e) {
			logger.warning("#### Exception happened in act() method...");
			logger.warning(LogUtil.getExMsg(e));
			closeFileLists();
			System.exit(MAX_PRIORITY);
		}
	}

	private void init() {
		try {
			onInit();

		} catch (Exception e) {
			logger.warning("#### Exception happened in onInit() method...");
			logger.warning(LogUtil.getExMsg(e));
			closeFileLists();
			System.exit(MAX_PRIORITY);
		}
	}

	private void finish() {
		try {
			onFinish();

		} catch (Exception e) {
			logger.warning("#### Exception happened in onFinish() method...");
			logger.warning(LogUtil.getExMsg(e));
			closeFileLists();
			System.exit(MAX_PRIORITY);
		}
	}

	protected void onInit() {
	}

	protected void onFinish() {
	}

	protected void logStart() {
		logger.info("#### " + this.getClass().getSimpleName() + " started...");
		logger.info("#### Started at " + FORM.format(new Date()));
		logger.info("#### LastComponentList=" + lastComponentList);
		logger.info("#### InputFileList=" + inputFileList);
		logger.info("#### OutputFileList=" + outputFileList);
	}

	protected void logComplete() {
		logger.info("#### Completed at " + FORM.format(new Date()));
		logger.info("#### " + this.getClass().getSimpleName() + " completed...");
	}

	public void closeFileLists() {

		try {
			logger.info("#### Closing inputFileList and outputFileList...");
			inputFileList.closeFile();
			outputFileList.closeFile();
			logger.info("#### InputFileList and outputFileList have been closed...");

		} catch (Exception e) {
			logger.warning("#### Exception happened while closing inputFileList and outputFileList...");
			logger.warning(LogUtil.getExMsg(e));
		}
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
			logger.finest("Adding " + c.toString() + " to lastComponentLst");
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
			logger.finest("Adding " + f.toString() + " to inputFileList");
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
			logger.finest("Adding " + f.toString() + " to outputFileList");
			this.outputFileList.addFileInformation(f);
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}