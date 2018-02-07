package com.batchfrommars.util;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;
import com.batchfrommars.file.FileInformation;

public class OriginalSumArrangement {

	private BigDecimal decimal = new BigDecimal(0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.batchfrommars.util.SumUtil#arrangeSum(java.util.function.Function,
	 * java.util.List, java.util.logging.Logger,
	 * com.batchfrommars.file.FileInformation,
	 * com.batchfrommars.file.FileInformation)
	 */

	public BigDecimal arrangeSumTask(Function<String, String> function, List<Task> tasks, List<Task> hiddenTask,
			Logger log, List<FileInformation> input, FileInformation output, String header, String footer)
			throws Exception {

		// create the sum task
		BatchComponentII batchComponentII = createSumComponent(function, log);
		// add the task into list
		tasks.add(new Task(batchComponentII, TaskName.SUM, tasks.size()));
		// execute the batch controller
		OriginalExecuteArrangement executeUtil = new OriginalExecuteArrangement();
		executeUtil.arrangeExecuteTask(input, output, log, tasks, hiddenTask, header, footer);

		// return the sum finally
		return decimal;
	}

	private BatchComponentII createSumComponent(Function<String, String> function, Logger log) {
		BatchComponentII batchComponentII = new BatchComponentII() {

			@Override
			protected Logger getLoggger() {
				return log;
			}

			@Override
			protected LinkedList<String> excuteProcess(LinkedList<String> dataList) {

				if (dataList.get(INPUT_1) != null) {
					BigDecimal number = new BigDecimal(function.apply(dataList.get(INPUT_1)));
					decimal = decimal.add(number);
					log.finest("adding number=" + number + " to decimal, decimal=" + decimal);
				}

				return dataList;
			}

		};
		return batchComponentII;
	}

}
