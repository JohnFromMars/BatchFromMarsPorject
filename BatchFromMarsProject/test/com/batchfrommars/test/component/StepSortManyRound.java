package com.batchfrommars.test.component;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.batchfrommars.component.SortComponent;
import com.batchfrommars.file.LogUtil;

public class StepSortManyRound extends SortComponent {

	@Override
	protected ArrayList<Object> getKeys(String data) {
		ArrayList<Object> keys = new ArrayList<>();
		keys.add(data.substring(6, 8));
		keys.add(data.substring(4, 6));
		keys.add(data.substring(0, 4));
		return keys;
	}

	@Override
	protected ArrayList<Integer> getOrders() {
		ArrayList<Integer> methods = new ArrayList<>();
		methods.add(DESCESNDING);
		methods.add(ASCESNDING);
		methods.add(DESCESNDING);
		return methods;
	}

	@Override
	protected Logger getLoggger() {
		return LogUtil.getLogger("StepSortManyRound", "d:/BatchFromMars/TestSortComponent/");
	}

}
