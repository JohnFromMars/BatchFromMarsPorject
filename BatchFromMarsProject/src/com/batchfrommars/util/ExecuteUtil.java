package com.batchfrommars.util;

import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;
import com.batchfrommars.file.FileInformation;

public interface ExecuteUtil {
	void executeArrangement(FileInformation input,FileInformation output,Logger log,List<ComponentII> components);
}
