package com.batchfrommars.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;

/**
 * The filter task for BacthController
 * @author user
 *
 */
public interface FilterUtil {
	/**
	 * Set a map task for the controller
	 * @param components
	 * @param log
	 * @param predicate
	 */
	void mapArrangement(List<ComponentII> components, Logger log, Predicate<String> predicate);
}
