package com.batchfrommars.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;
import com.batchfrommars.component.SortComponent;

public class OringinSortArrangement implements SortUtil {

	@Override
	public void sortArrangement(List<ComponentII> components, Logger log, String sortText) {

		log.finest("sortArrangement start");

		// set sort component
		SortComponent sortComponent = new SortComponent() {

			@Override
			protected java.util.logging.Logger getLoggger() {
				return log;
			}

			@Override
			protected ArrayList<Integer> getOrders() {
				return getOrderFromSortText(sortText);
			}

			@Override
			protected ArrayList<Object> getKeys(String data) {
				return getKeyFromSortText(sortText, data);
			}
		};

		log.finest("add sort component to list");
		// add to list
		components.add(sortComponent);

		log.finest("finish");

	}

	private ArrayList<Integer> getOrderFromSortText(String sortText) {
		String[] strings = sortText.split(",");

		for (int i = 0; i < strings.length - 1; i++) {

			if (i % 3 == 2) {
				if (strings[i].matches("\\d+")) {

				}
			}
		}
		
		return null;

	}

	private ArrayList<Object> getKeyFromSortText(String sortText, String data) {

		return null;
	}

}
