package com.batchfrommars.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;

public class OriginalFilterArrangement {

	public void arrangeFilterTask(List<Task> tasks, Logger log, Predicate<String> predicate) {
		log.finest("predicate=" + predicate.toString());
		// create filter task
		BatchComponentII batchComponentII = createFilterComponent(log, predicate);

		log.finest("add map component to list");
		// add it into task list
		tasks.add(new Task(batchComponentII, TaskName.FILTER, tasks.size()));

		log.finest("finish");
	}

	private BatchComponentII createFilterComponent(Logger log, Predicate<String> predicate) {
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
					if (predicate.test(dataList.get(INPUT_1))) {
						outData = dataList.get(INPUT_1);
						log.finest("filter data= " + outData);
					}
				}

				outList.add(outData);
				return outList;
			}
		};
		return batchComponentII;
	}

}