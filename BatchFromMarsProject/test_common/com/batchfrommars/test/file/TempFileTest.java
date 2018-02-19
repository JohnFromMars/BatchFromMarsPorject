package com.batchfrommars.test.file;

import com.batchfrommars.file.TempFile;

public class TempFileTest {
	@org.junit.Test
	public void testTempFile() throws Exception {
		TempFile tempFile = new TempFile("OUTPUT", "abc");
		System.out.println(tempFile.getFile().exists());
		tempFile.writeFile("123");
		tempFile.writeFile("456");
		tempFile.closeFile();
		
		TempFile tempFile2 = new TempFile("INPUT", "abc");
		System.out.println(tempFile2.readFile());
		System.out.println(tempFile2.readFile());
		System.out.println(tempFile2.readFile());
		tempFile2.closeFile();
		
	}
	
	

}
