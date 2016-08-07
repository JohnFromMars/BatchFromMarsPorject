package com.batchfrommars.component.sample;

import com.batchfrommars.component.BatchComponent;

public class JobTest0803Step3 extends BatchComponent {
	int count = 0;

	protected String excuteProcess(String data) {

		count++;

		return data;

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
