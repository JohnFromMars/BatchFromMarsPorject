package com.batchfrommars.util;

import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;

/**
 * Map task for the BatchController
 * 
 * @author user
 *
 */
public interface MapUtil {
	/**
	 * Set a map task for the controller
	 * 
	 * @param components
	 * @param log
	 * @param function
	 */
	void mapArrangement(List<ComponentII> components, Logger log, Function<String, String> function);
}
