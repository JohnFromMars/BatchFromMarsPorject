package com.batchfrommars.util;

import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;
import com.batchfrommars.file.FileInformation;

/**
 * Execute task for batch controller
 * 
 * @author user
 *
 */
public interface ExecuteUtil {
	/**
	 * Set the execute task the batch controller
	 * 
	 * @param input
	 * @param output
	 * @param log
	 * @param components
	 * @param header
	 * @param footer
	 * @throws Exception
	 */
	void executeArrangement(FileInformation input, FileInformation output, Logger log, List<ComponentII> components,
			String header, String footer) throws Exception;
}
