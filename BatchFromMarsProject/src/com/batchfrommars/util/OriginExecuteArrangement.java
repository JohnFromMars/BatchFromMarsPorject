package com.batchfrommars.util;

import java.util.List;
import java.util.logging.Logger;

import com.batchfrommars.component.ComponentII;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;

public class OriginExecuteArrangement implements ExecuteUtil {

	@Override
	public void executeArrangement(FileInformation input, FileInformation output, Logger log,
			List<ComponentII> components) throws InterruptedException {

		log.finest("components size = " + components.size());

		// set input, output
		components.get(0).addInputFileInformation(input);
		log.finest("start component(0)+input");

		if (output != null) {
			components.get(components.size() - 1).addOutputFileInformation(output);
			log.finest("start component(" + (components.size() - 1) + ")+output");

		}

		// set temp file
		for (int i = 0; i < components.size(); i++) {
			FileInformation fileInformation = new TemporaryFile();

			if (i < components.size() - 1) {
				components.get(i).addOutputFileInformation(fileInformation);
				log.finest("in loop component(" + i + ")+output");

			}

			if ((i + 1) < components.size()) {
				components.get(i + 1).addInputFileInformation(fileInformation);
				components.get(i + 1).addLastComponent(components.get(i));
				log.finest("in loop component(" + (i + 1) + ")+output");
				log.finest("in loop, add component(" + i + ") to component(" + i + 1 + ")'s last component list");
			}
		}

		// start the batch
		for (ComponentII componentII : components) {
			componentII.start();
		}

		// wait the unfinished components
		for (ComponentII componentII : components) {
			componentII.join();
		}

	}

}
