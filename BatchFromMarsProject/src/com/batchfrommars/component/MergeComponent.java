package com.batchfrommars.component;

import com.batchfrommars.file.FileInformation;

public abstract class MergeComponent extends ComponentII {

	@Override
	protected void act() throws Exception {
		// TODO Auto-generated method stub
		if (inputFileList.size() != 0) {
			for (FileInformation fileInformation : inputFileList.getFileInformationsList()) {
				while (!fileInformation.isEmpty()) {
					String data = fileInformation.readFile();
					outputFileList.writeToAllFile(data);
				}
			}
		}else{
			logger.severe("input of nmerge component should not be 0");
		}
	}
}
