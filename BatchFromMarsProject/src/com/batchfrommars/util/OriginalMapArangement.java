package com.batchfrommars.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;
import com.batchfrommars.component.ComponentII;

public class OriginalMapArangement {

	public void arrangeMapTask(List<ComponentII> components, Logger log, Function<String, String> function) {

		// create map task
		BatchComponentII batchComponentII = createMapComponent(log, function);

		log.finest("add map component to list");
		// add to list
		components.add(batchComponentII);

		log.finest("mapArrangement finish");
	}

	private BatchComponentII createMapComponent(Logger log, Function<String, String> function) {
		BatchComponentII batchComponentII = new BatchComponentII() {

			@Override
			protected Logger getLoggger() {
				return log;
			}

			@Override
			protected LinkedList<String> excuteProcess(LinkedList<String> dataList) {
				LinkedList<String> outList = new LinkedList<>();
				String outData = null;

				if (dataList.get(INPUT_1) != null) {
					outData = function.apply(dataList.get(INPUT_1));
					log.finest("After map = " + outData);
				}

				outList.add(outData);

				return outList;
			}
		};
		return batchComponentII;
	}

}
