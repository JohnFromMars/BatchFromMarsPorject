package com.batchfrommars.util;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;
import com.batchfrommars.component.ComponentII;
import com.batchfrommars.file.FileInformation;

public class OriginalCountArrangement {

	private Integer count = new Integer(0);

	public Integer arrangeCount(List<ComponentII> components, Logger log, List<FileInformation> input, FileInformation output,
			String header, String footer) throws Exception {

		//create count task
		BatchComponentII batchComponentII = createCountComponent(log);

		//add it into task list
		components.add(batchComponentII);

		//execute the batch controller
		OriginalExecuteArrangement executeUtil = new OriginalExecuteArrangement();
		executeUtil.executeArrangement(input, output, log, components, header, footer);

		return count;
	}

	private BatchComponentII createCountComponent(Logger log) {
		BatchComponentII batchComponentII = new BatchComponentII() {

			@Override
			protected Logger getLoggger() {
				return log;
			}

			@Override
			protected LinkedList<String> excuteProcess(LinkedList<String> dataList) {

				if (dataList.get(INPUT_1) != null) {
					logger.finest("dataList.get(INPUT_1)=" + dataList.get(INPUT_1));
					count++;
					logger.finest("count=" + count);
				}

				return dataList;
			}
		};
		return batchComponentII;
	}

}
