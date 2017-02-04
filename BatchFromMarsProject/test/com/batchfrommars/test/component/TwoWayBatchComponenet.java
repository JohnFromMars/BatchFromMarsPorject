package com.batchfrommars.test.component;

import java.util.LinkedList;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;
import com.batchfrommars.file.LogUtil;

public class TwoWayBatchComponenet extends BatchComponentII {

	@Override
	protected LinkedList<String> excuteProcess(LinkedList<String> dataList) {
		LinkedList<String> outList = new LinkedList<>();
		String data1 = null;
		String data2 = null;

		if (dataList.get(INPUT_1) != null) {
			data1 = dataList.get(INPUT_1);
		}

		if (dataList.get(INPUT_2) != null) {
			data2 = dataList.get(INPUT_2);
		}

		outList.add(data1);
		outList.add(data2);
		return outList;
	}

	@Override
	protected Logger getLoggger() {
		return LogUtil.getLogger("TwoWayBatchComponenet", "d:/BatchFromMars/TestBatchComponent/");
	}

}
