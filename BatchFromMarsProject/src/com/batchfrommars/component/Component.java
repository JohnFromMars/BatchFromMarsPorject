package com.batchfrommars.component;

public class Component extends Thread {

	private Component lastComponent;

	public Component getLastComponent() {
		return lastComponent;
	}

	public void setLastComponent(Component lastComponent) {
		this.lastComponent = lastComponent;
	}

	public boolean isLastComponentRunning() {

		if (lastComponent == null) {
			return false;
		} else {
			return lastComponent.isAlive();
		}

	}

}
