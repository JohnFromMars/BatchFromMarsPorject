package com.batchfrommars.test.component;

import java.util.logging.Logger;

import com.batchfrommars.component.MergeSortComponent;
import com.batchfrommars.file.LogUtil;

public class StepMerge extends MergeSortComponent {

	@Override
	protected Object getKey(String data) {
		return data.substring(0, 4);
	}

	@Override
	protected int getMergeSortMethod() {
		return DECSENDING;
	}

	@Override
	protected Logger getLoggger() {
		return LogUtil.getLogger("StepMerge", "d:/BatchFromMars/TestMergeSortComponent/");
	}

}
