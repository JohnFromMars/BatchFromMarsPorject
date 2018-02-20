package com.batchfrommars.util;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;

public class OriginalCopyArrangement {
	public void arrangeCopyTask(List<Task> tasks, Logger log) {
		BatchComponentII copyComponent = new BatchComponentII() {

			@Override
			protected Logger getLoggger() {
				// TODO Auto-generated method stub
				return log;
			}

			@Override
			protected LinkedList<String> excuteProcess(LinkedList<String> dataList) {
				// TODO Auto-generated method stub
				LinkedList<String> outputData = new LinkedList<>();
				String outData = null;

				if (dataList.get(INPUT_1) != null) {
						outData = dataList.get(INPUT_1);
						log.finest("copy data= " + outData);
				}

				outputData.add(outData);
				return outputData;
			}
		};
		tasks.add(new Task(copyComponent, TaskName.COPY, tasks.size()));
	}

}
