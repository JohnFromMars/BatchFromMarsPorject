package com.batchfrommars.util;

import java.io.IOException;
import java.util.List;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.PhysicalFile;

public class OriginalInputArrangement {

	// for physical file input
	public void arrangePhysicalFileInput(String filePath, String encodeing, List<FileInformation> inputs)
			throws IOException {
		FileInformation fileInformation = new PhysicalFile(PhysicalFile.INPUT, filePath, encodeing, false);
		inputs.add(fileInformation);
	}

	//for file information input
	public void arrangeFileInformationInput(List<FileInformation> inputs, FileInformation fileInformation) {
		inputs.add(fileInformation);
	}

}
