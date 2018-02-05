package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.QueueFile;
import com.batchfrommars.util.LogLevel;

public class OriginalSumArrangementTest {

	@Test
	public void testSum() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		BigDecimal decimal = batchController.input(testInput)
				                            .logger("TestSum", "D:/BatchFromMars", LogLevel.FINEST)
				                            .sum((s) -> s.substring(0, 4));

		assertEquals(new BigDecimal(9), decimal);

	}

	@Test
	public void testSum2() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();
		// Set input

		testInput.writeFile("0001.35DD00");
		testInput.writeFile("0003.25EE03");
		testInput.writeFile("0002.15EE02");
		

		BigDecimal decimal = batchController.input(testInput)
				                            .output(testOutput)
				                            .logger("TestSum2", "D:/BatchFromMars", LogLevel.FINEST)
				                            .sum((s) -> s.substring(0, 7));

		assertEquals(new BigDecimal(6.75), decimal);
		
		assertFalse(testOutput.isEmpty());
		assertEquals("0001.35DD00", testOutput.readFile());
		assertEquals("0003.25EE03", testOutput.readFile());
		assertEquals("0002.15EE02", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}
}
