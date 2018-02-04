package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;
import com.batchfrommars.util.LogeLevel;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class OriginalSortArrangementTest {

	@Test(expected = SyntaxException.class)
	public void testSyntax() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				.output("D:/BatchFromMars/TTEESST.txt", "BIG5", false).sort("4,6,1,3,e").execute();
	}

	@Test(expected = SyntaxException.class)
	public void testSyntax2() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				.output("D:/BatchFromMars/TTEESST.txt", "BIG5", false).sort("4,6,1,a").execute();
	}

	@Test(expected = SyntaxException.class)
	public void testSyntax3() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				.output("D:/BatchFromMars/TTEESST.txt", "BIG5", false).sort("4,a,1").execute();
	}

	@Test(expected = SyntaxException.class)
	public void testSyntax4() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				.output("D:/BatchFromMars/TTEESST.txt", "BIG5", false).sort("a,2,1").execute();
	}

	@Test
	public void testSyntax5() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				.output("D:/BatchFromMars/TTEESST.txt", "BIG5", false).sort("1,2").execute();
	}

	@Test(expected = SyntaxException.class)
	public void testSyntax6() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				.output("D:/BatchFromMars/TTEESST.txt", "BIG5", false).sort("2,1").execute();
	}

	@Test
	public void testSyntax7() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				.output("D:/BatchFromMars/TTEESST.txt", "BIG5", false).sort("2,2").execute();
	}

	@Test
	public void testSyntax8() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				.output("D:/BatchFromMars/TTEESST.txt", "BIG5", false).sort("2,2,3,3").execute();
	}

	@Test
	public void testSort() throws Exception {
		FileInformation testInput = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();

		// Set input
		testInput.writeFile("0000xxxx");
		testInput.writeFile("0001xxxx");
		testInput.writeFile("0100xxxx");
		testInput.writeFile("0120xxxx");
		testInput.writeFile("0030xxxx");
		testInput.writeFile("0034xxxx");
		testInput.writeFile("0250xxxx");
		testInput.writeFile("0000xxxx");
		testInput.writeFile("0010xxxx");
		testInput.writeFile("1240xxxx");
		testInput.writeFile("1000xxxx");
		testInput.writeFile("0500xxxx");
		testInput.writeFile("8500xxxx");
		testInput.writeFile("0022xxxx");
		testInput.writeFile("0440xxxx");
		
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				.input(testInput)
				.output(testOutput)
				.sort("1,4,D")
				.execute();
		
		assertEquals("8500xxxx", testOutput.readFile());
		assertEquals("1240xxxx", testOutput.readFile());
		assertEquals("1000xxxx", testOutput.readFile());
		assertEquals("0500xxxx", testOutput.readFile());
		assertEquals("0440xxxx", testOutput.readFile());
		assertEquals("0250xxxx", testOutput.readFile());
		assertEquals("0120xxxx", testOutput.readFile());
		assertEquals("0100xxxx", testOutput.readFile());
		assertEquals("0034xxxx", testOutput.readFile());
		assertEquals("0030xxxx", testOutput.readFile());
		assertEquals("0022xxxx", testOutput.readFile());
		assertEquals("0010xxxx", testOutput.readFile());
		assertEquals("0001xxxx", testOutput.readFile());
		assertEquals("0000xxxx", testOutput.readFile());
		assertEquals("0000xxxx", testOutput.readFile());
	}
	
	@Test
	public void twoRoundSort() throws Exception{
		FileInformation testInput = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();

		// Set input
		testInput.writeFile("0000AA00");
		testInput.writeFile("0001BB01");
		testInput.writeFile("0000CC02");
		testInput.writeFile("0001DD03");
		testInput.writeFile("0003EE04");
		testInput.writeFile("0002EE05");
		testInput.writeFile("0003DD06");
		testInput.writeFile("0000HH07");
		testInput.writeFile("0002AA08");
		testInput.writeFile("0001xx08");
		testInput.writeFile("0003AA01");
		testInput.writeFile("0002ZZ02");
		testInput.writeFile("0000BB05");
		testInput.writeFile("0003BB00");
		testInput.writeFile("0001NN01");
		testInput.writeFile("0002NN09");
		testInput.writeFile("0001NN09");
		testInput.writeFile("0001NN08");
		testInput.writeFile("0005NN07");
		testInput.writeFile("0001NN07");
		testInput.writeFile("0002NN08");
		testInput.writeFile("0002NN09");
		testInput.writeFile("0002NN04");
		testInput.writeFile("0002NN06");
		testInput.writeFile("0000AA05");
		testInput.writeFile("0001BB00");
		testInput.writeFile("0000CC00");
		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				.input(testInput)
				.output(testOutput)
				.sort("7,8,D,5,6,A,1,4,D")
				.execute();
		
		assertFalse(testOutput.isEmpty());
		assertEquals("0005NN07", testOutput.readFile());
		assertEquals("0003AA01", testOutput.readFile());
		assertEquals("0003BB00", testOutput.readFile());
		assertEquals("0003DD06", testOutput.readFile());
		assertEquals("0003DD01", testOutput.readFile());
		assertEquals("0003EE04", testOutput.readFile());
		assertEquals("0003EE03", testOutput.readFile());
		assertEquals("0002AA08", testOutput.readFile());
		assertEquals("0002EE05", testOutput.readFile());
		assertEquals("0002EE02", testOutput.readFile());
		assertEquals("0002NN09", testOutput.readFile());
		assertEquals("0002NN09", testOutput.readFile());
		assertEquals("0002NN08", testOutput.readFile());
		assertEquals("0002NN06", testOutput.readFile());
		assertEquals("0002NN04", testOutput.readFile());
		assertEquals("0002ZZ02", testOutput.readFile());
		assertEquals("0001BB01", testOutput.readFile());
		assertEquals("0001BB00", testOutput.readFile());
		assertEquals("0001DD03", testOutput.readFile());
		assertEquals("0001DD00", testOutput.readFile());
		assertEquals("0001NN09", testOutput.readFile());
		assertEquals("0001NN08", testOutput.readFile());
		assertEquals("0001NN07", testOutput.readFile());
		assertEquals("0001NN01", testOutput.readFile());
		assertEquals("0001xx08", testOutput.readFile());
		assertEquals("0000AA05", testOutput.readFile());
		assertEquals("0000AA00", testOutput.readFile());
		assertEquals("0000BB05", testOutput.readFile());
		assertEquals("0000CC02", testOutput.readFile());
		assertEquals("0000CC00", testOutput.readFile());
		assertEquals("0000HH07", testOutput.readFile());
		assertEquals("0000HH00", testOutput.readFile());
		assertTrue(testOutput.isEmpty());

	}

}
