package com.batchfrommars.test.component;

import java.util.LinkedList;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;
import com.batchfrommars.file.LogUtil;

public class StepDelayBatch extends BatchComponentII {

	@Override
	protected LinkedList<String> excuteProcess(LinkedList<String> dataList) {
		LinkedList<String> outList = new LinkedList<>();
		String data = null;

		if (dataList.get(INPUT_1) != null) {
			data = dataList.get(INPUT_1);
			
			try {
				Thread.sleep(300);
				
			} catch (InterruptedException e) {
				logger.warning(LogUtil.getExMsg(e));
			}
		}

		outList.add(data);
		return outList;
	}

	@Override
	protected Logger getLoggger() {
		return LogUtil.getLogger("StepDelayBatch", "d:/BatchFromMars/TestSortComponent/");
	}
}
