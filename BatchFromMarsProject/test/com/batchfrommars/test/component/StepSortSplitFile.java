package com.batchfrommars.test.component;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.batchfrommars.component.SortComponent;
import com.batchfrommars.file.LogUtil;

public class StepSortSplitFile extends SortComponent {

	@Override
	protected ArrayList<Object> getKeys(String data) {
		StringTokenizer tokenizer = new StringTokenizer(data, ",");
		int key = Integer.valueOf(tokenizer.nextToken());
		
		ArrayList<Object> keys = new ArrayList<>();
		keys.add(key);
		return keys;
	}

	@Override
	protected ArrayList<Integer> getMethods() {
		ArrayList<Integer> orders=new ArrayList<>();
		orders.add(DESCESNDING);
		return orders;
	}

	@Override
	protected Logger getLoggger() {

		return LogUtil.getLogger("StepSortSplitFile", "d:/BatchFromMars/TestSortComponent/");
	}

}
