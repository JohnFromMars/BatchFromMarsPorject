package com.batchfrommars.util;

import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;

public class OriginExecuteArrangement implements ExecuteUtil {

	@Override
	public void executeArrangement(FileInformation input, FileInformation output, Logger log,
			List<ComponentII> components) {

		log.finest("components size = " + components.size());

		// set input, output
		components.get(0).addInputFileInformation(input);
		components.get(components.size() - 1).addOutputFileInformation(output);

		// set temp file
		for (int i = 0; i < components.size() - 1; i++) {
			FileInformation fileInformation = new TemporaryFile();
			components.get(i).addOutputFileInformation(fileInformation);

			if ((i + 1) < components.size()) {
				components.get(i + 1).addInputFileInformation(fileInformation);
			}
		}

		// start the batch
		for (ComponentII componentII : components) {
			componentII.start();
		}

	}

}
