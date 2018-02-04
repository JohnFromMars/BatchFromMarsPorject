package com.batchfrommars.util;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Logger;

import com.batchfrommars.component.CompareComponent;
import com.batchfrommars.component.ComponentII;

public class OriginalCompareArrangement {

	public void arrangeCompareTask(List<ComponentII> components, Logger log, Function<String, String> firstInputKey,
			Function<String, String> secondInputKey, BiFunction<String, String, String> resultForm) {

		CompareComponent compareComponent = createCompareComponent(log, firstInputKey, secondInputKey, resultForm);

		log.finest("add compare component to list");

		components.add(compareComponent);

		log.finest("add compare component finish");

	}

	private CompareComponent createCompareComponent(Logger log, Function<String, String> firstInputKey,
			Function<String, String> secondInputKey, BiFunction<String, String, String> resultForm) {
		CompareComponent compareComponent = new CompareComponent() {

			@Override
			protected Logger getLoggger() {
				return log;
			}

			@Override
			protected String getResultFormat(String inputData1, String inputData2) {
				return resultForm.apply(inputData1, inputData2);
			}

			@Override
			protected Object getKeyFromInput2(String inputData) {
				return secondInputKey.apply(inputData);
			}

			@Override
			protected Object getKeyFromInput1(String inputData) {
				return firstInputKey.apply(inputData);
			}
		};
		return compareComponent;
	}

}
