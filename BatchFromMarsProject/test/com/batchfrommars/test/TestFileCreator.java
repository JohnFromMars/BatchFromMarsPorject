package com.batchfrommars.test;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.PhysicalFile;

public class TestFileCreator {
	public static final String D_STRING = String.format("%0400d", 123456789);
	public static final String E_STRING = String.format("%0200d", 123456789);
	public static final String F_STRING = String.format("%0100d", 123456789);
	public static final String G_STRING = String.format("%0100d", 123456789);

	public void createFixedFile() throws Exception {
		FileInformation testFile = new PhysicalFile(PhysicalFile.OUTPUT, "e:/BatchFromMars/SortData/sort3.txt", "UTF8",
				false);

		for (int i = 0; i < 2000000; i++) {
			int t = (int) (Math.random() * 200000000);
			testFile.writeFile(String.format("%09d", t) + D_STRING);
		}

		testFile.closeFile();
	}


	public void createSplitFile() throws Exception {
		FileInformation testFile = new PhysicalFile(PhysicalFile.OUTPUT, "e:/BatchFromMars/SortData/sort5.txt", "UTF8",
				false);

		for (int i = 0; i < 2000000; i++) {
			int t = (int) (Math.random() * 200000000);
			testFile.writeFile(t + "," + E_STRING + "," + F_STRING + "," + G_STRING);
		}

		testFile.closeFile();
	}
}
