package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;
import com.batchfrommars.util.LogeLevel;

public class OriginSumArrangement {

	@Test
	public void testSum() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new TemporaryFile();

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		BigDecimal decimal = batchController.addInput(testInput)
				                            .addLogger("TestSum", "D:/BatchFromMars", LogeLevel.FINEST)
				                            .sum((s) -> s.substring(0, 4));

		assertEquals(new BigDecimal(9), decimal);

	}

	@Test
	public void testSum2() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();
		// Set input

		testInput.writeFile("0001.35DD00");
		testInput.writeFile("0003.25EE03");
		testInput.writeFile("0002.15EE02");
		

		BigDecimal decimal = batchController.addInput(testInput)
				                            .addOutput(testOutput)
				                            .addLogger("TestSum2", "D:/BatchFromMars", LogeLevel.FINEST)
				                            .sum((s) -> s.substring(0, 7));

		assertEquals(new BigDecimal(6.75), decimal);
		
		assertFalse(testOutput.isEmpty());
		assertEquals("0001.35DD00", testOutput.readFile());
		assertEquals("0003.25EE03", testOutput.readFile());
		assertEquals("0002.15EE02", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}
}
