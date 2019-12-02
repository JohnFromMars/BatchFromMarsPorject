package com.batchfrommars.util;

import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;

/**
 * Sort task for the BatchController
 * 
 * @author user
 *
 */
public interface SortUtil {
	/**
	 * Set the sort task for the controller
	 * 
	 * @param components
	 * @param logger
	 * @param sortText
	 */
	void sortArrangement(List<ComponentII> components, Logger logger, String sortText);

}
