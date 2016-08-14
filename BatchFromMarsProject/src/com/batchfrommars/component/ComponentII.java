package com.batchfrommars.component;

import java.util.ArrayList;

/**
 * 
 * @author JohnFromMars
 * @date 2016年8月13日
 * @remark 2016年8月13日
 */
public abstract class ComponentII extends Thread {
	private ArrayList<ComponentII> lastComponentList = new ArrayList<ComponentII>();
	// constant area for children class
	protected final static String START_MSG = " started...";
	protected final static String COMPELETE_MSG = " compeleted...";
	//constant area
	private final static int NO_COMPONENT = 0;

	/**
	 * 
	 * @date 2016年8月14日
	 * @remark
	 */
	protected abstract void activate();

	public ArrayList<ComponentII> getLastComponentList() {
		return this.lastComponentList;
	}

	public void setLastComponentList(ArrayList<ComponentII> lastComponentList) {
		this.lastComponentList = lastComponentList;
	}

	/**
	 * return all last components is alive
	 * 
	 * @return
	 */
	public boolean isLastComponentsRunning() {
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
	 */
	public void run() {
		this.activate();
	}

	/**
	 * add componentII into last component list
	 * 
	 * @param component
	 */
	public void addLastComponent(ComponentII component) {
		lastComponentList.add(component);
	}

	/**
	 * add componentII into last component list
	 * 
	 * @param component
	 */
	public void addLastComponent(ComponentII component, ComponentII component2) {
		lastComponentList.add(component);
		lastComponentList.add(component2);
	}

	/**
	 * add componentII into last component list
	 * 
	 * @param component
	 */
	public void addLastComponent(ComponentII component, ComponentII component2, ComponentII component3) {
		lastComponentList.add(component);
		lastComponentList.add(component2);
		lastComponentList.add(component3);
	}

	/**
	 * add componentII into last component list
	 * 
	 * @param component
	 */
	public void addLastComponent(ComponentII component, ComponentII component2, ComponentII component3,
			ComponentII component4) {
		lastComponentList.add(component);
		lastComponentList.add(component2);
		lastComponentList.add(component3);
		lastComponentList.add(component4);
	}

	/**
	 * add componentII into last component list
	 * 
	 * @param component
	 */
	public void addLastComponent(ComponentII component, ComponentII component2, ComponentII component3,
			ComponentII component4, ComponentII component5) {
		lastComponentList.add(component);
		lastComponentList.add(component2);
		lastComponentList.add(component3);
		lastComponentList.add(component4);
		lastComponentList.add(component5);
	}
}
