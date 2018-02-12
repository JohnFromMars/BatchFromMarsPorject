package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.logging.Level;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.QueueFile;

public class OriginalMapArrangementTest {
	
	@Test
	public void testMap() throws Exception {
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
				       .logger("MapTest", "D:/BatchFromMars", Level.FINEST)
				       .map((s)->s.substring(0, 3))
				       .execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("000", testOutput.readFile());
		assertEquals("000", testOutput.readFile());
		assertEquals("000", testOutput.readFile());
		assertEquals("000", testOutput.readFile());
		assertEquals("000", testOutput.readFile());
		assertEquals("000", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}
	
	@Test
	public void testMap1() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();

		String date = "20/12/2017";
		// Set input
	
		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		

		batchController.input(testInput)
		               .output(testOutput)
				       .logger("MapTest", "D:/BatchFromMars", Level.FINEST)
				       .map((s)->s + "," + date)
				       .execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00,20/12/2017", testOutput.readFile());
		assertEquals("0003EE03,20/12/2017", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}
}
