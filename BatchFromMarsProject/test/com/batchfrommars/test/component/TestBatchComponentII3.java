package com.batchfrommars.test.component;

import java.util.LinkedList;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;
import com.batchfrommars.file.LogUtil;

public class TestBatchComponentII3 extends BatchComponentII {
	private int count = 0;

	@Override
	protected LinkedList<String> excuteProcess(LinkedList<String> dataList) {
		LinkedList<String> outList = new LinkedList<>();
		String outData = null;

		if (dataList.get(INPUT_1) != null) {
			logger.fine("count++");
			logger.fine("dataList.get(INPUT_1)=" + dataList.get(INPUT_1));

			outData = dataList.get(INPUT_1);
			count++;
		}

		outList.add(outData);
		return outList;
	}

	@Override
	protected Logger getLoggger() {
		return LogUtil.getLogger("TestBatchComponentII3", "d:/BatchFromMars/TestBatchComponent/");
	}

	public int getCount() {
		return count;
	}

}
