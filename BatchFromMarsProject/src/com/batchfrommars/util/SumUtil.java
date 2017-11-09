package com.batchfrommars.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;
import com.batchfrommars.file.FileInformation;

public interface SumUtil {

	BigDecimal arrangeSum(Function<String, String> function, List<ComponentII> components, Logger log,
			FileInformation input, FileInformation output,String header,String footer) throws Exception;

}