package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;
import com.batchfrommars.util.LogeLevel;

public class OriginExecuteArrangementTest {

	@Test
	public void testMultipleTasks() throws Exception {
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

		batchController.addInput(testInput)
		               .addOutput(testOutput)
				       .addLogger("MultipleTasksTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .filter((s) -> Integer.valueOf(s.substring(0, 4)) > 0)
				       .sort("1,4,A")
				       .map((String s) -> s + ".txt")
				       .execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00.txt", testOutput.readFile());
		assertEquals("0002EE02.txt", testOutput.readFile());
		assertEquals("0003DD01.txt", testOutput.readFile());
		assertEquals("0003EE03.txt", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}
}
