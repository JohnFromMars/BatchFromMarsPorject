package com.batchfrommars.util;

import java.io.IOException;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.PhysicalFile;

public class OriginalOutputArrangement {

	// for physical file output
	public FileInformation arrangePhysicalFileOutput(String filePath, String encodeing, boolean appdening)
			throws IOException {
		FileInformation fileInformation = new PhysicalFile(PhysicalFile.OUTPUT, filePath, encodeing, appdening);
		return fileInformation;
	}

	//for file information output
	public FileInformation arrangeFileInformationOutput(FileInformation fileInformation) {
		return fileInformation;
	}

}
