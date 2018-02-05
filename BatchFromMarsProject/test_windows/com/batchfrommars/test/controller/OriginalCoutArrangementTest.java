package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.QueueFile;
import com.batchfrommars.util.LogLevel;

public class OriginalCoutArrangementTest {
	@Test
	public void testCount() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();
		// Set input

		testInput.writeFile("0001.35DD00");
		testInput.writeFile("0003.25EE03");
		testInput.writeFile("0002.15EE02");

		Integer count = batchController.input(testInput)
				                       .output(testOutput)
				                       .logger("TestSum2", "D:/BatchFromMars", LogLevel.FINEST)
				                       .filter((s)->s.substring(0, 4).equals("0001"))
				                       .count();

		assertEquals(new Integer(1), count);

		assertFalse(testOutput.isEmpty());
		assertEquals("0001.35DD00", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

}
