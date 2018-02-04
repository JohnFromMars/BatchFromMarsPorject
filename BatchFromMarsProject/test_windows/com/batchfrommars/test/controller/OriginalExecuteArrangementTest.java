package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;
import com.batchfrommars.util.LogeLevel;

public class OriginalExecuteArrangementTest {

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

		batchController.input(testInput).output(testOutput)
				.logger("MultipleTasksTest", "D:/BatchFromMars", LogeLevel.FINEST)
				.filter((s) -> Integer.valueOf(s.substring(0, 4)) > 0).sort("1,4,A").map((String s) -> s + ".txt")
				.execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00.txt", testOutput.readFile());
		assertEquals("0002EE02.txt", testOutput.readFile());
		assertEquals("0003EE03.txt", testOutput.readFile());
		assertEquals("0003DD01.txt", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testMultipleTasks2() throws Exception {
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

		batchController.input(testInput).output(testOutput)
				.logger("MultipleTasksTest2", "D:/BatchFromMars", LogeLevel.FINEST).map((String s) -> s + ".txt")
				.sort("1,4,A").filter((s) -> s.substring(4, 6).equals("DD")).execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00.txt", testOutput.readFile());
		assertEquals("0003DD01.txt", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testHeader() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();
		String header = "hello this is header !";

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		batchController.input(testInput).output(testOutput).header(header)
				.logger("MultipleTasksTest2", "D:/BatchFromMars", LogeLevel.FINEST).map((String s) -> s + ".txt")
				.sort("1,4,A").filter((s) -> s.substring(4, 6).equals("DD")).execute();

		assertFalse(testOutput.isEmpty());
		assertEquals(header, testOutput.readFile());
		assertEquals("0001DD00.txt", testOutput.readFile());
		assertEquals("0003DD01.txt", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testFooter() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();
		String footer = "hello this is footer !";

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		batchController.input(testInput).output(testOutput).footer(footer)
				.logger("MultipleTasksTest2", "D:/BatchFromMars", LogeLevel.FINEST).map((String s) -> s + ".txt")
				.sort("1,4,A").filter((s) -> s.substring(4, 6).equals("DD")).execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00.txt", testOutput.readFile());
		assertEquals("0003DD01.txt", testOutput.readFile());
		assertEquals(footer, testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testHeaderAndFooter() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();
		String footer = "hello this is footer !";
		String header = "hello this is header !";

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		//@formatter:off
		batchController.input(testInput)
		               .output(testOutput)
		               .footer(footer)
		               .header(header)
				       .logger("MultipleTasksTest2", "D:/BatchFromMars", LogeLevel.FINEST)
				       .map((String s) -> s + ".txt")
				       .sort("1,4,A")
				       .filter((s) -> s.substring(4, 6)
				       .equals("DD"))
				       .execute();
		//@formatter:on

		assertFalse(testOutput.isEmpty());
		assertEquals(header, testOutput.readFile());
		assertEquals("0001DD00.txt", testOutput.readFile());
		assertEquals("0003DD01.txt", testOutput.readFile());
		assertEquals(footer, testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testPhysicalFileWithHeaderAndFooter() throws Exception {
		BatchController batchController = new BatchController() {
		};
		//@formatter:off
		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .output("D:/BatchFromMars/TestFooterAndHeader.txt", "BIG5", false)
				       .header("header...")
				       .footer("footer...")
				       .sort("4,6,1,3")
				       .execute();
		//@formatter:on
	}

	@Test
	public void testPhysicalFileWithHeader() throws Exception {
		BatchController batchController = new BatchController() {
		};

		//@formatter:off
		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .output("D:/BatchFromMars/TestHeader.txt", "BIG5", false)
				       .header("header...")
				       .sort("4,6,1,3")
				       .execute();
		//@formatter:on
	}

	@Test
	public void testPhysicalFileWithfooter() throws Exception {
		BatchController batchController = new BatchController() {
		};

		//@formatter:off
		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
			           .output("D:/BatchFromMars/TestFooter.txt", "BIG5", false)
			           .footer("footer...")
			           .sort("4,6,1,3")
			           .execute();
		//@formatter:on
	}

	@Test
	public void testNoInput() throws Exception {
		BatchController batchController = new BatchController() {
		};

		//@formatter:off
		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
				       .output("D:/BatchFromMars/TestNoInput.txt", "BIG5", false).sort("4,6,1,3").header("header...")
				       .footer("footer...")
				       .map((s) -> s.substring(0, 4))
				       .filter((s) -> s.equals("0000"))
				       .execute();
		//@formatter:on
	}

	@Test
	public void testNoInput2() throws Exception {
		BatchController batchController = new BatchController() {
		};

		//@formatter:off
		String header = "出表日期:" + new Date().toString() + "\r\n"
		              + "出表單位:資訊處";
		//@formatter:on

		//@formatter:off
		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
			 	       .output("D:/BatchFromMars/TestNoInput2.txt", "BIG5", false)
			 	       .sort("4,6,1,3")
			 	       .header(header)
				       .footer("footer...\r\noh oh oh!")
				       .map((s) -> s + "cc")
				       .filter((s) -> s.equals("0000"))
				       .execute();
		//@formatter:on
	}

	@Test
	public void testMultipleTasks3() throws Exception {
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

		batchController.input(testInput).output(testOutput)
				.logger("MultipleTasksTest3", "D:/BatchFromMars", LogeLevel.FINEST)
				.filter((s) -> Integer.valueOf(s.substring(0, 4)) > 0).sort("1,4,A").map((String s) -> s + ".txt")
				.filter((s) -> s.substring(0, 4).equals("0003")).map((s) -> s + ".cc").execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0003EE03.txt.cc", testOutput.readFile());
		assertEquals("0003DD01.txt.cc", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testMultipleTasks4() throws Exception {
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

		batchController.input(testInput).output(testOutput)
				.logger("MultipleTasksTest3", "D:/BatchFromMars", LogeLevel.FINEST)
				.filter((s) -> Integer.valueOf(s.substring(0, 4)) > 0).sort("1,4,A").map((String s) -> s + ".txt")
				.filter((s) -> s.substring(0, 4).equals("0003")).map((s) -> s + ".cc").execute();

		assertFalse(testOutput.isEmpty());
		assertEquals("0003EE03.txt.cc", testOutput.readFile());
		assertEquals("0003DD01.txt.cc", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}
}
