package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;
import com.batchfrommars.util.LogeLevel;

public class OriginalFilterArrangementTest {

	@Test
	public void testFilter() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();

		// Set input
	
		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		batchController.input(testInput)
		               .output(testOutput)
				       .logger("FilterTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .filter((s) -> Integer.valueOf(s.substring(0, 4)) > 0)
				       .execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00", testOutput.readFile());
		assertEquals("0003EE03", testOutput.readFile());
		assertEquals("0002EE02", testOutput.readFile());
		assertEquals("0003DD01", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}
	
	@Test
	public void testFilter2() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();

		// Set input
	
		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		batchController.input(testInput)
		               .output(testOutput)
				       .logger("FilterTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .filter((s) -> s.length() > 8)
				       .execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0000HH000", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}
	
	@Test
	public void testFilter3() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();

		// Set input
	
		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		batchController.input(testInput)
		               .output(testOutput)
				       .logger("FilterTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .filter((s) -> s.substring(4, 6).equals("DD"))
				       .execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00", testOutput.readFile());
		assertEquals("0003DD01", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}
}
