package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;

public class OriginalCompareArrangementTest {
	@Test
	public void testCompareTaskWithOrder() throws Exception {
		BatchController batchController = new BatchController();
		FileInformation testInput1 = new TemporaryFile();
		FileInformation testInput2 = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();

		testInput1.writeFile("000011111");
		testInput1.writeFile("000002222");
		testInput1.writeFile("1111xxxxx");
		testInput1.writeFile("4444sxdcf");

		testInput2.writeFile("000011111");
		testInput2.writeFile("111111111");
		testInput2.writeFile("4444sxdcf");

		//@formatter:off
		batchController.input(testInput1)
		               .input(testInput2)
		               .output(testOutput)
		               .compare((s) -> s, (s) -> s, (s1, s2) -> s1 + s2)
		               .execute();

		//@formatter:on

		assertFalse(testOutput.isEmpty());
		assertEquals("000011111000011111", testOutput.readFile());
		assertEquals("4444sxdcf4444sxdcf", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}
}
