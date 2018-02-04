package com.batchfrommars.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;
import com.batchfrommars.component.SortComponent;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class OriginalSortArrangement {

	
	public void arrangeSortTask(List<ComponentII> components, Logger log, String sortText) {

		log.finest("sortArrangement start, sort text=" + sortText);
		ArrayList<SortObject> result = analyseSortText(sortText, log);
		log.finest("result=" + result);

		//create sort task 
		SortComponent sortComponent = createSortComponent(log, result);

		log.finest("add sort component to list");
		// add to list
		components.add(sortComponent);

		log.finest("finish");

	}

	private SortComponent createSortComponent(Logger log, ArrayList<SortObject> result) {
		// set sort component
		SortComponent sortComponent = new SortComponent() {

			@Override
			protected java.util.logging.Logger getLoggger() {
				return log;
			}

			@Override
			protected ArrayList<Integer> getOrders() {
				return getOrderFromSortText(result, log);
			}

			@Override
			protected ArrayList<Object> getKeys(String data) {
				return getKeyFromSortText(result, data, log);
			}
		};
		return sortComponent;
	}

	private ArrayList<Integer> getOrderFromSortText(ArrayList<SortObject> result, Logger log) {

		ArrayList<Integer> orders = new ArrayList<>();

		for (SortObject sortObject : result) {
			orders.add(sortObject.getOrder());
			log.finest("add order=" + sortObject.getOrder());
		}
		log.finest("orders = " + orders);

		return orders;

	}

	private ArrayList<Object> getKeyFromSortText(ArrayList<SortObject> result, String data, Logger log) {
		ArrayList<Object> key = new ArrayList<>();

		for (SortObject sortObject : result) {
			key.add(data.substring(sortObject.getStart() - 1, sortObject.getEnd()));
			log.finest("add key=" + (sortObject.getStart() - 1) + ", " + sortObject.getEnd());
		}

		return key;
	}

	private ArrayList<SortObject> analyseSortText(String sortText, Logger log) {
		int leftPointer = 0;
		ArrayList<SortObject> sortObjects = new ArrayList<>();
		String[] strings = sortText.split(",");

		while (leftPointer < strings.length) {
			log.finest("leftPointer=" + leftPointer + ", strings.length=" + (strings.length));

			SortObject sortObject = new SortObject();

			for (int i = leftPointer; i < strings.length; i++) {
				log.finest("i=" + i);

				if (strings[i].matches("\\d+")) {

					if (sortObject.getStart() == null && sortObject.getEnd() == null) {
						log.finest("sortObject.getStart() == null && sortObject.getEnd() == null");
						log.finest("sortObject = " + sortObject.toString());
						log.finest("strings[i]=" + strings[i]);
						sortObject.setStart(Integer.parseInt(strings[i]));
						log.finest("after set=" + sortObject);

					} else if (sortObject.getStart() != null && sortObject.getEnd() == null
							&& Integer.parseInt(strings[i]) >= sortObject.getStart()) {
						log.finest(
								"sortObject.getStart() != null  sortObject.getEnd() == nullInteger.parseInt(strings[i]) >= sortObject.getStart()");
						log.finest("sortObject = " + sortObject.toString());
						log.finest("strings[i]=" + strings[i]);
						sortObject.setEnd(Integer.parseInt(strings[i]));
						log.finest("after set=" + sortObject);

					} else if (sortObject.getStart() != null && sortObject.getEnd() != null) {
						log.finest("sortObject.getStart() != null && sortObject.getEnd() != null");
						log.finest("sortObject = " + sortObject.toString());
						log.finest("strings[i]=" + strings[i]);
						sortObject.setOrder(1);
						sortObjects.add(sortObject);
						leftPointer = i;

						log.finest("after set=" + sortObject);
						log.finest("after set leftPointer = " + leftPointer);
						log.finest("sortObjects=" + sortObjects);
						break;

					} else {
						log.finest("SyntaxException");
						throw new SyntaxException(sortText + " please check the syntax of the stament.");
					}

				} else if (strings[i].equals("a") || strings[i].equals("A") || strings[i].equals("D")
						|| strings[i].equals("d")) {

					log.finest("in condition, strings[i]=" + strings[i]);

					if (sortObject.getStart() == null || sortObject.getEnd() == null) {
						log.finest("SyntaxException");
						throw new SyntaxException(sortText + " please check the syntax of the stament.");

					} else if (sortObject.getStart() != null && sortObject.getEnd() != null) {
						log.finest("sortObject = " + sortObject.toString());
						log.finest("strings[i]=" + strings[i]);

						if (strings[i].equals("A") || strings[i].equals("a")) {
							sortObject.setOrder(1);
						} else {
							sortObject.setOrder(-1);
						}
						log.finest("**sortObject = " + sortObject.toString());
						sortObjects.add(sortObject);
						leftPointer = i + 1;
						break;

					} else {
						log.finest("SyntaxException");
						throw new SyntaxException(sortText + " please check the syntax of the stament.");
					}

				} else {
					log.finest("SyntaxException");
					throw new SyntaxException(sortText + " please check the syntax of the stament.");
				}

				leftPointer = strings.length;
			}

			if (sortObject.getOrder() == null && sortObject.getStart() != null && sortObject.getEnd() != null) {
				sortObject.setOrder(1);
				sortObjects.add(sortObject);

			}

			if (sortObject.getOrder() == null && sortObject.getStart() != null && sortObject.getEnd() == null) {
				log.finest("SyntaxException");
				throw new SyntaxException(sortText + " please check the syntax of the stament.");
			}

		}

		log.finest("Sort object = " + sortObjects.toString());
		return sortObjects;
	}

}
