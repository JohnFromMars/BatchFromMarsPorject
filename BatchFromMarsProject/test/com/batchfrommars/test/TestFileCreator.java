package com.batchfrommars.test;

import java.io.IOException;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.PhysicalFile;

public class TestFileCreator {
	public static final String D_STRING = String.format("%0400d", 123456789);

	public static void main(String[] args) throws IOException {
		FileInformation testFile = new PhysicalFile(PhysicalFile.OUTPUT, "e:/BatchFromMars/SortData/sort3.txt", "UTF8",
				false);

		for (int i = 0; i < 2000000; i++) {
			int t = (int) (Math.random() * 200000000);
			testFile.writeFile(String.format("%09d", t) + D_STRING);
		}

		testFile.closeFile();
	}

}
