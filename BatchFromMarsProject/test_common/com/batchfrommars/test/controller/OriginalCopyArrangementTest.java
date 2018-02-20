package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.QueueFile;

public class OriginalCopyArrangementTest {
	@Test
	public void testCopy() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input
	
		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		batchController.input(testInput)
		               .output(testOutput)
				       .copy()
				       .execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00", testOutput.readFile());
		assertEquals("0003EE03", testOutput.readFile());
		assertEquals("0002EE02", testOutput.readFile());
		assertEquals("0003DD01", testOutput.readFile());
		assertEquals("0000HH00", testOutput.readFile());
		assertEquals("0000HH000", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}
	
	@Test
	public void testCopy2() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input
	
		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		batchController.input(testInput)
		               .output(testOutput)
				       .copy()
				       .execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00", testOutput.readFile());
		assertEquals("0003EE03", testOutput.readFile());
		assertEquals("0002EE02", testOutput.readFile());
		assertEquals("0000HH00", testOutput.readFile());
		assertEquals("0000HH000", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}



}
