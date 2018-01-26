package com.batchfrommars.test.component;

import java.util.logging.Logger;

import com.batchfrommars.component.MergeSortComponent;
import com.batchfrommars.file.LogUtil;

public class StepMergeAscending extends MergeSortComponent{

	@Override
	protected Object getKey(String data) {
		return data.substring(0,4);
	}

	@Override
	protected int getMergeSortMethod() {
		return ACSENDING;
	}

	@Override
	protected Logger getLoggger() {
		return LogUtil.getLogger("StepMergeAscending", "d:/BatchFromMars/TestMergeSortComponent/");
	}

}
