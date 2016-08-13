package com.batchfrommars.component;

import java.util.ArrayList;

/**
 * 
 * @author Yj
 *
 */
public class ComponentII extends Thread {
	private ArrayList<ComponentII> lastComponentList = new ArrayList<ComponentII>();

	public ArrayList<ComponentII> getLastComponentList() {
		return this.lastComponentList;
	}

	public void setLastComponentList(ArrayList<ComponentII> lastComponentList) {
		this.lastComponentList = lastComponentList;
	}

	public void addLastComponent(ComponentII component) {
		lastComponentList.add(component);
	}

	/**
	 * return all last components is alive
	 * 
	 * @return
	 */
	public boolean isLastComponentsRunning() {
		boolean isRunnnig = false;

		if (lastComponentList.size() != 0) {
			for (ComponentII item : lastComponentList) {
				isRunnnig = isRunnnig || item.isAlive();
			}
		}

		return isRunnnig;
	}
}
