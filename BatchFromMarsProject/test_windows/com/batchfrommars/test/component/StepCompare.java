package com.batchfrommars.test.component;

import java.util.logging.Logger;

import com.batchfrommars.component.CompareComponent;
import com.batchfrommars.file.LogUtil;

public class StepCompare extends CompareComponent {

	@Override
	protected Object getKeyFromInput1(String inputData) {
		return inputData.substring(0, 4);

	}

	@Override
	protected Object getKeyFromInput2(String inputData) {
		return inputData.substring(0, 4);
	}

	@Override
	protected String getResultFormat(String inputData1, String inputData2) {
		return inputData1 + "," + inputData2;
	}

	@Override
	protected Logger getLoggger() {
		return LogUtil.getLogger("StepCompare", "d:/BatchFromMars/TestBatchComponent/");
	}

}
