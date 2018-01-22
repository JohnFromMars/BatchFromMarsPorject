package com.batchfrommars.util;

import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;

public interface MapUtil {
	void mapArrangement(List<ComponentII> components, Logger log, Function<String, String> function);
}
