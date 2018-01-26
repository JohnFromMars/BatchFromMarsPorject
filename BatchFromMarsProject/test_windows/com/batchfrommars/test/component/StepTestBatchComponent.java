package com.batchfrommars.test.component;

import java.util.LinkedList;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;
import com.batchfrommars.file.LogUtil;

public class StepTestBatchComponent extends BatchComponentII {
	private int devCount = 0;
	private int uatCount = 0;
	private int prdCount = 0;

	@Override
	protected LinkedList<String> excuteProcess(LinkedList<String> dataList) {
		LinkedList<String> outList = new LinkedList<>();
		String outDev = null;
		String outUat = null;
		String outPrd = null;

		
		if (dataList.get(INPUT_1) != null) {
		
			if (dataList.get(INPUT_1).substring(0, 3).equals("dev")) {
				this.logger.finest("dev count ++,dataList.get(INPUT_1)=" + dataList.get(INPUT_1));
				outDev = dataList.get(INPUT_1);
				devCount++;

			} else if (dataList.get(INPUT_1).substring(0, 3).equals("uat")) {
				this.logger.finest("uat count ++,dataList.get(INPUT_1)=" + dataList.get(INPUT_1));
				outUat = dataList.get(INPUT_1);
				uatCount++;

			} else if (dataList.get(INPUT_1).substring(0, 3).equals("prd")) {
				this.logger.finest("prd count ++,dataList.get(INPUT_1)=" + dataList.get(INPUT_1));
				outPrd = dataList.get(INPUT_1);
				prdCount++;
			}
		}

		outList.add(outDev);
		outList.add(outUat);
		outList.add(outPrd);

		return outList;
	}

	@Override
	protected Logger getLoggger() {
		return LogUtil.getLogger("StepTestBatchComponent", "d:/BatchFromMars/TestBatchComponent/");
	}

	public int getDevCount() {
		return devCount;
	}

	public int getUatCount() {
		return uatCount;
	}

	public int getPrdCount() {
		return prdCount;
	}

}
