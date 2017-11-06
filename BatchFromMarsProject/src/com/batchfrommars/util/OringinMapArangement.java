package com.batchfrommars.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;
import com.batchfrommars.component.ComponentII;

public class OringinMapArangement implements MapUtil {

	@Override
	public void mapArrangement(List<ComponentII> components, Logger log, Function<String, String> function) {

		BatchComponentII batchComponentII = new BatchComponentII() {

			@Override
			protected Logger getLoggger() {
				return log;
			}

			@Override
			protected LinkedList<String> excuteProcess(LinkedList<String> dataList) {
				LinkedList<String> outList = new LinkedList<>();
				String outData = function.apply(dataList.get(INPUT_1));
				outList.add(outData);
				log.finest("After map = " + outData);
				
				return outList;
			}
		};

		log.finest("add map component to list");
		// add to list
		components.add(batchComponentII);

		log.finest("finish");
	}

}
