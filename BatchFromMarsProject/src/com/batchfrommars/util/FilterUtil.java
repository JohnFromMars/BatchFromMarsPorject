package com.batchfrommars.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;

public interface FilterUtil {
	void mapArrangement(List<ComponentII> components, Logger log, Predicate<String> predicate);
}
