package com.batchfrommars.util;

import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.component.MergeComponent;

public class OriginalMergeArrangement {

	public void arrangeMergeTask(List<Task> tasks, Logger log) {
		MergeComponent mergeComponent = createMergeComponent(log);
		tasks.add(new Task(mergeComponent, TaskName.MERGE, tasks.size()));
		
	}

	private MergeComponent createMergeComponent(Logger log) {
		MergeComponent mergeComponent = new MergeComponent() {

			@Override
			protected Logger getLoggger() {
				return log;
			}
		};

		return mergeComponent;
	}

}
