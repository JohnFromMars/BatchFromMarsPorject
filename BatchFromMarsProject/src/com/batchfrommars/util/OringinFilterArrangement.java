package com.batchfrommars.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;
import com.batchfrommars.component.ComponentII;

public class OringinFilterArrangement implements FilterUtil {

	@Override
	public void mapArrangement(List<ComponentII> components, Logger log, Predicate<String> predicate) {
		log.finest("predicate=" + predicate.toString());

		BatchComponentII batchComponentII = new BatchComponentII() {

			@Override
			protected Logger getLoggger() {
				return log;
			}

			@Override
			protected LinkedList<String> excuteProcess(LinkedList<String> dataList) {
				LinkedList<String> outList = new LinkedList<>();
				String outData = null;

				if (predicate.test(dataList.get(INPUT_1))) {
					outData = dataList.get(INPUT_1);
					log.finest("filter data= " + outData);
				}

				outList.add(outData);
				return outList;
			}
		};

		log.finest("add map component to list");
		// add to list
		components.add(batchComponentII);

		log.finest("finish");
	}

}
