package com.batchfrommars.test.component;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.batchfrommars.component.SortComponent;
import com.batchfrommars.file.LogUtil;
import com.batchfrommars.test.beans.TestBean;

public class StepSortObject extends SortComponent {

	@Override
	protected ArrayList<Object> getKeys(String data) {
		TestBean bean = new TestBean();
		try {
			bean.parse(data);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<Object> keys = new ArrayList<>();
		keys.add(bean.getS1());
		return keys;
	}

	@Override
	protected ArrayList<Integer> getMethods() {
		ArrayList<Integer> method = new ArrayList<>();
		method.add(ASCESNDING);
		return method;
	}

	@Override
	protected Logger getLoggger() {
		// TODO Auto-generated method stub
		return LogUtil.getLogger("StepSortObject", "d:/BatchFromMars/TestSortComponent/");
	}

}
