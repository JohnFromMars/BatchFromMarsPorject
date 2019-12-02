package com.batchfrommars.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;
import com.batchfrommars.file.FileInformation;

/**
 * Sum Task for the BathController
 * 
 * @author user
 *
 */
public interface SumUtil {

	/**
	 * Set the sum task for the controller
	 * 
	 * @param function
	 * @param components
	 * @param log
	 * @param input
	 * @param output
	 * @param header
	 * @param footer
	 * @return
	 * @throws Exception
	 */
	BigDecimal arrangeSum(Function<String, String> function, List<ComponentII> components, Logger log,
			FileInformation input, FileInformation output, String header, String footer) throws Exception;

}