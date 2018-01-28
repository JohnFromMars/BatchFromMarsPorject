package com.batchfrommars.util;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import com.batchfrommars.component.BatchComponentII;
import com.batchfrommars.component.ComponentII;
import com.batchfrommars.file.FileInformation;

public class OriginSumArrangement implements SumUtil {

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
	@Override
	public BigDecimal arrangeSum(Function<String, String> function, List<ComponentII> components, Logger log,
			FileInformation input, FileInformation output, String header, String footer) throws Exception {

		// create the sum task
		BatchComponentII batchComponentII = createSumComponent(function, log);
		// add the task into list
		components.add(batchComponentII);
		//execute the batch controller
		ExecuteUtil executeUtil = new OriginExecuteArrangement();
		executeUtil.executeArrangement(input, output, log, components, header, footer);

		//return the sum finally
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
